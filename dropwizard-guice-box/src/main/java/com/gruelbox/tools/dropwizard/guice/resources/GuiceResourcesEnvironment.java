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

import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gruelbox.tools.dropwizard.guice.EnvironmentInitialiser;

import io.dropwizard.setup.Environment;

class GuiceResourcesEnvironment implements EnvironmentInitialiser {

  private static final Logger LOGGER = LoggerFactory.getLogger(GuiceResourcesEnvironment.class);

  @Inject private Set<WebResource> webResources;

  @Override
  public void init(Environment environment) {
    webResources.stream()
      .peek(t -> LOGGER.debug("Registering resource {}", t))
      .forEach(environment.jersey()::register);
  }
}
