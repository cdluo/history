package com.kirisoul.cs.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
   */
  public SQLQuery(String db) throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:" + db;
    conn = DriverManager.getConnection(urlToDB);

    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys = ON;");
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
    String query = "SELECT Name FROM Nations";

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
    String query = "SELECT Pop FROM Nations WHERE " + "name = ?";

    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, name);

    ResultSet rs = prep.executeQuery();

    int pop = rs.getInt(1);

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
}
