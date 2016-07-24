package com.kirisoul.cs.history.events;

/**
 * An Economic Boom!
 * 
 * Research Status:
 * None, just made it up.
 * 
 * @author ChrisLuo
 */
public class EconomicBoom extends Event{

  /**
   * Constructor
   * 
   * @param year1
   * @param to1
   */
  public EconomicBoom(int year1, String to1) {
    super(year1, to1);
    
    super.getGdp()[0] = 5 + Math.random() * 5;
    
    super.setName("Economic Boom");
  }
}
