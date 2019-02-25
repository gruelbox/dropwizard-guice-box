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
