package com.kirisoul.cs.history.events;

/**
 * Oh no! A Natural Disaster
 * 
 * Research:
 * None, made it up
 * 
 * @author ChrisLuo
 */
public class NaturalDisaster extends Event{

  /**
   * Constructor
   * 
   * @param year1
   * @param to1
   */
  public NaturalDisaster(int year1, String to1) {
    super(year1, to1);
    
    super.getPop()[1] = -(50 + (int)Math.round(Math.random()*250));
    super.getGdp()[0] = -(1 + Math.random()*4);
    super.getGdp()[1] = -(100 + (int)Math.round(Math.random()*100));
    super.getSocial()[1] = -(5);
    super.getLiving()[1] = -(2);
    
    super.setName("Natural Disaster");
  }
}
