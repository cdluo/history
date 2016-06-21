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
    pop += (int) Math.floor(1 + Math.random()*(0.01*pop));
  }
  
  public void growGdp(){
    gdp += (int) Math.floor(1 + Math.random()*(0.01*gdp));
    gdp -= (int) Math.floor(1 + Math.random()*(0.01*gdp));
  }
  
  public void growSocial(){
    social += Math.round(Math.random());
    social -= Math.round(Math.random());
    
    if(social > 100){
      social = 100;
    }
    
    if(social < 0){
      social = 0;
    }
  }
  
  public void growLiving(){
    living += Math.round(Math.random());
    living -= Math.round(Math.random());
    
    if(living > 100){
      living = 100;
    }
    
    if(living < 0){
      living = 0;
    }
  }
  
  public void eventPop(double[] changes){
    pop = (int) Math.floor(pop * (1 + (changes[0]/100)));
    pop += changes[1];
  }
  
  public void eventGdp(double[] changes){
    gdp = (int) Math.floor(gdp * (1 + (changes[0]/100)));
    gdp += changes[1];
  }
  
  public void eventSocial(double[] changes){
    social = (int) Math.floor(social * (1 + (changes[0]/100)));
    social += changes[1];
    
    if(social > 100){
      social = 100;
    }
    
    if(social < 0){
      social = 0;
    }
  }
  
  public void eventLiving(double[] changes){
    living = (int) Math.floor(living * (1 + (changes[0]/100)));
    living += changes[1];
    
    if(living > 100){
      living = 100;
    }
    
    if(living < 0){
      living = 0;
    }
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
