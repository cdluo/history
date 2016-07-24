package com.kirisoul.cs.history.events;

/**
 * Represents an event on the database timeline.
 * 
 * @author ChrisLuo
 */

public class Event {

  /**
   * The year this event takes place
   */
  private int year;
  /**
   * Who is affected by this event
   */
  private String to;
  /**
   * The type of this event.
   */
  private String name;
  
  /**
   * The change matricies for interpreting the effects of this event.
   */
  private double[] popChange;
  private double[] gdpChange;
  private double[] socialChange;
  private double[] livingChange;
  
  /**
   * Constructor
   * 
   * @param year1
   * @param to1
   */
  public Event(int year1, String to1){
    year = year1;
    to= to1;
    name = "undefined";
    
    popChange = new double[2];
    gdpChange = new double[2];
    socialChange = new double[2];
    livingChange = new double[2];
  }
  
  /**
   * Accessor for the year of the event.
   * @return year
   */
  public int getYear(){
    return year;
  }
  
  /**
   * Accessor for the name of the event.
   * @return name
   */
  public String getName(){
    return name;
  }
  
  /**
   * Sets the name of this event.
   * @param name1
   */
  public void setName(String name1){
    name = name1;
  }
  
  /**
   * Accessor for the recipient of this event.
   * @return
   */
  public String getTo(){
    return to;
  }
  
  /**
   * Accessor for the population change matrix of this event.
   * @return pop change
   */
  public double[] getPop(){
    return popChange;
  }
  
  /**
   * Accessor for the GDP change matrix of this event.
   * @return GDP change
   */
  public double[] getGdp(){
    return gdpChange;
  }
  
  /**
   * Accessor for the Social change matrix of this event.
   * @return social change
   */
  public double[] getSocial(){
    return socialChange;
  }
  
  /**
   * Accessor for the Living change matrix of this event.
   * @return living change
   */
  public double[] getLiving(){
    return livingChange;
  }
}
