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
package com.gruelbox.tools.dropwizard.guice.example.hibernate;

import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

/**
 * Base case. Absolutely minimal configuration to ensure that there are
 * no assumptions that the injector have to do anything.
 *
 * @author Graham Crockford
 */
public class TestExampleApplicationHibernate {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestExampleApplicationHibernate.class);

  @ClassRule
  public static final DropwizardAppRule<ExampleConfiguration> RULE =
    new DropwizardAppRule<>(
        ExampleApplicationHibernate.class,
        ResourceHelpers.resourceFilePath("example-configuration.yml")
    );

  @Test
  public void testStartupShutdown() {
    // No-op. We just want to make sure it starts.
    LOGGER.info("Started");
  }
}
