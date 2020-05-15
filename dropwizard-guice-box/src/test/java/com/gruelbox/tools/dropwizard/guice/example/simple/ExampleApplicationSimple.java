/**
 * dropwizard-guice-box Copyright 2018-2019 Graham Crockford
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gruelbox.tools.dropwizard.guice.example.simple;

import static org.junit.Assert.assertEquals;

import com.google.common.annotations.VisibleForTesting;
import com.gruelbox.tools.dropwizard.guice.GuiceBundle;
import com.gruelbox.tools.dropwizard.guice.example.ExampleConfiguration;
import com.gruelbox.tools.dropwizard.guice.resources.WebResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests that we can inject into the {@link Application} and create a {@link WebResource}.
 *
 * @author Graham Crockford
 */
@SuppressWarnings("WeakerAccess")
public class ExampleApplicationSimple extends Application<ExampleConfiguration> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExampleApplicationSimple.class);

  @VisibleForTesting
  @SuppressWarnings("WeakerAccess")
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
    bootstrap.addBundle(new GuiceBundle<>(this, new ExampleApplicationSimpleModule()));
  }

  @Override
  public String getName() {
    return APP_NAME;
  }

  @Override
  public void run(ExampleConfiguration configuration, Environment environment) {

    // Use injected resources
    assertEquals("awful", something);
    LOGGER.info("Starting something " + something);
  }
}
