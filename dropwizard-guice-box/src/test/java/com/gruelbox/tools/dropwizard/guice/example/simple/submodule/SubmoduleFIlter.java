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
package com.gruelbox.tools.dropwizard.guice.example.simple.submodule;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.IOException;
import javax.inject.Named;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

@Singleton
class SubmoduleFilter implements Filter {

  private final String something;

  @Inject
  SubmoduleFilter(@Named("something") String something) {
    this.something = something;
  }

  @Override
  public void init(FilterConfig filterConfig) {
    // No-op
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    ((HttpServletResponse) response).setHeader("submodule-response", something);
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    // No-op
  }
}
