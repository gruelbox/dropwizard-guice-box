# dropwizard-guice-box

[![Build Status](https://travis-ci.org/gruelbox/dropwizard-guice-box.svg?branch=master)](https://travis-ci.org/gruelbox/dropwizard-guice-box)
[![CodeFactor](https://www.codefactor.io/repository/github/gruelbox/dropwizard-guice-box/badge)](https://www.codefactor.io/repository/github/gruelbox/dropwizard-guice-box)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.gruelbox/dropwizard-guice-box/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.gruelbox/dropwizard-guice-box)
[![Javadocs](https://www.javadoc.io/badge/com.gruelbox/dropwizard-guice-box.svg?color=blue)](https://www.javadoc.io/doc/com.gruelbox/dropwizard-guice-box)

A slightly different take on Guice integration with DropWizard, which makes heavy use of multibindings to create a more plugin-like, modular experience.

## Installation

Check the [latest release](https://github.com/gruelbox/dropwizard-guice-box/releases) and add the dependency to your POM:

```
<dependency>
  <groupId>com.gruelbox</groupId>
  <artifactId>dropwizard-guice-box</artifactId>
  <version>latest release version</version>
</dependency>
```

All releases from 1.0.0 onwards are [semantically versioned](https://semver.org/). You can safely take any release incrementing the minor or patch versions without worrying about compatibility.

## Usage

```
public class MyApplication extends Application<MyConfiguration> {

  // Optional - you can inject directly into your Application to get access
  // to injected assets in the run() method.
  @Inject private SomethingINeed somethingINeed;

  @Override
  public void initialize(final Bootstrap<MyConfiguration> bootstrap) {
    super.initialize(bootstrap);
    
    // Any modules listed here can implement Configured<MyConfiguration> to get access to the
    // configuration during the injector build.
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
    // You can access injected assets here if you wish, however, multi-binding
    // an EnvironmentInitialiser is a cleaner way to do this, allowing your code to
    // be more modular.
    somethingINeed.canNowBeUsed();
  }
}
```
 
You now have access to some Guice idiomatic bind points in your `Module`s: 

- Multibindings to `WebResource` provide resources.
- Multibindings to `HealthCheck` provide healthchecks.
- Multibindings to `ExceptionMapper` allow you to map exceptions from your endpoints to HTTP responses.
- Multibindings to `Task` to provide administration tasks.
- Multibindings to `Managed` provide controlled startup and shutdown of components.
- Multibindings to `Service` provide controlled startup and shutdown of background processes.
- Multibindings to `EnvironmentInitialiser` allow you to hook into the `Application.run` phase directly from anywhere in your code when you need to do anything else on startup, without having to add code to `Application.run` directly.

All of these can inject as normal, and all except `WebResource` can be package private, aiding encapsulation (unfortunately, JAX-WS prevents this for JAX-RS resources).

The following are provided by default for injection anywhere:

- Your application configuration
- The `Environment`
- The `HttpServletRequest` and `HttpServletResponse` during web requests (thanks to `GuiceFilter`), if you install `new ServletModule()` during `Injector` creation.

In addition, any `Module` provided directly to `GuiceBundle` can support the `Configured` interface to gain access to the application configuration during bind time. This can be helpful where your configuration drives the choice of bindings.

## Hibernate

If you're using Dropwizard's Hibernate bundle, you can provide your `@Entity` classes via injection too, which is great for keeping underlying database structure encapsulated in your modules.

Add the dependency:

```
<dependency>
  <groupId>com.gruelbox</groupId>
  <artifactId>dropwizard-guice-box-hibernate</artifactId>
  <version>1.1.0</version>
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

See the [tests](https://github.com/gruelbox/dropwizard-guice-box/tree/master/dropwizard-guice-box/src/test/java/com/gruelbox/tools/dropwizard/guice/example/simple) for some basic examples.
