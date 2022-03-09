package library.publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PublisherModel {
  private Connection connection;

  public PublisherModel(Connection connection) {
    this.connection = connection;
  }

  public ObservableList<String> getPublisherList() throws SQLException {
    try (
      PreparedStatement stmnt = connection.prepareStatement("select Name from Library.Publisher;");
      ResultSet rs = stmnt.executeQuery();
    ) {
      ObservableList<String> publisherList = FXCollections.observableArrayList();

      while (rs.next()) {
        try {
          publisherList.add(rs.getString("Name"));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      return publisherList;
    }
  }
}
