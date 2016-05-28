package com.kirisoul.cs.history;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Timer;
import java.util.TreeSet;
import java.util.regex.Pattern;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.kirisoul.cs.history.world.Time;
import com.kirisoul.cs.history.world.World;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import freemarker.template.Configuration;
import spark.Spark;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import spark.ExceptionHandler;
import spark.template.freemarker.FreeMarkerEngine;


public class Main {
  public static void main(String[] args) throws SQLException {
    new Main(args).run();
  }

  private String[] args;
  private String db;
  
  private World world;
  private Time time;
  private Timer timer;
  private static final int DELAY = 1500;
  private static final int SECOND = 1000;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() throws SQLException {
    OptionParser parser = new OptionParser();

    parser.accepts("gui");
    OptionSpec<String> database = parser.nonOptions().ofType(String.class);
    OptionSet options = parser.parse(args);
    
    db = options.valueOf(database);
    if(db == null){
      System.out.println("Please specify the location of a sqlite3 db after ./run");
      System.exit(1);
    }

    if (options.has("gui")) {
      runSparkServer();
    } else {
      // Terminal Version
      timer = new Timer();
      
      try{
        world = new World(db);
      }catch(SQLException | ClassNotFoundException e){
        System.out.println("ERROR: Could not open database");
        System.exit(1); 
      }
      
      time = new Time(world);
      timer.schedule(time, DELAY, SECOND);
    }
  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.\n", templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer() {
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();

    // Setup Spark Routes
    Spark.get("/stars", new FrontHandler(), freeMarker);
  }

  private class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables =
        ImmutableMap.of("title", "Stars: Query the database",
                        "db", db);
      return new ModelAndView(variables, "query.ftl");
    }
  }


  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }


}
