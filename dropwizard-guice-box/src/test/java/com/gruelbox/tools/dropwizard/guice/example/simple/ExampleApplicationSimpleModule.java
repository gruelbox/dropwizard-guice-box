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

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.ext.ExceptionMapper;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.gruelbox.tools.dropwizard.guice.example.simple.submodule.Submodule;
import com.gruelbox.tools.dropwizard.guice.resources.WebResource;

final class ExampleApplicationSimpleModule extends AbstractModule {
  @Override
  protected void configure() {
    super.configure();
    install(new Submodule());
    Multibinder.newSetBinder(binder(), WebResource.class)
      .addBinding().to(ExampleResource.class);
    Multibinder.newSetBinder(binder(), ExceptionMapper.class)
      .addBinding().to(ExampleExceptionMapper.class);
  }

  @Provides
  @Named("something")
  @Singleton
  String something() {
    return "awful";
  }
}
