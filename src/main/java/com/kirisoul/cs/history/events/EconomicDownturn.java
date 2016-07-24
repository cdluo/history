package com.kirisoul.cs.history.events;

public class EconomicDownturn extends Event{

  public EconomicDownturn(int year1, String to1) {
    super(year1, to1);
    
    super.getGdp()[0] = -(5 + Math.random() * 5);
    super.setName("Economic Downturn");
  }
}
