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

  private SQLQuery db;
  
  public EventGenerator(SQLQuery db1){
    db = db1;
  }
  
  /**
   * Adds an event to the timeline.
   * @throws SQLException 
   */
  public void addEvent(Event e) throws SQLException{
    db.addEvent(e.getYear(), e.getName(), e.getTo());
  }
  
  /**
   * Generates an event and adds it to the timeline.
   * @throws SQLException
   */
  public void generate() throws SQLException{
    int year = db.getYear() + (int) Math.floor(Math.random()*100);  //For event
    
    int type = (int) Math.floor(Math.random()*100);
    String name;  //For event
   
    if(type > 95){
      name = "Natural Disaster";
    }else if(type > 67){
      name = "Election";
    }else if(type > 33){
      name = "Economic Downturn";
    }else{
      name = "Economic Boom";
    }
    
    int index = (int) Math.floor(Math.random() * db.getNationNames().size());
    String to = (String) db.getNationNames().toArray()[index];
    
    Event newEvent = new Event(year, name, to);
    addEvent(newEvent);
  }
  
}
