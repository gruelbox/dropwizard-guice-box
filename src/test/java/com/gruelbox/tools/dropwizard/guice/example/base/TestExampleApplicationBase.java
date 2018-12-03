package com.gruelbox.tools.dropwizard.guice.example.base;

import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gruelbox.tools.dropwizard.guice.example.ExampleConfiguration;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

/**
 * Base case. Absolutely minimal configuration to ensure that there are
 * no assumptions that the injector have to do anything.
 *
 * @author Graham Crockford
 */
public class TestExampleApplicationBase {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestExampleApplicationBase.class);

  @ClassRule
  public static final DropwizardAppRule<ExampleConfiguration> RULE =
    new DropwizardAppRule<>(
        ExampleApplicationBase.class,
        ResourceHelpers.resourceFilePath("example-configuration.yml")
    );

  @Test
  public void testStartupShutdown() {
    // No-op. We just want to make sure it starts.
    LOGGER.info("Started");
  }
}
