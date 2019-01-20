package com.gruelbox.tools.dropwizard.guice.hibernate;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.hibernate.SessionFactory;

import com.google.common.base.MoreObjects;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;
import io.dropwizard.setup.Environment;

/**
 * Constructs a {@link HibernateBundle}, deferring provision of injected
 * resources such as {@link EntityContribution} until after injection.
 *
 * @author Graham Crockford
 *
 * @param <T> The DropWizard configuration type in use in the application.
 */
public class HibernateBundleFactory<T extends Configuration> {

  @Inject private Set<EntityContribution> additionalEntities;
  private final Function<T, DataSourceFactory> configurator;
  private HibernateBundle<T> bundle;

  /**
   * Constructor. Initialise with a lambda which will provide an
   * appropriately configured {@link DataSourceFactory} from the
   * application configuration.
   *
   * @param configurator Returns a configured {@link DataSourceFactory}.
   */
  public HibernateBundleFactory(Function<T, DataSourceFactory> configurator) {
    this.configurator = configurator;
  }

  /**
   * Constructs the deferred-initialisation {@link HibernateBundle} on
   * first request, then returns the same instance each time.
   *
   * @return The bundle.
   */
  public HibernateBundle<T> bundle() {
    return MoreObjects.firstNonNull(bundle, create());
  }

  private HibernateBundle<T> create() {

    // Defer collecting the entity list until after the creation of the injector
    // since otherwise the Application would have to know about all the entities
    // in use throughout the application.
    SessionFactoryFactory sessionFactoryFactory = new SessionFactoryFactory() {
      @Override
      public SessionFactory build(HibernateBundle<?> bundle,
          Environment environment,
          PooledDataSourceFactory dbConfig,
          ManagedDataSource dataSource,
          List<Class<?>> entities) {
        return super.build(
          bundle, environment, dbConfig, dataSource,
          FluentIterable.concat(entities, FluentIterable.from(additionalEntities).transformAndConcat(EntityContribution::getEntities)).toList()
        );
      }
    };

    // Construct the bundle, without excessive knowledge of the application
    // configuration
    bundle = new HibernateBundle<T>(ImmutableList.of(), sessionFactoryFactory) {
      @Override
      public DataSourceFactory getDataSourceFactory(T configuration) {
        return configurator.apply(configuration);
      }
    };

    return bundle;
  }
}
