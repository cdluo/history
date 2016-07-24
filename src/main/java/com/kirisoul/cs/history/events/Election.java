package com.kirisoul.cs.history.events;

/**
 * An Election!
 * 
 * Research:
 * None, made it up
 * 
 * @author ChrisLuo
 */
public class Election extends Event{

  /**
   * Constructor
   * 
   * @param year1
   * @param to1
   */
  public Election(int year1, String to1) {
    super(year1, to1);
    
    double coin = Math.random()*10;
    double socialChange = 2 + Math.floor(Math.random()*3);
    
    if(coin > 5){
      super.getSocial()[1] = socialChange;
    }else{
      super.getSocial()[1] = -socialChange;
    }
    
    super.setName("Election");
  }
}
