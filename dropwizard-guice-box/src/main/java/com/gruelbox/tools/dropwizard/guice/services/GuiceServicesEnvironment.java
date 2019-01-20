package com.gruelbox.tools.dropwizard.guice.services;

import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.Service;
import com.gruelbox.tools.dropwizard.guice.EnvironmentInitialiser;

import io.dropwizard.setup.Environment;

class GuiceServicesEnvironment implements EnvironmentInitialiser {

  private static final Logger LOGGER = LoggerFactory.getLogger(GuiceServicesEnvironment.class);

  @Inject private Set<Service> services;

  @Override
  public void init(Environment environment) {
    services.stream()
      .peek(t -> LOGGER.info("Starting service {}", t))
      .map(ManagedServiceTask::new)
      .forEach(environment.lifecycle()::manage);
  }
}
