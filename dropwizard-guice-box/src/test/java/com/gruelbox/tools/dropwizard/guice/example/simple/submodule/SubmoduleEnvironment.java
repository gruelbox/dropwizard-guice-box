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
package com.gruelbox.tools.dropwizard.guice.example.simple.submodule;

import com.google.inject.Inject;
import com.gruelbox.tools.dropwizard.guice.EnvironmentInitialiser;
import io.dropwizard.core.setup.Environment;

class SubmoduleEnvironment implements EnvironmentInitialiser {

  private final SubmoduleFilter filter;

  @Inject
  SubmoduleEnvironment(SubmoduleFilter filter) {
    this.filter = filter;
  }

  @Override
  public void init(Environment environment) {
    environment
        .servlets()
        .addFilter(SubmoduleFilter.class.getSimpleName(), filter)
        .addMappingForUrlPatterns(null, true, "/*");
  }
}
