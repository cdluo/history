package com.kirisoul.cs.history;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Timer;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.kirisoul.cs.history.database.SQLQuery;
import com.kirisoul.cs.history.world.Time;
import com.kirisoul.cs.history.world.World;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * Main class, starts the server, contains handlers for the GUI, etc...
 * 
 * @author ChrisLuo
 */

public class Main {
  public static void main(String[] args) throws SQLException, ClassNotFoundException, URISyntaxException, InterruptedException {
    new Main(args).run();
  }

  private String[] args;
  private SQLQuery sqlDB;
  
  private World world;
  private Time time;
  private Timer timer;
  private static final int DELAY = 500;
  private static final int TIMESPEED = 2000;
  
  private static final Gson GSON = new Gson();

  private Main(String[] args) {
    this.args = args;
  }

  private void run() throws SQLException, ClassNotFoundException, URISyntaxException, InterruptedException {
    
    try{
      world = new World();
    }catch(SQLException | ClassNotFoundException e){
      e.printStackTrace();
      System.exit(1); 
    }
    
    sqlDB = new SQLQuery();
    
    Spark.staticFileLocation("/public");
    Spark.setPort(getHerokuAssignedPort());
    
    Spark.get("/start", (request, response) -> {
      Map<String, Object> variables =
        ImmutableMap.of("title", "TTNW");
      return new ModelAndView(variables, "start.ftl");
    }, new FreeMarkerEngine());
    
    Spark.get("/home", (request, response) -> {
      Map<String, Object> variables =
        ImmutableMap.of("title", "TTNW", "header", "Earth");
      return new ModelAndView(variables, "query.ftl");
    }, new FreeMarkerEngine());
    
//    Spark.post("/home", new MainHandler());
    Spark.post("/time", new TimeHandler());
    Spark.post("/timeline", new TimelineHandler());
    Spark.post("/year", new YearHandler());
    Spark.post("/newNation", new NewNationHandler());
    Spark.post("/newEvent", new NewEventHandler());
    Spark.post("/stopTime", new StopTimeHandler());
    Spark.post("/resumeTime", new ResumeTimeHandler());
  }
  
  /**
   * From the Spark Heroku Tutorial.
   * 
   * @return port to use
   */
  static int getHerokuAssignedPort() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get("PORT") != null) {
        return Integer.parseInt(processBuilder.environment().get("PORT"));
    }
    return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
  }
  
  /**
   * Sends the nations list to the front end for time updates.
   * 
   * @author ChrisLuo
   */
  private class MainHandler implements Route {
    @Override
    public Object handle(Request req, Response res) {
      Map<String, Object> variables =
          ImmutableMap.of("title", "TTNW", "header", "Earth");
        return new ModelAndView(variables, "query.ftl");
    }
  }
  
  /**
   * Sends the nations list to the front end for time updates.
   * 
   * @author ChrisLuo
   */
  private class TimeHandler implements Route {
    @Override
    public Object handle(Request req, Response res) {

      return GSON.toJson(world.getNations().values());
    }
  }
  
  /**
   * Sends the event log/timeline to the front end.
   * 
   * @author ChrisLuo
   */
  private class TimelineHandler implements Route {
    @Override
    public Object handle(Request req, Response res) {
      
      return GSON.toJson(world.getCurEvents());
    }
  }
  
  /**
   * Sends the current year to the front end.
   * 
   * @author ChrisLuo
   */
  private class YearHandler implements Route {
    @Override
    public Object handle(Request req, Response res) {
      
      return GSON.toJson(world.getYear());
    }
  }
  
  /**
   * Handles creation/adding of new nation to DB.
   * 
   * @author ChrisLuo
   */
  private class NewNationHandler implements Route {
    @Override
    public Object handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String name = qm.value("name");
      int pop = Integer.parseInt(qm.value("pop"));
      int gdp = Integer.parseInt(qm.value("gdp"));
      int social = Integer.parseInt(qm.value("social"));
      int living = Integer.parseInt(qm.value("living"));
      
      System.out.println(pop);
      System.out.println(gdp);
      
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
  
  /**
   * Handles creating/adding of new event to DB.
   * 
   * @author ChrisLuo
   */
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
  
  /**
   * Upon being called by front end, stops the servers time functions.
   * 
   * @author ChrisLuo
   */
  private class StopTimeHandler implements Route {
    @Override
    public Object handle(Request req, Response res) {
      timer.cancel();
      
      return GSON.toJson(world.getYear());
    }
  }
  
  /**
   * Upon being called by front end, starts the servers time functions.
   * 
   * @author ChrisLuo
   */
  private class ResumeTimeHandler implements Route {
    @Override
    public Object handle(Request req, Response res) {
      timer = new Timer();
      time = new Time(world);
      timer.schedule(time, DELAY, TIMESPEED);
      
      return GSON.toJson(world.getYear());
    }
  }
}
