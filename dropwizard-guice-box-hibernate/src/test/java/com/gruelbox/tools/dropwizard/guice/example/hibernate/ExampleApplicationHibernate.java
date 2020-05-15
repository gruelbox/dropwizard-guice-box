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
package com.gruelbox.tools.dropwizard.guice.example.hibernate;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.gruelbox.tools.dropwizard.guice.GuiceBundle;
import com.gruelbox.tools.dropwizard.guice.hibernate.GuiceHibernateModule;
import com.gruelbox.tools.dropwizard.guice.hibernate.HibernateBundleFactory;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.util.Duration;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.H2Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base case. Absolutely minimal configuration to ensure that there are no assumptions that the
 * injector have to do anything.
 *
 * @author Graham Crockford
 */
@SuppressWarnings("WeakerAccess")
public class ExampleApplicationHibernate extends Application<ExampleConfiguration> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExampleApplicationHibernate.class);

  @VisibleForTesting
  @SuppressWarnings("WeakerAccess")
  static final String APP_NAME = "Base example";

  public ExampleApplicationHibernate() {
    // No-op
  }

  @Override
  public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {
    LOGGER.info("Initialising");
    super.initialize(bootstrap);

    HibernateBundleFactory<ExampleConfiguration> hibernateBundleFactory =
        new HibernateBundleFactory<>(
            configuration -> {
              DataSourceFactory dsf = new DataSourceFactory();
              dsf.setDriverClass("org.h2.Driver");
              dsf.setUrl("jdbc:h2:mem:test");
              dsf.setProperties(
                  ImmutableMap.of(
                      "charset",
                      "UTF-8",
                      "hibernate.dialect",
                      H2Dialect.class.getName(),
                      AvailableSettings.LOG_SESSION_METRICS,
                      "false"));
              dsf.setMaxWaitForConnection(Duration.seconds(1));
              dsf.setValidationQuery("/* Health Check */ SELECT 1");
              dsf.setMinSize(1);
              dsf.setMaxSize(4);
              dsf.setCheckConnectionWhileIdle(false);
              return dsf;
            });

    bootstrap.addBundle(new GuiceBundle<>(this, new GuiceHibernateModule(hibernateBundleFactory)));

    bootstrap.addBundle(hibernateBundleFactory.bundle());
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
