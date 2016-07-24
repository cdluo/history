package com.kirisoul.cs.history.events;

/**
 * Represents an event from the database timeline.
 * @author ChrisLuo
 */

public class Event {

  private int year;
  private String to;
  private String name;  //For DB purposes only
  
  //For interpreting the effects of this event.
  private double[] popChange;
  private double[] gdpChange;
  private double[] socialChange;
  private double[] livingChange;
  
  public Event(int year1, String to1){
    year = year1;
    to= to1;
    name = "undefined";
    
    popChange = new double[2];
    gdpChange = new double[2];
    socialChange = new double[2];
    livingChange = new double[2];
  }
  
  public int getYear(){
    return year;
  }
  
  /*
   * Will be overriden by more specific classes.
   */
  public String getName(){
    return name;
  }
  
  public void setName(String name1){
    name = name1;
  }
  
  public String getTo(){
    return to;
  }
  
  public double[] getPop(){
    return popChange;
  }
  
  public double[] getGdp(){
    return gdpChange;
  }
  
  public double[] getSocial(){
    return socialChange;
  }
  
  public double[] getLiving(){
    return livingChange;
  }
}
