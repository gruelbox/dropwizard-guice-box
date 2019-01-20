package com.gruelbox.tools.dropwizard.guice.hibernate;

import javax.persistence.Entity;

import org.hibernate.SessionFactory;

/**
 * Set bind instnces of {@link EntityContribution} to provide
 * {@link Entity}-annotated classes to the Hibernate {@link SessionFactory} on
 * startup.
 *
 * @author Graham Crockford
 */
public interface EntityContribution {

  /**
   * @return Entity classes to be included in {@link SessionFactory} startup.
   */
  Iterable<Class<?>> getEntities();

}
