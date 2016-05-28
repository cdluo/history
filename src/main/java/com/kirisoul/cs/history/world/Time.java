package com.kirisoul.cs.history.world;

import java.util.TimerTask;

public class Time extends TimerTask{

  private World world;
  
  public Time(World world1){
    world = world1;
  }

  @Override
  public void run() {
    world.passTime();
  }
}
