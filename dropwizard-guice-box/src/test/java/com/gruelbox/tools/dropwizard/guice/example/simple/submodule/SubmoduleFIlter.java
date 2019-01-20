package com.gruelbox.tools.dropwizard.guice.example.simple.submodule;

import java.io.IOException;

import javax.inject.Named;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
class SubmoduleFilter implements Filter {

  private final String something;

  @Inject
  SubmoduleFilter(@Named("something") String something) {
    this.something = something;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // No-op
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    ((HttpServletResponse)response).setHeader("submodule-response", something);
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    // No-op
  }
}
