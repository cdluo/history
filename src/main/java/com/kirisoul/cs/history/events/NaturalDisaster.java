package com.kirisoul.cs.history.events;

public class NaturalDisaster extends Event{

  public NaturalDisaster(int year1, String to1) {
    super(year1, to1);
    
    super.getPop()[1] = -(50 + (int)Math.round(Math.random()*250));
    super.getGdp()[0] = -(1 + Math.random()*4);
    super.getGdp()[1] = -(100 + (int)Math.round(Math.random()*100));
    super.getSocial()[1] = -(5);
    super.getLiving()[1] = -(2);
    
    super.setName("Natural Disaster");
  }
}
