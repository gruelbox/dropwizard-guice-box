package com.gruelbox.tools.dropwizard.guice.example.simple;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;
import com.gruelbox.tools.dropwizard.guice.GuiceBundle;
import com.gruelbox.tools.dropwizard.guice.example.ExampleConfiguration;
import com.gruelbox.tools.dropwizard.guice.resources.WebResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Tests that we can inject into the {@link Application} and create
 * a {@link WebResource}.
 *
 * @author Graham Crockford
 */
public class ExampleApplicationSimple extends Application<ExampleConfiguration> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExampleApplicationSimple.class);

  @VisibleForTesting
  static final String APP_NAME = "Simple example";

  // Anything bound can be injected to be available at the start of
  // the run method.
  @Inject
  @Named("something")
  private String something;

  public ExampleApplicationSimple() {
    // No-op
  }

  @Override
  public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {
    LOGGER.info("Initialising");
    super.initialize(bootstrap);
    bootstrap.addBundle(
      new GuiceBundle<ExampleConfiguration>(this, new ExampleApplicationSimpleModule())
    );
  }

  @Override
  public String getName() {
    return APP_NAME;
  }

  @Override
  public void run(ExampleConfiguration configuration, Environment environment) throws Exception {

    // Use injected resources
    assertEquals("awful", something);
    LOGGER.info("Starting something " + something);

  }
}