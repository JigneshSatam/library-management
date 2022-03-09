package library.item;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemModel {
  private Connection connection;

  public ItemModel(Connection connection) {
    this.connection = connection;
  }

  public ObservableList<String> getItemTypeLits() throws SQLException {
    try (
      PreparedStatement stmnt = connection.prepareStatement("select Type from Library.Item_Type ORDER BY TYPE;");
      ResultSet rs = stmnt.executeQuery();
    ) {
      ObservableList<String> typeList = FXCollections.observableArrayList();

      while (rs.next()) {
        try {
          typeList.add(rs.getString("Type"));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      return typeList;
    }
  }

  public ObservableList<Item> getItemList() throws SQLException {
    CallableStatement stmnt = connection.prepareCall("call Library.getItems();");
    ResultSet rs = stmnt.executeQuery();
    ObservableList<Item> itemList = FXCollections.observableArrayList();
    while (rs.next()) {
      Item item;
      try {
        item = new Item(rs.getString("ISBN"));
        item.setItem_ID(rs.getInt("Item_ID"));
        item.setItem_Description_ID(rs.getInt("Item_Description_ID"));
        item.setPublisher_ID(rs.getInt("Publisher_ID"));
        item.setTitle(rs.getString("Title"));
        item.setDeweyNumberField(rs.getString("Dewey_Decimal_System_Number"));
        item.setItem_Type(rs.getString("Item_Type"));
        item.setPublisher(rs.getString("Publisher"));
        item.setNumber_Of_Copies(rs.getInt("Number_Of_Copies"));
        item.setPublication_Date(rs.getString("Publication_Date"));
        item.setAuthors(rs.getString("Authors"));
        itemList.add(item);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return itemList;
  }

  public void createItem(Item item) throws SQLException {
    String query = "CALL Library.insertItem(?, ?, ?, ?, ?, ?, ?, ?);";
    try {
      CallableStatement statment = connection.prepareCall(query);
      setCommonColumns(statment, item);
      ResultSet rs = statment.executeQuery();
      System.out.println("isBeforeFirst: " + rs.isBeforeFirst());
      // System.out.println("rowInserted: " + rs.rowInserted());
    } catch (SQLException e) {
      throw e;
    }
  }

  public void updateItem(Item item) throws SQLException {
    String query = "CALL Library.updateItem(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    try {
      CallableStatement statment = connection.prepareCall(query);
      setCommonColumns(statment, item);
      statment.setInt("Item_ID_IN", item.getItem_ID());
      statment.setInt("Item_Description_ID_IN", item.getItem_Description_ID());
      statment.executeQuery();
    } catch (SQLException e) {
      throw e;
    }
  }

  public void deleteItem(Item item) throws SQLException {
    String query = "CALL Library.deleteItem(?, ?);";
    try {
      CallableStatement statment = connection.prepareCall(query);
      statment.setInt("Item_ID_IN", item.getItem_ID());
      statment.setInt("Item_Description_ID_IN", item.getItem_Description_ID());
      statment.executeQuery();
    } catch (SQLException e) {
      throw e;
    }
  }

  private void setCommonColumns(CallableStatement statment, Item item) throws SQLException {
    statment.setString("Title_IN", item.getTitle());
    statment.setString("Publication_Date_IN", item.getPublication_Date());
    statment.setString("Publisher_Name_IN", item.getPublisher());
    statment.setString("Dewey_Decimal_System_Number_IN", item.getDeweyNumberField());
    statment.setString("Authors_IN", item.getAuthors());
    statment.setString("Item_Type_IN", item.getItem_Type());
    statment.setInt("Number_Of_Copies_IN", item.getNumber_Of_Copies());
    statment.setString("ISBN_IN", item.getISBN());
  }
}
