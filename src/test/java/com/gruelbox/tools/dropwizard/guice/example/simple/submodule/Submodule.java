package com.gruelbox.tools.dropwizard.guice.example.simple.submodule;

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


import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.multibindings.Multibinder;
import com.gruelbox.tools.dropwizard.guice.EnvironmentInitialiser;

/**
 * Encapsulated module which hooks into application startup to add
 * a servlet filter.
 *
 * @author Graham Crockford
 */
public class Submodule implements Module {

  @Override
  public void configure(Binder binder) {
    Multibinder.newSetBinder(binder, EnvironmentInitialiser.class)
      .addBinding().to(SubmoduleEnvironment.class);
  }

}
