package library.databaseConnections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {
  /**
   * Mysql database url with name of the {@code databse} to connect
   */
  private static final String dbURL =  "jdbc:mysql://localhost:3306/Library";
  /**
   * Mysql database user
   */
  private static final String user = "root";
  /**
   * Mysql database password
   */
  private static final String password = "root";

  private static Connection connection;

  private MySqlConnection() {}


  /**
   * getConnection returns {@code mysql} connection or throws {@code SQLException}
   *
   * @return Connection to mysql database
   * @throws SQLException if a database access error occurs or the url is {@code null}
   */
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


  /**
   * Closes the connection to {@code mysql} database
   * @throws SQLException if error is occured while closing the connection
   */
  public void close() throws SQLException {
    if (connection != null) {
      connection.close();
    }
  }
}
