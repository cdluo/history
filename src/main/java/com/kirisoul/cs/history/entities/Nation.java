package com.kirisoul.cs.history.entities;

/**
 * Does not represent a nation, but rather a set 
 * of changes that can occur in a nation.
 * @author ChrisLuo
 */

public class Nation {

  private String name;
  private int pop;
  private int gdp;
  
  public Nation(String name1, int pop1, int gdp1){
    name = name1;
    pop=pop1;
    gdp=gdp1;
  }
  
  public void growPop(){
    pop += (int) (Math.random()*10);
  }
  
  public void growGdp(){
    gdp += (int) (Math.random()*10);
  }
  
  public String getName(){
    return name;
  }
  
  public int getPop(){
    return pop;
  }
  
  public int getGdp(){
    return gdp;
  }
  
  public String toString(){
    return name + "|Pop: " + pop + "|Gdp: " +gdp;
  }
}
