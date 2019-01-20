package com.gruelbox.tools.dropwizard.guice;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

/**
 * Multi-bind classes implementing this interface to access the
 * {@link Environment} during the
 * {@link Application#run(io.dropwizard.Configuration, Environment)} phase of
 * application startup.
 *
 * <p>
 * You may inject the application configuration if you need it. The
 * {@link Environment} is also available for injection, but is provided here for
 * convenience and historical reasons.
 * </p>
 *
 * @author Graham Crockford
 */
public interface EnvironmentInitialiser {

  /**
   * Called during application startup and allows code to modify the
   * {@link Environment}.
   *
   * @param environment The application {@link Environment}.
   */
  public void init(Environment environment);

}
