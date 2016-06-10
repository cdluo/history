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
  private List<Nation> nations;
  
  /**
   * Constructor
   * @param db the sqlite database
   * @throws ClassNotFoundException exception
   * @throws SQLException exception
   */
  public World(String db) throws ClassNotFoundException, SQLException{
    query = new SQLQuery(db);
    nations = buildNations();
  }
  
  /**
   * Adds a nation to both the sqlite database and our local nation list.
   * @param name of the nation
   * @param gdp of the nation
   * @param pop of the nation
   * @throws SQLException exception
   */
  public void addNation(String name, int gdp, int pop) throws SQLException{
    query.addNation(name, pop, gdp);
    Nation added = new Nation(name, pop, gdp);
    nations.add(added);
  }
  
  /**
   * Passes time
   * @throws SQLException exception
   */
  public void passTime() throws SQLException{
    for(Nation n: nations){
      
      n.growPop();
      n.growGdp();
      
      query.updatePop(n.getName(), n.getPop());
      query.updateGdp(n.getName(), n.getGdp());
      
      System.out.println(n.toString());
    }
  }
  
  /**
   * Builds the nations list at instantiation.
   * passTime() will update this.
   * @return ArrayList of Nations
   * @throws SQLException exception
   */
  public List<Nation> buildNations() throws SQLException{
    
    List<Nation> nations1 = new ArrayList<Nation>();
    
    for(String name: query.getNationNames()){
      Nation n = new Nation(name, query.queryPop(name), query.queryGdp(name));
      nations1.add(n);
    }
   
    return nations1;
  }
  
  /**
   * Returns the ArrayList of Nations.
   * @return nations
   */
  public List<Nation> getNations(){
    return nations;
  }
}
