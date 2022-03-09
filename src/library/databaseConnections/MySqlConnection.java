package library.databaseConnections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {

  private static final String dbURL =  "jdbc:mysql://localhost:3306/Library";
  private static final String user = "root";
  private static final String password = "root";
  private static Connection connection;

  private MySqlConnection() {}

  public static Connection getConnection() throws SQLException {
    if (connection == null) {
      synchronized (MySqlConnection.class) {
        if (connection == null) {
          connection = DriverManager.getConnection(dbURL,user,password);
        }
      }
    }
    return connection;
  }

  public void close() throws SQLException {
    if (connection != null) {
      connection.close();
    }
  }
}
