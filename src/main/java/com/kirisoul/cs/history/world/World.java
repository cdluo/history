package com.kirisoul.cs.history.world;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.kirisoul.cs.history.database.SQLQuery;
import com.kirisoul.cs.history.entities.Nation;
import com.kirisoul.cs.history.events.Event;
import com.kirisoul.cs.history.events.EventGenerator;

/**
 * Conducts all the logic for the game
 * @author ChrisLuo
 */

public class World {

  /**
   * DB
   */
  private SQLQuery query;
  /**
   * HashMap of all the nations.
   */
  private ConcurrentHashMap<String, Nation> nations;
  /**
   * Event Generator
   */
  private EventGenerator eventGen;
  /**
   * Events for the current year
   */
  private List<Event> curEvents;
  /**
   * The current year.
   */
  private int year;
  
  /**
   * Constructor
   * @param db the sqlite database
   * @throws ClassNotFoundException exception
   * @throws SQLException exception
   * @throws URISyntaxException 
   */
  public World() throws ClassNotFoundException, SQLException, URISyntaxException{
    query = new SQLQuery();
    nations = buildNations();
    eventGen = new EventGenerator(query);
    curEvents = null;
    year = query.getYear();
  }
  
  /**
   * Adds a nation to both the sqlite database and our local nation list.
   * @param name of the nation
   * @param gdp of the nation
   * @param pop of the nation
   * @throws SQLException exception
   */
  public void addNation(String name, int pop, int gdp, int social, int living) throws SQLException{
    query.addNation(name, pop, gdp, social, living);
    Nation added = new Nation(name, pop, gdp, social, living);
    nations.put(name, added);
  }
  
  /**
   * Passes time
   * @throws SQLException exception
   */
  public void passTime() throws SQLException{
    
    query.IncYear();
    year = query.getYear();
    if(year % 10 == 0 && nations.size() !=0){
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
    
    for(Nation n: nations.values()){
      
      n.passTime();
      
      query.updatePop(n.getName(), n.getPop());
      query.updateGdp(n.getName(), n.getGdp());
      query.updateSocial(n.getName(), n.getSocial());
      query.updateLiving(n.getName(), n.getLiving());
      
      System.out.println(n.toString());  
    }
    
    System.out.println("----------------");
  }
  
  /**
   * Interprets the effects of a passed in event
   * based on its change matricies
   * 
   * @param e the event to be interpreted
   * @throws SQLException exception
   */
  public void interpretEvent(Event e) throws SQLException{
    String to = e.getTo();
    Nation n = nations.get(to);

    if(n != null){
        
        n.eventPop(e.getPop());
        n.eventGdp(e.getGdp());
        n.eventSocial(e.getSocial());
        n.eventLiving(e.getLiving());
        
        query.updatePop(n.getName(), n.getPop());
        query.updateGdp(n.getName(), n.getGdp());
        query.updateSocial(n.getName(), n.getSocial());
        query.updateLiving(n.getName(), n.getLiving());
        
    }else{
      System.out.println("Warning, could not find nation to apply event to.");
    }
  }
  
  /**
   * Builds the nations list at instantiation.
   * passTime() will update this.
   * 
   * @return ArrayList of Nations
   * @throws SQLException exception
   */
  public ConcurrentHashMap<String, Nation> buildNations() throws SQLException{
    
    ConcurrentHashMap<String, Nation> nations1 = new ConcurrentHashMap<String, Nation>();
    
    for(String name: query.getNationNames()){
      Nation n = new Nation(name, query.queryPop(name), query.queryGdp(name),
                            query.querySocial(name), query.queryLiving(name));
      nations1.put(name, n);
    }
   
    return nations1;
  }
  
  /**
   * Returns the HashMap of Nations.
   * 
   * @return nations
   */
  public ConcurrentHashMap<String, Nation> getNations(){
    return nations;
  }
  
  /**
   * Gets the events for the current year from the DB.
   * 
   * @return current events in a list.
   */
  public List<Event> getCurEvents(){
    return curEvents;
  }
  
  /**
   * Gets the current year from the DB.
   * 
   * @return the year.
   */
  public int getYear(){
    return year;
  }
}
