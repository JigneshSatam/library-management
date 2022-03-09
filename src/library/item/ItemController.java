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

  public void list(Boolean clearList, Item item) throws SQLException {
    vbox.getChildren().clear();
    ObservableList<String> publishers = new PublisherModel(connection).getPublisherList();

    ItemModel itemModel = new ItemModel(connection);
    ObservableList<String> types = itemModel.getItemTypeLits();

    ObservableList<Item> items = FXCollections.observableArrayList();
    if (!clearList) {
      items = itemModel.getItemList();
    }

    new ItemForm(vbox, publishers, types, item, this).createItemForm();
    new ItemTable(vbox, items, this).createItemTable();
  }

  public void createItem(Item item) throws SQLException {
    new ItemModel(connection).createItem(item);
  }

  public void updateItem(Item item) throws SQLException {
    new ItemModel(connection).updateItem(item);
  }

  public void deleteItem(Item item) throws SQLException {
    new ItemModel(connection).deleteItem(item);
  }

}
