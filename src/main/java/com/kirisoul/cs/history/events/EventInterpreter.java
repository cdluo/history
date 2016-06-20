package com.kirisoul.cs.history.events;

/**
 * Interprets what an event means for a nation's parameters.
 * @author Chris Luo
 */
public class EventInterpreter {

  /**
   * Each array contains 2 doubles, the first is the percentage change,
   * the second is a numerical change (used more for incrementing social/living changes).
   */
  
  private double[] pop;
  private double[] gdp;
  private double[] social;
  private double[] living;
  
  public EventInterpreter(){
    pop = new double[2];
    gdp = new double[2];
    social = new double[2];
    living = new double[2];
  }
  
  public void zero(double[] toZero){
    for(int i=0; i<toZero.length; i++){
      toZero[i] = 0;
    }
  }
  
  public void zeroAll(){
    zero(pop);
    zero(gdp);
    zero(social);
    zero(living);
  }
  
  public void interpret(Event e){
    zeroAll();
    
    if(e.getName().equals("Natural Disaster")){
      pop[1] = -(50 + (int)Math.round(Math.random()*250));
      gdp[0] = -(1 + Math.random()*4);
      gdp[1] = -(100 + (int)Math.round(Math.random()*100));
      social[1] = -(5);
      living[1] = -(2);
    }
    
    else if(e.getName().equals("Election")){
      double coin = Math.random()*10;
      double socialChange = 2 + Math.floor(Math.random()*3);
      
      if(coin > 5){
        social[1] = socialChange;
      }else{
        social[1] = -socialChange;
      }
    }
    
    else if(e.getName().equals("Economic Boom")){
      gdp[0] = 5 + Math.random() * 5;
    }
    
    else if(e.getName().equals("Economic Downturn")){
      gdp[0] = -(5 + Math.random() * 5);
    }
    
    else{
      System.out.println("Warning: no event interpreted.");
    }
  }
  
  public double[] getPop(){
    return pop;
  }
  
  public double[] getGdp(){
    return gdp;
  }
  
  public double[] getSocial(){
    return social;
  }
  
  public double[] getLiving(){
    return living;
  }
}
