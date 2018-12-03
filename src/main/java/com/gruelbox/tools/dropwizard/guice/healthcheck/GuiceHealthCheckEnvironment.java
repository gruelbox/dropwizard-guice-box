package com.gruelbox.tools.dropwizard.guice.healthcheck;

import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.health.HealthCheck;
import com.gruelbox.tools.dropwizard.guice.EnvironmentInitialiser;

import io.dropwizard.setup.Environment;

class GuiceHealthCheckEnvironment implements EnvironmentInitialiser {

  private static final Logger LOGGER = LoggerFactory.getLogger(GuiceHealthCheckEnvironment.class);

  @Inject private Set<HealthCheck> healthChecks;

  @Override
  public void init(Environment environment) {
    healthChecks.stream()
      .peek(t -> LOGGER.info("Registering health check {}", t))
      .forEach(t -> environment.healthChecks().register(t.getClass().getSimpleName(), t));
  }
}