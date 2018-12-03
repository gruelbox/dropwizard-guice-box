package com.gruelbox.tools.dropwizard.guice.services;

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


import com.google.common.util.concurrent.Service;

import io.dropwizard.lifecycle.Managed;

/**
 * Allows a {@link Service} to be managed by DropWizard lifecycle.
 */
final class ManagedServiceTask implements Managed {

  private final Service task;

  public ManagedServiceTask(Service task) {
    this.task = task;
  }

  @Override
  public void start() throws Exception {
    task.startAsync().awaitRunning();
  }

  @Override
  public void stop() throws Exception {
    task.stopAsync().awaitTerminated();
  }
}
