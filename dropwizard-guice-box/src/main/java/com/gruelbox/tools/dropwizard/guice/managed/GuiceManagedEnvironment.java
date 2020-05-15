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
package com.gruelbox.tools.dropwizard.guice.managed;

import com.gruelbox.tools.dropwizard.guice.EnvironmentInitialiser;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import java.util.Set;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class GuiceManagedEnvironment implements EnvironmentInitialiser {

  private static final Logger LOGGER = LoggerFactory.getLogger(GuiceManagedEnvironment.class);

  private final Set<Managed> managedTasks;

  @Inject
  GuiceManagedEnvironment(Set<Managed> managedTasks) {
    this.managedTasks = managedTasks;
  }

  @Override
  public void init(Environment environment) {
    managedTasks.stream()
        .peek(t -> LOGGER.debug("Starting managed task {}", t))
        .forEach(environment.lifecycle()::manage);
  }
}
