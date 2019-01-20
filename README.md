# dropwizard-guice-box

[![Build Status](https://travis-ci.org/gruelbox/dropwizard-guice-box.svg?branch=master)](https://travis-ci.org/gruelbox/dropwizard-guice-box)
[![Sonarcloud Security Rating](https://sonarcloud.io/api/project_badges/measure?project=com.gruelbox%3Adropwizard-guice-box&metric=security_rating)](https://sonarcloud.io/dashboard?id=com.gruelbox%3Adropwizard-guice-box)
[![Sonarcloud Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=com.gruelbox%3Adropwizard-guice-box&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=com.gruelbox%3Adropwizard-guice-box)
[![Sonarcloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.gruelbox%3Adropwizard-guice-box&metric=coverage)](https://sonarcloud.io/dashboard?id=com.gruelbox%3Adropwizard-guice-box)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=com.gruelbox%3Adropwizard-guice-box&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.gruelbox%3Adropwizard-guice-box)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.gruelbox/dropwizard-guice-box/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.gruelbox/dropwizard-guice-box)
[![Javadocs](https://www.javadoc.io/badge/com.gruelbox/dropwizard-guice-box.svg?color=blue)](https://www.javadoc.io/doc/com.gruelbox/dropwizard-guice-box)

A slightly different take on Guice integration with DropWizard, which makes heavy use of multibindings to create a more plugin-like, modular experience.

## Installation

Add the dependency to your POM:

```
<dependency>
  <groupId>com.gruelbox</groupId>
  <artifactId>dropwizard-guice-box</artifactId>
  <version>0.0.2</version>
</dependency>
```

## Usage

```
public class MyApplication extends Application<MyConfiguration> {

  @Inject private SomethingINeed somethingINeed;

  @Override
  public void initialize(final Bootstrap<MyConfiguration> bootstrap) {
    super.initialize(bootstrap);
    bootstrap.addBundle(
      new GuiceBundle<MyConfiguration>(
        this,
        new MyApplicationModule(),
        new MyOtherApplicationModule()
      )
    );
  }

  @Override
  public void run(final MyConfiguration configuration, final Environment environment) {
    somethingINeed.canNowBeUsed();
  }
}
```
 
You now have access to some Guice idiomatic bind points in your `Module`s: 

- Multibindings to `WebResource` provide resources.
- Multibindings to `HealthCheck` provide healthchecks.
- Multibindings to `Managed` provide controlled startup and shutdown of components.
- Multibindings to `Service` provide controlled startup and shutdown of background processes.
- Multibindings to `EnvironmentInitialiser` allow you to hook into the `Application.run` phase directly from anywhere in your code when you need to do anything else on startup. 

All of these can inject as normal, and all except `WebResource` can be package private, aiding encapsulation (unfortunately, JAX-WS prevents this for JAX-RS resources).

The following are provided by default for injection:

- Your application configuration
- The `Environment`
- The `HttpServletRequest` and `HttpServletResponse` during web requests (thanks to `GuiceFilter`).

In addition, any `Module` provided directly to `GuiceBundle` can support the `Configured` interface to gain access to the application configuration during bind time.

## Hibernate

If you're using Dropwizard's Hibernate bundle, you can provide your `@Entity` classes via injection too, which is great for keeping underlying database structure encapsulated in your modules.

Add the dependency:

```
<dependency>
  <groupId>com.gruelbox</groupId>
  <artifactId>dropwizard-guice-box-hibernate</artifactId>
  <version>0.0.2</version>
</dependency>
```

And modify your code to construct and install the `HibernateBundle`, replacing the example `DataSourceFactory` configuration in the example with your requirements:

```
public class MyApplication extends Application<MyConfiguration> {

  @Inject private SomethingINeed somethingINeed;

  @Override
  public void initialize(final Bootstrap<MyConfiguration> bootstrap) {
  
    super.initialize(bootstrap);
    
    HibernateBundleFactory<MyConfiguration> hibernateBundleFactory = new HibernateBundleFactory<>(configuration -> {
      DataSourceFactory dsf = new DataSourceFactory();
      dsf.setDriverClass(configuration.getDriverClass());
      dsf.setUrl(configuration.getJdbcUrl());
      // etc...
      return dsf;
    });
    
    bootstrap.addBundle(
      new GuiceBundle<MyConfiguration>(
        this,
        new MyApplicationModule(),
        new MyOtherApplicationModule(),
        new GuiceHibernateModule(hibernateBundleFactory)
      )
    );
    
    bootstrap.addBundle(hibernateBundleFactory.bundle());
  }

  @Override
  public void run(final MyConfiguration configuration, final Environment environment) {
    somethingINeed.canNowBeUsed();
  }
}
```

You now have access to both `SessionFactory` and `HibernateBundle` via injection, and can multi-bind implementations of `EntityContribution` to provide JPA entities at runtime, as you would with `WebResource`, `Healthcheck` etc.

## Examples

See the [tests](https://github.com/gruelbox/dropwizard-guice-box/tree/master/src/test/java/com/gruelbox/tools/dropwizard/guice/example/simple) for some basic examples.

## Credit

The POM and Travis build borrow heavily from other projects. See [oss-archetype](https://github.com/gruelbox/oss-archetype#credit) for credits.
