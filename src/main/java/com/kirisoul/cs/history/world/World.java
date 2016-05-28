package com.kirisoul.cs.history.world;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kirisoul.cs.database.SQLQuery;
import com.kirisoul.cs.history.entities.Nation;

/**
 * Conducts all the logic for the game
 * @author ChrisLuo
 */

public class World {

  private SQLQuery query;
  private List<String> nations;
  private Nation nation;
  
  public World(String db) throws ClassNotFoundException, SQLException{
    query = new SQLQuery(db);
    nations = query.getNationNames();
    nation = new Nation();
  }
  
  public void addNation(String name, int pop, int gdp) throws SQLException{
    query.addNation(name, pop, gdp);
  }
  
  public void passTime() throws SQLException{
    for(String n: nations){
      int newPop = nation.growPop(query.queryPop(n));
      int newGdp = nation.growGdp(query.queryGdp(n));
      
      query.updatePop(n, newPop);
      query.updateGdp(n, newGdp);
      
      System.out.println(n + " Pop: " + newPop + "|GDP: " + newGdp);
    }
  }
}
