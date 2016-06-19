package com.kirisoul.cs.history.events;

/**
 * Represents an event from the database timeline.
 * @author ChrisLuo
 */

public class Event {

  private int year;
  private String name;
  private String to;
  
  public Event(int year1, String name1, String to1){
    year = year1;
    name = name1;
    to= to1;
  }
  
  public int getYear(){
    return year;
  }
  
  public String getName(){
    return name;
  }
  
  public String getTo(){
    return to;
  }
}
