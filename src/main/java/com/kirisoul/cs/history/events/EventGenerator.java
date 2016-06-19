package com.kirisoul.cs.history.events;

import com.kirisoul.cs.history.database.SQLQuery;

/**
 * Creates new events to add to the timeline.
 * 
 * Possible Events:
 * 
 * (Major)
 * -War
 * -Natural Disaster
 * -Major Innovation/Invention
 * -Revolution
 *    -Social
 *    -Economic
 *    -Governmental
 *    
 * (Minor)
 * -Election
 * -New Laws on (X)
 * -Economic Downturn
 * -Economic Boom
 *    
 * @author ChrisLuo
 */

public class EventGenerator {

  private SQLQuery db;
  
  public EventGenerator(SQLQuery db1){
    db = db1;
  }
  
  /**
   * Adds one event within the next 100 years of the present year.
   */
  public void addEvent(){
    
  }
  
}
