package com.kirisoul.cs.history.events;

public class EconomicBoom extends Event{

  public EconomicBoom(int year1, String to1) {
    super(year1, to1);
    
    super.getGdp()[0] = 5 + Math.random() * 5;
    
    super.setName("Economic Boom");
  }
}
