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
package com.gruelbox.tools.dropwizard.guice;

import com.google.inject.Module;
import com.gruelbox.tools.dropwizard.guice.resources.WebResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Any Guice {@link Module} directly provided to {@link GuiceBundle} during the {@link
 * Application#initialize(io.dropwizard.setup.Bootstrap)} phase of startup which supports this
 * interface will be called back during the {@link Application#run(io.dropwizard.Configuration,
 * io.dropwizard.setup.Environment)} phase, but prior to creation of the injector, to provide it
 * with the configuration.
 *
 * <p>Note that this should <em>only</em> be used where conditional bindings are required - for
 * example where you only want to make certain {@link WebResource}s available, depending on
 * configuration.
 *
 * <p>For all other cases, remember that the configuration is bound and can be injected at runtime
 * without needing to pass it down the stack of {@link Module}s during injector construction.
 *
 * <p>Note that other startup resources such as {@link Bootstrap} and {@link Environment} are
 * deliberately not included here. The {@code Environment} is explicitly a {@code run}-time resource
 * and should be injected, and {@code Bootstrap} can be accessed at the same time as creating the
 * {@link GuiceBundle} in {@link Application#initialize(Bootstrap)}.
 *
 * @author Graham Crockford
 * @param <T> The application configuration class.
 */
@SuppressWarnings("WeakerAccess")
public interface Configured<T> {

  /**
   * Provides the configuration to a supporting {@link Module} prior to injector creation.
   *
   * @param configuration The application configuration.
   */
  void setConfiguration(T configuration);
}
