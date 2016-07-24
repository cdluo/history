package com.kirisoul.cs.history.world;

import java.sql.SQLException;
import java.util.TimerTask;

/**
 * Represents Time (or the timer for the application at least).
 * 
 * @author ChrisLuo
 */
public class Time extends TimerTask{

  /**
   * World that this time operates on.
   */
  private World world;
  
  /**
   * Constructor
   * 
   * @param world1
   */
  public Time(World world1){
    world = world1;
  }

  /**
   * Function to run in this timer.
   */
  @Override
  public void run() {
    try {
      world.passTime();
    } catch (SQLException e) {
      System.out.println("SQLException");
      e.printStackTrace();
    }
  }
}
