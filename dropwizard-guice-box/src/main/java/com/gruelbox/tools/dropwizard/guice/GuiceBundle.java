/**
 * dropwizard-guice-box Copyright 2018-2019 Graham Crockford
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gruelbox.tools.dropwizard.guice;

import static com.google.common.base.Predicates.instanceOf;

import com.codahale.metrics.health.HealthCheck;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import com.google.common.util.concurrent.Service;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.util.Types;
import com.gruelbox.tools.dropwizard.guice.resources.WebResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.ConfiguredBundle;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.lifecycle.Managed;
import jakarta.inject.Inject;
import java.util.Arrays;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Yet another approach to integrating DropWizard with Guice. Usage:
 *
 * <pre>public class MyApplication extends Application&lt;MyConfiguration&gt; {
 *
 * {@literal @}Inject private SomethingINeed somethingINeed;
 *
 * {@literal @}Override
 * public void initialize(final Bootstrap&lt;MyConfiguration&gt; bootstrap) {
 * bootstrap.addBundle(
 * new GuiceBundle&lt;MyConfiguration&gt;(
 * this,
 * new MyApplicationModule(),
 * new MyOtherApplicationModule()
 * )
 * );
 * }
 *
 * {@literal @}Override
 * public void run(final MyConfiguration configuration, final Environment environment) {
 * somethingINeed.canNowBeUsed();
 * }
 * }
 * </pre>
 *
 * <p>You now have access to some Guice idiomatic bind points in your {@link Module}s:
 *
 * <ul>
 *   <li>Multibindings to {@link WebResource} provide resources.
 *   <li>Multibindings to {@link HealthCheck} provide healthchecks.
 *   <li>Multibindings to {@link Managed} provide controlled startup and shutdown of components.
 *   <li>Multibindings to {@link Service} provide controlled startup and shutdown of background
 *       processes.
 *   <li>Multibindings to {@link EnvironmentInitialiser} allow you to hook into the {@link
 *       Application#run(io.dropwizard.core.Configuration, Environment)} phase directly from
 *       anywhere in your code when you need to do anything else on startup.
 * </ul>
 *
 * @author Graham Crockford
 * @param <T> The configuration class type.
 */
public class GuiceBundle<T> implements ConfiguredBundle<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(GuiceBundle.class);

  private final Iterable<Module> modules;
  private final Application<?> application;

  /**
   * Creates the bundle.
   *
   * @param application The {@link Application} itself. Any fields or methods annotated with {@link
   *     Inject} will be populated at the start of the {@link
   *     Application#run(io.dropwizard.core.Configuration, Environment)} phase, prior to the {@code
   *     Application}'s own {@code run}} method.
   * @param modules Guice modules to include when creating the injector itself, which will occur in
   *     the run phase.
   */
  public GuiceBundle(Application<?> application, Module... modules) {
    this(application, Arrays.asList(modules));
  }

  /**
   * Creates the bundle.
   *
   * @param application The {@link Application} itself. Any fields or methods annotated with {@link
   *     Inject} will be populated at the start of the {@link
   *     Application#run(io.dropwizard.core.Configuration, Environment)} phase, prior to the {@code
   *     Application}'s own {@code run}} method.
   * @param modules Guice modules to include when creating the injector itself, which will occur in
   *     the run phase.
   */
  @SuppressWarnings("WeakerAccess")
  public GuiceBundle(Application<?> application, Iterable<Module> modules) {
    this.application = application;
    this.modules = modules;
  }

  @Override
  public void initialize(Bootstrap<?> bootstrap) {
    // No-op
  }

  @Override
  public void run(T configuration, Environment environment) {
    configureModules(configuration);
    final Injector injector = createInjector(configuration, environment);
    registerRequestFilter(injector, environment);
    initialiseEnvironment(injector, environment);
    injector.injectMembers(application);
  }

  private void initialiseEnvironment(final Injector injector, Environment environment) {
    injector.getInstance(Key.get(setOf(EnvironmentInitialiser.class))).stream()
        .peek(t -> LOGGER.debug("Initialising environment for {}", t))
        .forEach(t -> t.init(environment));
  }

  private void registerRequestFilter(final Injector injector, Environment environment) {
    GuiceFilter guiceFilter = injector.getInstance(GuiceFilter.class);
    environment
        .servlets()
        .addFilter("GuiceFilter", guiceFilter)
        .addMappingForUrlPatterns(null, false, "/*");
    environment
        .admin()
        .addFilter("GuiceFilter", guiceFilter)
        .addMappingForUrlPatterns(null, false, "/*");
  }

  private Injector createInjector(T configuration, Environment environment) {
    return Guice.createInjector(
        Iterables.concat(
            modules, ImmutableList.of(new GuiceBundleModule<>(environment, configuration))));
  }

  @SuppressWarnings("unchecked")
  private void configureModules(T configuration) {
    Streams.stream(modules)
        .filter(instanceOf(Configured.class))
        .forEach(m -> ((Configured) m).setConfiguration(configuration));
  }

  @SuppressWarnings({"SameParameterValue", "unchecked"})
  private static <T> TypeLiteral<Set<T>> setOf(Class<T> type) {
    return (TypeLiteral<Set<T>>) TypeLiteral.get(Types.setOf(type));
  }
}
