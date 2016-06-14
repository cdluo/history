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
  private int social;
  private int living;
  
  public Nation(String name1, int pop1, int gdp1, int social1, int living1){
    name = name1;
    pop = pop1;
    gdp = gdp1;
    social = social1;
    living = living1;
  }
  
  public void growPop(){
    pop += (int) (Math.random()*(0.1*pop)-Math.random()*(0.1*pop));
  }
  
  public void growGdp(){
    gdp += (int) (Math.random()*(0.1*gdp)-Math.random()*(0.1*gdp));
  }
  
  public void growSocial(){
    social += 1;
  }
  
  public void growLiving(){
    living += 1;
  }
  
  public void passTime(){
    growPop();
    growGdp();
    growSocial();
    growLiving();
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
  
  public int getSocial(){
    return social;
  }
  
  public int getLiving(){
    return living;
  }
  
  public String toString(){
    return name + "|Pop: " + pop + "|Gdp: " +gdp + "|Social: " +social + "|Living: " + living;
  }
}
