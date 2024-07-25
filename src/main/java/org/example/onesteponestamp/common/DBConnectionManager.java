package org.example.onesteponestamp.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DB Connection을 싱글톤으로 관리
 * 사용할 때는 DBConnectionManager.getInstance.getConnection으로 가져와서 사용.
 */
public class DBConnectionManager {
  private static DBConnectionManager instance;
  private static Connection connection;

  private DBConnectionManager() {
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      String url = "jdbc:oracle:thin:@localhost:1521:xe";
      String username = "java";
      String password = "oracle";
      connection = DriverManager.getConnection(url, username, password);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  public static DBConnectionManager getInstance() {
    if (instance == null) {
      synchronized (DBConnectionManager.class) {
        if (instance == null) {
          instance = new DBConnectionManager();
        }
      }
    }
    return instance;
  }

  public Connection getConnection() {
    return connection;
  }
}
