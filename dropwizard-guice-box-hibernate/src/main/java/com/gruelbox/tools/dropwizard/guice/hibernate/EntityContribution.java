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
