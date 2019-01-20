package com.gruelbox.tools.dropwizard.guice.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.gruelbox.tools.dropwizard.guice.EnvironmentInitialiser;

public class GuiceHealthCheckModule extends AbstractModule {
  @Override
  protected void configure() {
    Multibinder.newSetBinder(binder(), HealthCheck.class);
    Multibinder.newSetBinder(binder(), EnvironmentInitialiser.class)
      .addBinding().to(GuiceHealthCheckEnvironment.class);
  }
}
