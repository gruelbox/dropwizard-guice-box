package com.gruelbox.tools.dropwizard.guice.example.simple;

import io.dropwizard.servlets.tasks.Task;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

class ExampleTask extends Task {

  @Inject
  ExampleTask() {
    super("mytask");
  }

  @Override
  public void execute(Map<String, List<String>> map, PrintWriter printWriter) {
    printWriter.print("Foo");
  }
}
