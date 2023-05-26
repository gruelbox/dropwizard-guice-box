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
package com.gruelbox.tools.dropwizard.guice.resources;

import com.google.common.collect.Sets;
import com.gruelbox.tools.dropwizard.guice.EnvironmentInitialiser;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.servlets.tasks.Task;
import jakarta.inject.Inject;
import jakarta.ws.rs.ext.ExceptionMapper;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class GuiceJerseyEnvironment implements EnvironmentInitialiser {

  private static final Logger LOGGER = LoggerFactory.getLogger(GuiceJerseyEnvironment.class);

  private final Set<WebResource> webResources;
  private final Set<ExceptionMapper<?>> exceptionMappers;
  private final Set<Task> tasks;

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Inject
  GuiceJerseyEnvironment(
      Set<WebResource> webResources,
      Set<ExceptionMapper<?>> exceptionMappers,
      Set<ExceptionMapper> exceptionMappersRaw,
      Set<Task> tasks) {
    this.webResources = webResources;
    this.exceptionMappers = Sets.union(exceptionMappers, (Set) exceptionMappersRaw);
    this.tasks = tasks;
  }

  @Override
  public void init(Environment environment) {
    webResources.stream()
        .peek(t -> LOGGER.debug("Registering resource {}", t))
        .forEach(environment.jersey()::register);
    exceptionMappers.stream()
        .peek(t -> LOGGER.debug("Registering exception mapper {}", t))
        .forEach(environment.jersey()::register);
    tasks.stream()
        .peek(t -> LOGGER.debug("Registering task {}", t))
        .forEach(environment.admin()::addTask);
  }
}
