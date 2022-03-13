package library.item;

import java.sql.Connection;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import library.item.Views.ItemForm;
import library.item.Views.ItemTable;
import library.publisher.PublisherModel;

public class ItemController {
  VBox vbox;
  Connection connection;

  public ItemController(Connection connection, VBox vbox) {
    this.connection = connection;
    this.vbox = vbox;
  }


  /**
   * list generates the item form to perform CRUD operations as well as displays the list of all items in the library
   * @param clearList is used to clear all the fields in the item form as well as the list of items displayed in the table section
   * @param item is used to fill the item form details provided in the item
   * @param search is used to search the items with the properties provided in the {@code item} parameter
   * @throws SQLException if any sql operation fails
   */
  public void list(Boolean clearList, Item item, Boolean search) throws SQLException {
    vbox.getChildren().clear();
    ObservableList<String> publishers = new PublisherModel(connection).getPublisherList();

    ItemModel itemModel = new ItemModel(connection);
    ObservableList<String> types = itemModel.getItemTypeLits();

    ObservableList<Item> items = FXCollections.observableArrayList();
    if (!clearList) {
      if (search) {
        items = itemModel.searchitems(item);
      } else {
        items = itemModel.getItemList();
      }
    }

    new ItemForm(vbox, publishers, types, item, this).createItemForm();
    new ItemTable(vbox, items, this).createItemTable();
  }


  /**
   * Creates an item in the {@code library} with the properties provided in the
   * item parameter
   *
   * @param item contains the properties like {@code Title, Publisher...}
   * @throws SQLException if any exception occured while
   * creating the item
   */
  public void createItem(Item item) throws SQLException {
    new ItemModel(connection).createItem(item);
  }


  /**
   * Updates an item in the {@code library} with the properties provided in the
   * item parameter
   *
   * @param item contains the properties to be updated as well as old properties
   *             like {@code Title, Publisher...}
   * @throws SQLExceptionwhile if any exception occured while updating the item
   */
  public void updateItem(Item item) throws SQLException {
    new ItemModel(connection).updateItem(item);
  }


  /**
   * Deletes an item from the {@code library}
   *
   * @param item to be deleted
   * @throws SQLException if any exception occured while deleting the item
   */
  public void deleteItem(Item item) throws SQLException {
    new ItemModel(connection).deleteItem(item);
  }

}
