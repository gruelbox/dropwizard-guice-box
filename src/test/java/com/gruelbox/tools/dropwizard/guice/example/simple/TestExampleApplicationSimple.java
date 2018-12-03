package com.gruelbox.tools.dropwizard.guice.example.simple;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Test;

import com.gruelbox.tools.dropwizard.guice.example.ExampleConfiguration;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

public class TestExampleApplicationSimple {

  @ClassRule
  public static final DropwizardAppRule<ExampleConfiguration> RULE =
    new DropwizardAppRule<>(
        ExampleApplicationSimple.class,
        ResourceHelpers.resourceFilePath("example-configuration.yml")
    );

  @Test
  public void testResource() {
    Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("test client");

    assertEquals(
      ExampleApplicationSimple.APP_NAME,
      client.target(
        String.format("http://localhost:%d/example/name", RULE.getLocalPort()))
       .request()
       .get(String.class)
    );

    assertEquals(
      "Hello world",
      client.target(
        String.format("http://localhost:%d/example/configProperty", RULE.getLocalPort()))
       .request()
       .get(String.class)
    );

    Response response = client.target(
        String.format("http://localhost:%d/example/configProperty", RULE.getLocalPort()))
       .request().get();
    assertEquals("awful", response.getHeaderString("submodule-response"));
  }
}