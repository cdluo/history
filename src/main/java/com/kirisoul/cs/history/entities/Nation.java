package com.kirisoul.cs.history.entities;

/**
 * Does not represent a nation, but rather a set 
 * of changes that can occur in a nation.
 * @author ChrisLuo
 */

public class Nation {

  public Nation(){
    
  }
  
  public int growPop(int pop){
    int newPop = pop+ (int) (Math.random()*10);
    return newPop;
  }
  
  public int growGdp(int gdp){
    int newGdp = gdp + (int) (Math.random()*10);
    return newGdp;
  }
}
