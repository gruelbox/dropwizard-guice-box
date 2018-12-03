package com.gruelbox.tools.dropwizard.guice;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.gruelbox.tools.dropwizard.guice.healthcheck.GuiceHealthCheckModule;
import com.gruelbox.tools.dropwizard.guice.managed.GuiceManagedModule;
import com.gruelbox.tools.dropwizard.guice.resources.GuiceResourcesModule;
import com.gruelbox.tools.dropwizard.guice.services.GuiceServicesModule;

import io.dropwizard.setup.Environment;

/**
 * Standard bindings for {@link GuiceBundle}.
 *
 * @author Graham Crockford
 */
class GuiceBundleModule extends AbstractModule {

  private final Environment environment;

  GuiceBundleModule(Environment environment) {
    this.environment = environment;
  }

  @Override
  protected void configure() {
    bind(Environment.class).toInstance(environment);
    Multibinder.newSetBinder(binder(), EnvironmentInitialiser.class);
    install(new GuiceHealthCheckModule());
    install(new GuiceManagedModule());
    install(new GuiceResourcesModule());
    install(new GuiceServicesModule());
  }
}