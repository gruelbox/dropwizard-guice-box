/**
 * dropwizard-guice-box
 * Copyright 2018-2019 Graham Crockford
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gruelbox.tools.dropwizard.guice;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.gruelbox.tools.dropwizard.guice.healthcheck.GuiceHealthCheckModule;
import com.gruelbox.tools.dropwizard.guice.managed.GuiceManagedModule;
import com.gruelbox.tools.dropwizard.guice.resources.GuiceJerseyModule;
import com.gruelbox.tools.dropwizard.guice.services.GuiceServicesModule;

import io.dropwizard.setup.Environment;

/**
 * Standard bindings for {@link GuiceBundle}.
 *
 * @author Graham Crockford
 */
class GuiceBundleModule<T> extends AbstractModule {

  private final Environment environment;
  private final T configuration;

  GuiceBundleModule(Environment environment, T configuration) {
    this.environment = environment;
    this.configuration = configuration;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void configure() {

    bind((Class<T>)configuration.getClass()).toInstance(configuration);
    bind(Environment.class).toInstance(environment);

    Multibinder.newSetBinder(binder(), EnvironmentInitialiser.class);

    install(new GuiceHealthCheckModule());
    install(new GuiceManagedModule());
    install(new GuiceJerseyModule());
    install(new GuiceServicesModule());
  }
}
