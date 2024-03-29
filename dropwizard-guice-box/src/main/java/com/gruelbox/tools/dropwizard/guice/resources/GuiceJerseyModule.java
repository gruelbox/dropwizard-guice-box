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

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.gruelbox.tools.dropwizard.guice.EnvironmentInitialiser;
import io.dropwizard.servlets.tasks.Task;
import jakarta.ws.rs.ext.ExceptionMapper;

public class GuiceJerseyModule extends AbstractModule {
  @Override
  protected void configure() {
    Multibinder.newSetBinder(binder(), WebResource.class);
    Multibinder.newSetBinder(binder(), new TypeLiteral<ExceptionMapper<?>>() {});
    Multibinder.newSetBinder(binder(), ExceptionMapper.class);
    Multibinder.newSetBinder(binder(), Task.class);
    Multibinder.newSetBinder(binder(), EnvironmentInitialiser.class)
        .addBinding()
        .to(GuiceJerseyEnvironment.class);
  }
}
