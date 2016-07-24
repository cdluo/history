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
  
  private static final Gson GSON = new Gson();

  private Main(String[] args) {
    this.args = args;
  }

  private void run() throws SQLException, ClassNotFoundException, URISyntaxException {
    
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
   * @return
   */
  static int getHerokuAssignedPort() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get("PORT") != null) {
        return Integer.parseInt(processBuilder.environment().get("PORT"));
    }
    return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
  }
  
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
