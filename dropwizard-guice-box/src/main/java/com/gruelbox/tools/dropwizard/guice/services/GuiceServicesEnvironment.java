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
package com.gruelbox.tools.dropwizard.guice.services;

import com.google.common.util.concurrent.Service;
import com.gruelbox.tools.dropwizard.guice.EnvironmentInitialiser;
import io.dropwizard.core.setup.Environment;
import jakarta.inject.Inject;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class GuiceServicesEnvironment implements EnvironmentInitialiser {

  private static final Logger LOGGER = LoggerFactory.getLogger(GuiceServicesEnvironment.class);

  private final Set<Service> services;

  @Inject
  GuiceServicesEnvironment(Set<Service> services) {
    this.services = services;
  }

  @Override
  public void init(Environment environment) {
    services.stream()
        .peek(t -> LOGGER.debug("Starting service {}", t))
        .map(ManagedServiceTask::new)
        .forEach(environment.lifecycle()::manage);
  }
}
