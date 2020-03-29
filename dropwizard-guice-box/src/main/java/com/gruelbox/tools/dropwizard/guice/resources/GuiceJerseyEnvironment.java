/**
 * dropwizard-guice-box
 * Copyright 2018-2019 Graham Crockford
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gruelbox.tools.dropwizard.guice.resources;

import io.dropwizard.servlets.tasks.Task;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Streams;
import com.gruelbox.tools.dropwizard.guice.EnvironmentInitialiser;

import io.dropwizard.setup.Environment;

class GuiceJerseyEnvironment implements EnvironmentInitialiser {

  private static final Logger LOGGER = LoggerFactory.getLogger(GuiceJerseyEnvironment.class);

  private final Set<WebResource> webResources;

  @SuppressWarnings("rawtypes")
  private final Set<ExceptionMapper> exceptionMappers;
  private final Set<Task> tasks;

  @Inject
  GuiceJerseyEnvironment(Set<WebResource> webResources,
                         Set<ExceptionMapper> exceptionMappers,
                         Set<Task> tasks) {
    this.webResources = webResources;
    this.exceptionMappers = exceptionMappers;
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
