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
package com.gruelbox.tools.dropwizard.guice.example.simple;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gruelbox.tools.dropwizard.guice.example.ExampleConfiguration;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
public class TestExampleApplicationSimple {

  private static final DropwizardAppExtension<ExampleConfiguration> RULE =
      new DropwizardAppExtension<>(
          ExampleApplicationSimple.class,
          ResourceHelpers.resourceFilePath("example-configuration.yml"));

  private static Client client;

  @BeforeAll
  public static void before() {
    client = new JerseyClientBuilder(RULE.getEnvironment()).build("test client");
  }

  @AfterAll
  public static void after() {
    client.close();
  }

  @Test
  public void testResource() {
    assertEquals(
        ExampleApplicationSimple.APP_NAME,
        client
            .target(String.format("http://localhost:%d/example/name", RULE.getLocalPort()))
            .request()
            .get(String.class));

    assertEquals(
        "Hello world",
        client
            .target(
                String.format("http://localhost:%d/example/configProperty", RULE.getLocalPort()))
            .request()
            .get(String.class));

    Response response =
        client
            .target(
                String.format("http://localhost:%d/example/configProperty", RULE.getLocalPort()))
            .request()
            .get();
    assertEquals("awful", response.getHeaderString("submodule-response"));
  }

  @Test
  public void testExceptionMapper() {
    assertEquals(
        Status.FORBIDDEN.getStatusCode(),
        client
            .target(String.format("http://localhost:%d/example/unsupported", RULE.getLocalPort()))
            .request()
            .get()
            .getStatus());
  }

  @Test
  public void testTask() {
    assertEquals(
        "Foo",
        client
            .target(String.format("http://localhost:%d/tasks/mytask", RULE.getAdminPort()))
            .request()
            .post(Entity.text(""), String.class));
  }
}
