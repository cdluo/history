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
  public void addNation(String name, int gdp, int pop, int social, int living) throws SQLException{
    query.addNation(name, pop, gdp, social, living);
    Nation added = new Nation(name, pop, gdp, social, living);
    nations.add(added);
  }
  
  /**
   * Passes time
   * @throws SQLException exception
   */
  public void passTime() throws SQLException{
    
    query.IncYear();
    System.out.println("Current Year: " + query.getYear());
    System.out.println("");
    
    String event = query.getEvent(query.getYear());

    if(event != null){
      //Do event stuff (new classes for each event)
      System.out.println(event + " happened!");
      System.out.println("");
    }
    
    for(Nation n: nations){
      
      n.passTime();
      
      query.updatePop(n.getName(), n.getPop());
      query.updateGdp(n.getName(), n.getGdp());
      query.updateSocial(n.getName(), n.getSocial());
      query.updateLiving(n.getName(), n.getLiving());
      
      System.out.println(n.toString());  
    }
    
    System.out.println("----------------");
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
      Nation n = new Nation(name, query.queryPop(name), query.queryGdp(name),
                            query.querySocial(name), query.queryLiving(name));
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
