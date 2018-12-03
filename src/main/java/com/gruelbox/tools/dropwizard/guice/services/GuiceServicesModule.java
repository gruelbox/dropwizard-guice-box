package com.gruelbox.tools.dropwizard.guice.services;

import com.google.common.util.concurrent.Service;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.gruelbox.tools.dropwizard.guice.EnvironmentInitialiser;

public class GuiceServicesModule extends AbstractModule {
  @Override
  protected void configure() {
    Multibinder.newSetBinder(binder(), Service.class);
    Multibinder.newSetBinder(binder(), EnvironmentInitialiser.class)
      .addBinding().to(GuiceServicesEnvironment.class);
  }
}