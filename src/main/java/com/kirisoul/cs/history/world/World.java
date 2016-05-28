package com.kirisoul.cs.history.world;

import java.util.ArrayList;
import java.util.List;

import com.kirisoul.cs.history.entities.Nation;

/**
 * Conducts all the logic for the game
 * @author ChrisLuo
 */

public class World {

  private List<Nation> nations;
  
  public World(){
    nations = new ArrayList<Nation>();
  }
  
  public void addNation(String name, int pop){
    Nation newN = new Nation(name, pop);
    nations.add(newN);
  }
  
  public void passTime(){
    for(Nation n: nations){
      n.growPop();
      System.out.println(n);
    }
  }
}
