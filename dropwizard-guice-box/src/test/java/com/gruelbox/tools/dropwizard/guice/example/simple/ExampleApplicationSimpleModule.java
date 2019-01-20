package com.gruelbox.tools.dropwizard.guice.example.simple;

import javax.inject.Named;
import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.gruelbox.tools.dropwizard.guice.example.simple.submodule.Submodule;
import com.gruelbox.tools.dropwizard.guice.resources.WebResource;

final class ExampleApplicationSimpleModule extends AbstractModule {
  @Override
  protected void configure() {
    super.configure();
    install(new Submodule());
    Multibinder.newSetBinder(binder(), WebResource.class)
      .addBinding().to(ExampleResource.class);
  }

  @Provides
  @Named("something")
  @Singleton
  String something() {
    return "awful";
  }
}
