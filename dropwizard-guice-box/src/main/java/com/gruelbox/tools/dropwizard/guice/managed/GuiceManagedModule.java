package com.gruelbox.tools.dropwizard.guice.managed;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.gruelbox.tools.dropwizard.guice.EnvironmentInitialiser;

import io.dropwizard.lifecycle.Managed;

public class GuiceManagedModule extends AbstractModule {
  @Override
  protected void configure() {
    Multibinder.newSetBinder(binder(), Managed.class);
    Multibinder.newSetBinder(binder(), EnvironmentInitialiser.class)
      .addBinding().to(GuiceManagedEnvironment.class);
  }
}
