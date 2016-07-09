package com.kirisoul.cs.history.database;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kirisoul.cs.history.events.Event;
import org.postgresql.Driver;

public class SQLQuery {
  
  /**
   * Connection to the sqlite3 database.
   */
  private Connection conn;

  /**
   * constructor.
   * @param db
   *          the path to the database file
   * @throws ClassNotFoundException
   *           exception
   * @throws SQLException
   *           exception
   * @throws URISyntaxException 
   */
  public SQLQuery() throws ClassNotFoundException, SQLException, URISyntaxException {
    
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("Where is your PostgreSQL JDBC Driver? "
          + "Include in your library path!");
      e.printStackTrace();
      return;
    }
    
    try {
      conn = getConnection();
    } catch (SQLException e) {
      System.out.println("Connection Failed! Check output console");
      e.printStackTrace();
      return;
    }

//    Statement stat = conn.createStatement();
//    stat.executeUpdate("PRAGMA foreign_keys = ON;");
  }
  
  private static Connection getConnection() throws URISyntaxException, SQLException {
    String dbUrl = "jdbc:postgresql://ec2-54-243-249-159.compute-1.amazonaws.com:5432/df708mtap8o1be?user=yiifzbhbiiaaij&password=pnz2ZDE06e8jwAL2hXPbVh2OjF&sslmode=require";

//    String username = dbUri.getUserInfo().split(":")[0];
//    String password = dbUri.getUserInfo().split(":")[1];
//    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

    return DriverManager.getConnection(dbUrl);
  }
  
  /**
   * Adds a nation to the database
   * @param n the nation to be added
   * @throws SQLException exception
   */
  public void addNation(String name, int pop, int gdp, int social, int living) throws SQLException {
    String query = "INSERT INTO Nations VALUES (?,?,?,?,?)";
    PreparedStatement ps = conn.prepareStatement(query);

    ps.setString(1, name);
    ps.setInt(2, pop);
    ps.setInt(3, gdp);
    ps.setInt(4, social);
    ps.setInt(5, living);

    ps.addBatch();
    ps.executeBatch();
    ps.close();
  }
  
  /**
   * Gets a list of all the nations in the database
   * @return List of nations
   * @throws SQLException exception
   */
  public List<String> getNationNames() throws SQLException {
    String query = "SELECT Name FROM nations;";

    PreparedStatement prep = conn.prepareStatement(query);

    ResultSet rs = prep.executeQuery();

    List<String> toReturn = new ArrayList<String>();

    while (rs.next()) {
      String name = rs.getString(1);
      toReturn.add(name);
    }

    rs.close();
    prep.close();

    return toReturn;
  }
  
  /**
   * Given the name of a nation, returns its population
   * @param name of the nation
   * @return population
   * @throws SQLException exception
   */
  public int queryPop(String name) throws SQLException {
    String query = "SELECT Pop FROM Nations WHERE " + "Name = ?";

    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, name);

    ResultSet rs = prep.executeQuery();
    
    rs.next();
    int pop = rs.getInt(1);
    
    System.out.println(pop);

    rs.close();
    prep.close();

    return pop;
  }
  
  /**
   * Given the name of a nation, returns its gdp
   * @param name of the nation
   * @return gdp
   * @throws SQLException exception
   */
  public int queryGdp(String name) throws SQLException {
    String query = "SELECT Gdp FROM Nations WHERE " + "name = ?";

    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, name);

    ResultSet rs = prep.executeQuery();

    rs.next();
    int gdp = rs.getInt(1);

    rs.close();
    prep.close();

    return gdp;
  }
  
  /**
   * Get the Social Stability index for a nation given its name
   * @param name
   * @return social
   * @throws SQLException exception
   */
  public int querySocial(String name) throws SQLException {
    String query = "SELECT Social FROM Nations WHERE " + "name = ?";

    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, name);

    ResultSet rs = prep.executeQuery();

    rs.next();
    int gdp = rs.getInt(1);

    rs.close();
    prep.close();

    return gdp;
  }
  
  /**
   * Gets the living standard of a nation given its name.
   * @param name
   * @return living
   * @throws SQLException
   */
  public int queryLiving(String name) throws SQLException {
    String query = "SELECT Living FROM Nations WHERE " + "name = ?";

    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, name);

    ResultSet rs = prep.executeQuery();

    rs.next();
    int gdp = rs.getInt(1);

    rs.close();
    prep.close();

    return gdp;
  }
  
  /**
   * Updates a nation's population in the database
   * @param name
   * @param newPop
   * @throws SQLException
   */
  public void updatePop(String name, int newPop) throws SQLException {
    String query = "UPDATE Nations SET Pop = ? WHERE name = ?";
    PreparedStatement ps = conn.prepareStatement(query);

    ps.setInt(1, newPop);
    ps.setString(2, name);

    ps.addBatch();
    ps.executeBatch();
    ps.close();
  }
  
  /**
   * Updates a nation's gdp in the database
   * @param name
   * @param newGdp
   * @throws SQLException
   */
  public void updateGdp(String name, int newGdp) throws SQLException {
    String query = "UPDATE Nations SET Gdp = ? WHERE name = ?";
    PreparedStatement ps = conn.prepareStatement(query);

    ps.setInt(1, newGdp);
    ps.setString(2, name);

    ps.addBatch();
    ps.executeBatch();
    ps.close();
  }
  
  /**
   * Updates a nation's social stability in the db.
   * @param name
   * @param newSoc
   * @throws SQLException
   */
  public void updateSocial(String name, int newSoc) throws SQLException {
    String query = "UPDATE Nations SET Social = ? WHERE name = ?";
    PreparedStatement ps = conn.prepareStatement(query);

    ps.setInt(1, newSoc);
    ps.setString(2, name);

    ps.addBatch();
    ps.executeBatch();
    ps.close();
  }
  
  /**
   * Updates a nation's living standards in the db.
   * @param name
   * @param newLiv
   * @throws SQLException
   */
  public void updateLiving(String name, int newLiv) throws SQLException {
    String query = "UPDATE Nations SET Living = ? WHERE name = ?";
    PreparedStatement ps = conn.prepareStatement(query);

    ps.setInt(1, newLiv);
    ps.setString(2, name);

    ps.addBatch();
    ps.executeBatch();
    ps.close();
  }
  
  /////////////////////// Timeline ///////////////////////////
  
  /**
   * Gets the present year.
   * @return present year (int)
   * @throws SQLException exception
   */
  public int getYear() throws SQLException {
    String query = "SELECT Year FROM Timeline WHERE Event = ?";

    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, "Present");

    ResultSet rs = prep.executeQuery();
    
    rs.next();
    int year = rs.getInt(1);
    rs.close();
    prep.close();

    return year;
  }
  
  /**
   * Updates the present year.
   * @throws SQLException exception
   */
  public void IncYear() throws SQLException {
    String query = "UPDATE Timeline SET Year = ? WHERE Event = ?";
    PreparedStatement ps = conn.prepareStatement(query);

    ps.setInt(1, getYear()+1);
    ps.setString(2, "Present");

    ps.addBatch();
    ps.executeBatch();
    ps.close();
  }
  
  /**
  * Adds a timeline event to the database
  * @param e the event to be added
  * @throws SQLException exception
  */
 public void addEvent(int year, String event, String to) throws SQLException {
   String query = "INSERT INTO Timeline VALUES (?,?,?)";
   PreparedStatement ps = conn.prepareStatement(query);

   ps.setInt(1, year);
   ps.setString(2, event);
   ps.setString(3, to);

   ps.addBatch();
   ps.executeBatch();
   ps.close();
 }

 /**
  * Gets the events for a given year
  * @param year integer
  * @return List of events
  * @throws SQLException exception
  */
 public List<Event> getEvent(int year) throws SQLException {
   String query = "SELECT * FROM Timeline WHERE Year = ? AND EVENT != ?;";

   PreparedStatement prep = conn.prepareStatement(query);
   prep.setInt(1, year);
   prep.setString(2, "Present");

   ResultSet rs = prep.executeQuery();
 
   List<Event> events = new ArrayList<Event>();
   
   while (rs.next()) {
     Event newEvent = new Event(rs.getInt(1), rs.getString(2), rs.getString(3));
     events.add(newEvent);
   }
   
   if(events.isEmpty()){
     rs.close();
     prep.close();
     return null;
   }else{
     rs.close();
     prep.close();
     return events;
   }
 }
 
 /**
  * Gets the entire timeline (for logging purposes in the GUI?)
  * @return entire timeline in a list of events
  * @throws SQLException exception
  */
 public List<Event> getTimeline() throws SQLException{
   String query = "SELECT * FROM Timeline;";

   PreparedStatement prep = conn.prepareStatement(query);

   ResultSet rs = prep.executeQuery();
 
   List<Event> events = new ArrayList<Event>();
   
   while (rs.next()) {
     Event newEvent = new Event(rs.getInt(1), rs.getString(2), rs.getString(3));
     events.add(newEvent);
   }
   
   rs.close();
   prep.close();
   return events;
   
 }
}
