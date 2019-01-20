package com.gruelbox.tools.dropwizard.guice.example.simple;


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
