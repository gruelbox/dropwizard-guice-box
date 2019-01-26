package com.gruelbox.tools.dropwizard.guice.managed;

import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gruelbox.tools.dropwizard.guice.EnvironmentInitialiser;

import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;

class GuiceManagedEnvironment implements EnvironmentInitialiser {

  private static final Logger LOGGER = LoggerFactory.getLogger(GuiceManagedEnvironment.class);

  @Inject private Set<Managed> managedTasks;

  @Override
  public void init(Environment environment) {
    managedTasks.stream()
      .peek(t -> LOGGER.debug("Starting managed task {}", t))
      .forEach(environment.lifecycle()::manage);
  }
}
