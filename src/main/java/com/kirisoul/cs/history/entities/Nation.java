package com.kirisoul.cs.history.entities;

/**
 * Represent a nation and the set 
 * of changes that can occur in it from year to year.
 * 
 * @author ChrisLuo
 */

public class Nation {

  /**
   * Name of the nation
   */
  private String name;
  /**
   * Its population
   */
  private int pop;
  /**
   * Its GDP
   */
  private int gdp;
  /**
   * Its social stability index (SSI)
   */
  private int social;
  /**
   * Its living standard index.
   */
  private int living;
  
  /**
   * Constructor
   * 
   * @param name1 of this nation
   * @param pop1 population of this nation
   * @param gdp1 gdp of this nation
   * @param social1 SSI of this nation
   * @param living1 Living Standards of this Nation
   */
  public Nation(String name1, int pop1, int gdp1, int social1, int living1){
    name = name1;
    pop = pop1;
    gdp = gdp1;
    social = social1;
    living = living1;
  }
  
  /**
   * Regular population growth function. Can end the world.
   */
  public void growPop(){
    pop += (int) Math.floor(1 + Math.random()*(0.01*pop));
    if(pop < 0){
      pop = 0;
    }
  }
  
  /**
   * Regular GDP growth function. Might end the world.
   */
  public void growGdp(){
    gdp += (int) Math.floor(1 + Math.random()*(0.01*gdp));
    gdp -= (int) Math.floor(1 + Math.random()*(0.01*gdp));
    
    if(gdp < 0){
      gdp = 0;
    }
  }
  
  /**
   * Regular social change function.
   */
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
  
  /**
   * Regular living change function.
   */
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
  
  /**
   * Interprets an event change matrix for population.
   * @param changes the change matrix
   */
  public void eventPop(double[] changes){
    pop = (int) Math.floor(pop * (1 + (changes[0]/100)));
    pop += changes[1];
    
    if(pop < 0){
      pop = 0;
    }
  }
  
  /**
   * Interprets an event change matrix for gdp.
   * @param changes the change matrix
   */
  public void eventGdp(double[] changes){
    gdp = (int) Math.floor(gdp * (1 + (changes[0]/100)));
    gdp += changes[1];
    
    if(gdp < 0){
      gdp = 0;
    }
  }
  
  /**
   * Interprets an event change matrix for social.
   * @param changes the change matrix
   */
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
  
  /**
   * Interprets an event change matrix for living.
   * @param changes the change matrix
   */
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
  
  /**
   * Calls all the regular change functions (Passes a year).
   */
  public void passTime(){
    growPop();
    growGdp();
    growSocial();
    growLiving();
  }
  /**
   * Accessor for the nation's name
   * 
   * @return name
   */
  public String getName(){
    return name;
  }
  
  /**
   * Accessor for the nation's population.
   * 
   * @return population
   */
  public int getPop(){
    return pop;
  }
  
  /**
   * Accessor for the nation's gdp.
   * 
   * @return gdp
   */
  public int getGdp(){
    return gdp;
  }
  
  /**
   * Accessor for the nation's social.
   * 
   * @return social
   */
  public int getSocial(){
    return social;
  }
  
  /**
   * Accessor for the nation's living.
   * 
   * @return living
   */
  public int getLiving(){
    return living;
  }
  
  /**
   * Returns a string form of the nation, with all its
   * essential info seperated by "|".
   * 
   * @return string
   */
  public String toString(){
    return name + "|Pop: " + pop + "|Gdp: " +gdp + "|Social: " +social + "|Living: " + living;
  }
}
