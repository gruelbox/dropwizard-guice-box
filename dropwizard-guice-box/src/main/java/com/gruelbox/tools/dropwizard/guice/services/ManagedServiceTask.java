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
package com.gruelbox.tools.dropwizard.guice.services;

import com.google.common.util.concurrent.Service;
import io.dropwizard.lifecycle.Managed;

/** Allows a {@link Service} to be managed by DropWizard lifecycle. */
final class ManagedServiceTask implements Managed {

  private final Service task;

  public ManagedServiceTask(Service task) {
    this.task = task;
  }

  @Override
  public void start() {
    task.startAsync().awaitRunning();
  }

  @Override
  public void stop() {
    task.stopAsync().awaitTerminated();
  }
}
