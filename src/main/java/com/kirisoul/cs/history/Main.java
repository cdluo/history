package com.kirisoul.cs.history;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
import com.google.gson.Gson;
import com.kirisoul.cs.history.database.SQLQuery;
import com.kirisoul.cs.history.entities.Nation;
import com.kirisoul.cs.history.events.Event;
import com.kirisoul.cs.history.events.EventGenerator;
import com.kirisoul.cs.history.events.EventInterpreter;
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
import spark.Route;
import spark.TemplateViewRoute;
import spark.ExceptionHandler;
import spark.template.freemarker.FreeMarkerEngine;


public class Main {
  public static void main(String[] args) throws SQLException, ClassNotFoundException, URISyntaxException {
    new Main(args).run();
  }

  private String[] args;
  private SQLQuery sqlDB;
  
  private World world;
  private Time time;
  private Timer timer;
  private static final int DELAY = 1000;
  private static final int SECOND = 1000;
  private static final int HALF_SECOND = 500;
  private static final int QUARTER_SECOND = 250;
  
  private static final Gson GSON = new Gson();

  private Main(String[] args) {
    this.args = args;
  }

  private void run() throws SQLException, ClassNotFoundException, URISyntaxException {
//    OptionParser parser = new OptionParser();
//
//    parser.accepts("gui");
//    OptionSpec<String> database = parser.nonOptions().ofType(String.class);
//    OptionSet options = parser.parse(args);
//    
//    db = options.valueOf(database);
//    if(db == null){
//      System.out.println("Please specify the location of a sqlite3 db after ./run");
//      System.exit(1);
//    }
    
    try{
      world = new World();
    }catch(SQLException | ClassNotFoundException e){
      e.printStackTrace();
      System.exit(1); 
    }
    
    sqlDB = new SQLQuery();
    
    timer = new Timer();
    time = new Time(world);
    timer.schedule(time, DELAY, SECOND);
    
    Spark.staticFileLocation("/public");
    Spark.setPort(getHerokuAssignedPort());
    
    Spark.get("/home", (request, response) -> {
      Map<String, Object> variables =
        ImmutableMap.of("title", "History", "header", "Earth");
      return new ModelAndView(variables, "query.ftl");
    }, new FreeMarkerEngine());
    
    // Setup Spark Routes
//    Spark.get("/home", new FrontHandler(), freeMarker);
    Spark.post("/time", new TimeHandler());
    Spark.post("/timeline", new TimelineHandler());
    Spark.post("/year", new YearHandler());
    Spark.post("/newNation", new NewNationHandler());
    Spark.post("/newEvent", new NewEventHandler());
    Spark.post("/stopTime", new StopTimeHandler());
    Spark.post("/resumeTime", new ResumeTimeHandler());

//    runSparkServer();

  }

//  private static FreeMarkerEngine createEngine() {
//    Configuration config = new Configuration();
//    File templates = new File("src/main/resources/spark/template/freemarker");
//    try {
//      config.setDirectoryForTemplateLoading(templates);
//    } catch (IOException ioe) {
//      System.out.printf("ERROR: Unable use %s for template loading.\n", templates);
//      System.exit(1);
//    }
//    return new FreeMarkerEngine(config);
//  }
  
  /**
   * From the Spark Heroku Tutorial.
   * @return
   */
  static int getHerokuAssignedPort() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get("PORT") != null) {
        return Integer.parseInt(processBuilder.environment().get("PORT"));
    }
    return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
  }

//  private void runSparkServer() {
//    Spark.externalStaticFileLocation("src/main/resources/static");
//    
//    Spark.setPort(getHerokuAssignedPort());
//
//    FreeMarkerEngine freeMarker = createEngine();
//
//    // Setup Spark Routes
//    Spark.get("/home", new FrontHandler(), freeMarker);
//    Spark.post("/time", new TimeHandler());
//    Spark.post("/timeline", new TimelineHandler());
//    Spark.post("/year", new YearHandler());
//    Spark.post("/newNation", new NewNationHandler());
//    Spark.post("/newEvent", new NewEventHandler());
//    Spark.post("/stopTime", new StopTimeHandler());
//    Spark.post("/resumeTime", new ResumeTimeHandler());
//  }

//  private class FrontHandler implements TemplateViewRoute {
//    @Override
//    public ModelAndView handle(Request req, Response res) {
//      Map<String, Object> variables =
//        ImmutableMap.of("title", "History", "header", "Earth");
//      return new ModelAndView(variables, "query.ftl");
//    }
//  }
  
  private class TimeHandler implements Route {
    @Override
    public Object handle(Request req, Response res) {

      return GSON.toJson(world.getNations());
    }
  }
  
  private class TimelineHandler implements Route {
    @Override
    public Object handle(Request req, Response res) {
      
      return GSON.toJson(world.getCurEvents());
    }
  }
  
  private class YearHandler implements Route {
    @Override
    public Object handle(Request req, Response res) {
      
      return GSON.toJson(world.getYear());
    }
  }
  
  private class NewNationHandler implements Route {
    @Override
    public Object handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String name = qm.value("name");
      int pop = Integer.parseInt(qm.value("pop"));
      int gdp = Integer.parseInt(qm.value("gdp"));
      int social = Integer.parseInt(qm.value("social"));
      int living = Integer.parseInt(qm.value("living"));
      
      try {
        world.addNation(name, pop, gdp, social, living);
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        System.out.println("ERROR: Couldn't add new nation.");
        e.printStackTrace();
      }
      
      return GSON.toJson(world.getYear());
    }
  }
  
  private class NewEventHandler implements Route {
    @Override
    public Object handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String type = qm.value("type");
      int year = Integer.parseInt(qm.value("year"));
      String to = qm.value("to");

      try {
        sqlDB.addEvent(year, type, to);
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        System.out.println("ERROR: Couldn't add new event.");
        e.printStackTrace();
      }
      
      return GSON.toJson(world.getYear());
    }
  }
  
  private class StopTimeHandler implements Route {
    @Override
    public Object handle(Request req, Response res) {
      timer.cancel();
      
      return GSON.toJson(world.getYear());
    }
  }
  
  private class ResumeTimeHandler implements Route {
    @Override
    public Object handle(Request req, Response res) {
      timer = new Timer();
      time = new Time(world);
      timer.schedule(time, DELAY, SECOND);
      
      return GSON.toJson(world.getYear());
    }
  }

}
