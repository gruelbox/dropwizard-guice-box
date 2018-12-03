package com.gruelbox.tools.dropwizard.guice.example.simple.submodule;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.multibindings.Multibinder;
import com.gruelbox.tools.dropwizard.guice.EnvironmentInitialiser;

/**
 * Encapsulated module which hooks into application startup to add
 * a servlet filter.
 *
 * @author Graham Crockford
 */
public class Submodule implements Module {

  @Override
  public void configure(Binder binder) {
    Multibinder.newSetBinder(binder, EnvironmentInitialiser.class)
      .addBinding().to(SubmoduleEnvironment.class);
  }

}
