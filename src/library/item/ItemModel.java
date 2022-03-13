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


  /**
   * Returns the list of allowed item types
   *
   * @return ObservableList<String> is a list of Names of the item type
   * @throws SQLException if any exception occured while fetching the list
   */
  public ObservableList<String> getItemTypeLits() throws SQLException {
    try (
      PreparedStatement statment = connection.prepareStatement("select Type from Library.Item_Type ORDER BY TYPE;");
      ResultSet rs = statment.executeQuery();
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


  /**
   * Returns the list of items
   *
   * @return ObservableList<Item> is a list of {@code items}
   * @throws SQLException if any exception occured while fetching the list
   */
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


  /**
   * Creates an item in the {@code library} with the properties provided in the
   * item parameter}
   *
   * @param item contains the properties like {@code Title, Publisher...}
   * @throws SQLException if any exception occured while
   *                      creating the item
   */
  public void createItem(Item item) throws SQLException {
    String query = "CALL Library.insertItem(?, ?, ?, ?, ?, ?, ?, ?);";
    try {
      CallableStatement statment = connection.prepareCall(query);
      setCommonColumns(statment, item);
      statment.executeQuery();
    } catch (SQLException e) {
      throw e;
    }
  }


  /**
   * Updates an item in the {@code library} with the properties provided in the
   * item parameter
   *
   * @param item contains the properties to be updated as well as old properties
   * @throws SQLException if any exception occured while updating the item
   */
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


  /**
   * Deletes an item from the {@code library}
   *
   * @param item to be deleted
   * @throws SQLException if any exception occured while deleting the item
   */
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


  /**
   * Searches item(s) with the properties provied in the {@code itemToSearch}
   * parameter
   *
   * @param itemToSearch contains the properties to be searched
   * @return ObservableList<Item> is a list of {@code item(s)} which matches the search criteria
   * @throws SQLException if any exception occured while searching the item(s)
   */
  public ObservableList<Item> searchitems(Item itemToSearch) throws SQLException {
    String query = getSerachQuery(itemToSearch);
    PreparedStatement statment = connection.prepareStatement(query);
    int counter = 1;

    if (itemToSearch.getTitle() != null &&
      itemToSearch.getTitle().trim() != "") {
      statment.setString(counter++, "%" + itemToSearch.getTitle() + "%");
    }
    if (itemToSearch.getAuthors() != null &&
        itemToSearch.getAuthors().trim() != "") {
      for (String author : itemToSearch.getAuthors().split(",")) {
        if (author.trim() != "") {
          statment.setString(counter++, "%" + author.trim() + "%");
        }
      }
    }
    if (itemToSearch.getDeweyNumberField() != null &&
        itemToSearch.getDeweyNumberField().trim() != "") {
      statment.setString(counter++, itemToSearch.getDeweyNumberField());
    }
    if (itemToSearch.getISBN() != null &&
        itemToSearch.getISBN().trim() != "") {
      statment.setString(counter++, itemToSearch.getISBN());
    }
    if (itemToSearch.getNumber_Of_Copies() > 0) {
      statment.setInt(counter++, itemToSearch.getNumber_Of_Copies());
    }
    if (itemToSearch.getItem_Type() != null &&
        itemToSearch.getItem_Type().trim() != "") {
      statment.setString(counter++, itemToSearch.getItem_Type());
    }
    if (itemToSearch.getPublication_Date() != null &&
        itemToSearch.getPublication_Date().trim() != "") {
      statment.setString(counter++, itemToSearch.getPublication_Date());
    }

    ResultSet rs = statment.executeQuery();
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


  /**
   * sets values of the item columns
   *
   * @param statment is the query
   * @param item contains the properties of the item
   * @throws SQLException
   */
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


  /**
   * Generates and returns the query for searching the item(s) using the {@code not null} properties provided in the {@code item} parameter
   * @param item contains properties of the item(s) to be searched
   * @return String - Returns query string
   */
  private String getSerachQuery(Item item) {
    String query =  """
      select
        Item_Description.Item_Description_ID,
        Item.ISBN, Item_Description.Title,
        Item_Description.Dewey_Decimal_System_Number,
        Item.Item_Type, Publisher.Name as Publisher,
        Item.Number_Of_Copies, Item_Description.Publication_Date,
        Item.Item_ID, Publisher.Publisher_ID,
        group_concat(Author.Name separator ', ') as Authors
      from Item_Description
      inner join Publisher using(Publisher_ID)
      inner join Item using(Item_Description_ID)
      left join Authorship using(Item_Description_ID)
      left join Author using(Author_ID)
      where
        Item.Item_ID in (
          select Item.Item_ID
          from Item_Description
          inner join Publisher using(Publisher_ID)
          inner join Item using(Item_Description_ID)
          left join Authorship using(Item_Description_ID)
          left join Author using(Author_ID)
          where
    """;
      if (
        item.getTitle() != null &&
        item.getTitle().trim() != ""
      ) {
        query += "\n Item_Description.Title like ? \n and ";
      }
      if (
        item.getAuthors() != null &&
        item.getAuthors().trim() != ""
      ) {
        Boolean start = true;
        for (String author: item.getAuthors().split(",")) {
          if (author.trim() != "") {
            if (start) {
              query += " ( ";
              start = false;
            }
            query += "\n Author.Name like ? \n or ";
          }
        }
        if (!start) {
          query = query.substring(0, query.lastIndexOf(" or "));
          query += " ) \n and ";
        }
      }
      if (
        item.getDeweyNumberField() != null &&
        item.getDeweyNumberField().trim() != ""
      ) {
        query += "\n Item_Description.Dewey_Decimal_System_Number = ? \n and ";
      }
      if (
        item.getISBN() != null &&
        item.getISBN().trim() != ""
      ) {
        query += "\n Item.ISBN = ? \n and ";
      }
      if (item.getNumber_Of_Copies() > 0) {
        query += "\n Item.Number_Of_Copies = ? \n and ";
      }
      if (
        item.getItem_Type() != null &&
        item.getItem_Type().trim() != ""
      ) {
        query += "\n Item.Item_Type = ? \n and ";
      }
      if (
        item.getPublication_Date() != null &&
        item.getPublication_Date().trim() != ""
      ) {
        query += "\n Item_Description.Publication_Date = ? \n and ";
      }
      query = query.substring(0, query.lastIndexOf(" and "));
      query += """
      )
      group by
        Item_Description.Item_Description_ID, Item.ISBN,
        Item_Description.Title,
        Item_Description.Dewey_Decimal_System_Number,
        Item.Item_Type,
        Publisher.Name, Item.Number_Of_Copies,
        Item_Description.Publication_Date,
        Item.Item_ID, Publisher.Publisher_ID;
    """;
    return query;
  }
}
