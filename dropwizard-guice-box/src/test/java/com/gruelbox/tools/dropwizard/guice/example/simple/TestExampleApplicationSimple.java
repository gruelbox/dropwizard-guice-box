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
