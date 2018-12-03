package com.gruelbox.tools.dropwizard.guice.example.simple;

/*-
 * ===============================================================================L
 * com.gruelbox:dropwizard-guice-box
 * ================================================================================
 * Copyright (C) 2018 Gruelbox
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ===============================================================================E
 */


import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.gruelbox.tools.dropwizard.guice.example.ExampleConfiguration;
import com.gruelbox.tools.dropwizard.guice.resources.WebResource;

import io.dropwizard.setup.Environment;

@Path("/example")
@Singleton
public final class ExampleResource implements WebResource {

  private final Environment environment;
  private final ExampleConfiguration configuration;

  @Inject
  ExampleResource(Environment environment, ExampleConfiguration configuration) {
    this.environment = environment;
    this.configuration = configuration;
  }

  @GET
  @Timed
  @Path("/name")
  @Produces(MediaType.TEXT_PLAIN)
  public String name() {
    return environment.getName();
  }

  @GET
  @Timed
  @Path("/configProperty")
  @Produces(MediaType.TEXT_PLAIN)
  public String configProperty() {
    return configuration.getProperty1();
  }
}
