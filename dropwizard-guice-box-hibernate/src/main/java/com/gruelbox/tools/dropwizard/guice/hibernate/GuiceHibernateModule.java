package com.gruelbox.tools.dropwizard.guice.hibernate;

import org.hibernate.SessionFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;

import io.dropwizard.hibernate.HibernateBundle;

public class GuiceHibernateModule extends AbstractModule {

  private final HibernateBundleFactory<?> hibernateBundleFactory;

  public GuiceHibernateModule(HibernateBundleFactory<?> hibernateBundleFactory) {
    super();
    this.hibernateBundleFactory = hibernateBundleFactory;
  }

  @Override
  protected void configure() {
    Multibinder.newSetBinder(binder(), EntityContribution.class);
    binder().requestInjection(hibernateBundleFactory);
    binder().bind(HibernateBundle.class).toInstance(hibernateBundleFactory.bundle());
  }

  @Provides
  SessionFactory sessionFactory(@SuppressWarnings("rawtypes") HibernateBundle hibernateBundle) {
    return hibernateBundle.getSessionFactory();
  }
}
