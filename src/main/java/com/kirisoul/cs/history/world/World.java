package com.kirisoul.cs.history.world;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kirisoul.cs.history.database.SQLQuery;
import com.kirisoul.cs.history.entities.Nation;
import com.kirisoul.cs.history.events.Event;
import com.kirisoul.cs.history.events.EventGenerator;
import com.kirisoul.cs.history.events.EventInterpreter;

/**
 * Conducts all the logic for the game
 * @author ChrisLuo
 */

public class World {

  private SQLQuery query;
  private List<Nation> nations;   //Make a hashmap
  private EventGenerator eventGen;
  private EventInterpreter eventInterpret;
  private List<Event> curEvents;
  private int year;
  
  /**
   * Constructor
   * @param db the sqlite database
   * @throws ClassNotFoundException exception
   * @throws SQLException exception
   */
  public World(String db) throws ClassNotFoundException, SQLException{
    query = new SQLQuery(db);
    nations = buildNations();
    eventGen = new EventGenerator(query);
    eventInterpret = new EventInterpreter();
    curEvents = null;
    year = -1;
  }
  
  /**
   * Adds a nation to both the sqlite database and our local nation list.
   * @param name of the nation
   * @param gdp of the nation
   * @param pop of the nation
   * @throws SQLException exception
   */
  public void addNation(String name, int gdp, int pop, int social, int living) throws SQLException{
    query.addNation(name, pop, gdp, social, living);
    Nation added = new Nation(name, pop, gdp, social, living);
    nations.add(added);
  }
  
  /**
   * Passes time
   * @throws SQLException exception
   */
  public void passTime() throws SQLException{
    
    query.IncYear();
    year = query.getYear();
    if(year % 10 == 0){
      eventGen.generate();
    }
    
    System.out.println("Current Year: " + year);
    System.out.println("");
    
    curEvents = query.getEvent(year);

    if(curEvents != null){
      
      for(Event e: curEvents){
      //Do event stuff (new classes for each event)
        System.out.println(e.getYear() + ": " + e.getName() + " happened in " + e.getTo() + "."); 
        
        interpretEvent(e);
      }
      
      System.out.println("");
    }
    
    for(Nation n: nations){
      
      n.passTime();
      
      query.updatePop(n.getName(), n.getPop());
      query.updateGdp(n.getName(), n.getGdp());
      query.updateSocial(n.getName(), n.getSocial());
      query.updateLiving(n.getName(), n.getLiving());
      
      System.out.println(n.toString());  
    }
    
    System.out.println("----------------");
  }
  
  public void interpretEvent(Event e) throws SQLException{
    eventInterpret.interpret(e);
    String to = e.getTo();
    
    int flag = 0;
    //Will be better once this is a hashmap
    for(Nation n: nations){
      if(n.getName().equals(to)){
//        System.out.println("Before GDP: " + n.getGdp());
        
        n.eventPop(eventInterpret.getPop());
        n.eventGdp(eventInterpret.getGdp());
//        System.out.println(eventInterpret.getGdp()[0] + "|" + eventInterpret.getGdp()[1]);
        n.eventSocial(eventInterpret.getSocial());
        n.eventLiving(eventInterpret.getLiving());
        
//        System.out.println("After GDP: " + n.getGdp());
        
        query.updatePop(n.getName(), n.getPop());
        query.updateGdp(n.getName(), n.getGdp());
        query.updateSocial(n.getName(), n.getSocial());
        query.updateLiving(n.getName(), n.getLiving());
        
        flag=1;
      }
    }
    
    if(flag == 0){
      System.out.println("Warning, could not find nation to apply event to.");
    }
    
    ////////
    
  }
  
  /**
   * Builds the nations list at instantiation.
   * passTime() will update this.
   * @return ArrayList of Nations
   * @throws SQLException exception
   */
  public List<Nation> buildNations() throws SQLException{
    
    List<Nation> nations1 = new ArrayList<Nation>();
    
    for(String name: query.getNationNames()){
      Nation n = new Nation(name, query.queryPop(name), query.queryGdp(name),
                            query.querySocial(name), query.queryLiving(name));
      nations1.add(n);
    }
   
    return nations1;
  }
  
  /**
   * Returns the ArrayList of Nations.
   * @return nations
   */
  public List<Nation> getNations(){
    return nations;
  }
  
  public List<Event> getCurEvents(){
    return curEvents;
  }
  
  public int getYear(){
    return year;
  }
}
