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
package com.gruelbox.tools.dropwizard.guice.example.base;

import com.google.common.annotations.VisibleForTesting;
import com.gruelbox.tools.dropwizard.guice.GuiceBundle;
import com.gruelbox.tools.dropwizard.guice.example.ExampleConfiguration;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base case. Absolutely minimal configuration to ensure that there are no assumptions that the
 * injector have to do anything.
 *
 * @author Graham Crockford
 */
@SuppressWarnings("WeakerAccess")
public class ExampleApplicationBase extends Application<ExampleConfiguration> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExampleApplicationBase.class);

  @VisibleForTesting
  @SuppressWarnings("WeakerAccess")
  public static final String APP_NAME = "Base example";

  public ExampleApplicationBase() {
    // No-op
  }

  @Override
  public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {
    LOGGER.info("Initialising");
    super.initialize(bootstrap);
    bootstrap.addBundle(new GuiceBundle<>(this));
  }

  @Override
  public String getName() {
    return APP_NAME;
  }

  @Override
  public void run(ExampleConfiguration configuration, Environment environment) {
    LOGGER.info("Starting");
  }
}
