package com.gruelbox.tools.dropwizard.guice.example.simple.submodule;

import com.google.inject.Inject;
import com.gruelbox.tools.dropwizard.guice.EnvironmentInitialiser;

import io.dropwizard.setup.Environment;

class SubmoduleEnvironment implements EnvironmentInitialiser {

  private final SubmoduleFilter filter;

  @Inject
  SubmoduleEnvironment(SubmoduleFilter filter) {
    this.filter = filter;
  }

  @Override
  public void init(Environment environment) {
    environment.servlets().addFilter(SubmoduleFilter.class.getSimpleName(), filter)
      .addMappingForUrlPatterns(null, true, "/*");
  }
}
