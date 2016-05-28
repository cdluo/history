package com.kirisoul.cs.history.entities;

/**
 * Represents a nation
 * @author ChrisLuo
 */
public class Nation {

  private String name;
  private int pop;

  public Nation(String name1, int pop1) {
    name = name1;
    pop = pop1;
  }

  public void growPop() {
    int growth = (int) Math.round((Math.random()*10));
    pop = pop + growth;
  }
  
  public int getPop(){
    return pop;
  }
  
  public String toString(){
    return name + "|Population: " + String.valueOf(pop);
  }
}
