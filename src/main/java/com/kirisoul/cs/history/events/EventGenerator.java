package com.kirisoul.cs.history.events;

import java.sql.SQLException;

import com.kirisoul.cs.history.database.SQLQuery;

/**
 * Creates new events to add to the timeline.
 * 
 * Possible Events:
 * 
 * (Major)
 * -War
 * -Natural Disaster (DONE)
 * -Major Innovation/Invention
 * -Revolution
 *    -Social
 *    -Economic
 *    -Governmental
 *    
 * (Minor)
 * -Election (DONE)
 * -Economic Downturn (DONE)
 * -Economic Boom (DONE)
 *    
 * @author ChrisLuo
 */

public class EventGenerator {

  /**
   * Database
   */
  private SQLQuery db;
  
  /**
   * Constructor
   * 
   * @param db1
   */
  public EventGenerator(SQLQuery db1){
    db = db1;
  }
  
  /**
   * Adds an event to the timeline.
   * @throws SQLException exception
   */
  public void addEvent(Event e) throws SQLException{
    db.addEvent(e.getYear(), e.getName(), e.getTo());
  }
  
  /**
   * Generates an event and adds it to the timeline.
   * @throws SQLException exception
   */
  public void generate() throws SQLException{
    int year = db.getYear() + (int) Math.floor(Math.random()*100);
    
    int index = (int) Math.floor(Math.random() * db.getNationNames().size());
    String to = (String) db.getNationNames().toArray()[index];
    
    Event newEvent = null;
    
    int type = (int) Math.floor(Math.random()*100);
   
    if(type > 95){
      newEvent = new NaturalDisaster(year, to);
    }else if(type > 67){
      newEvent = new Election(year, to);
    }else if(type > 33){
      newEvent = new EconomicDownturn(year, to);
    }else{
      newEvent = new EconomicBoom(year, to);
    }
    
    addEvent(newEvent);
  }
  
}
