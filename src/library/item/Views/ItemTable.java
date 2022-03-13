package library.item.Views;

import java.sql.SQLException;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import library.item.Item;
import library.item.ItemController;

public class ItemTable {
  private VBox vbox;
  private List<Item>items;
  private ItemController itemController;

  public ItemTable(VBox vbox, List<Item> items, ItemController itemController) {
    this.vbox = vbox;
    this.items = items;
    this.itemController = itemController;
  }

  /**
   * creates the table view for the list of items provided and attachs the table to the {@code vbox}
   */
  public void createItemTable() {
    TableView<Item> itemTable = new TableView<>();

    TableColumn<Item, String> isbn = new TableColumn<>("ISBN");
    isbn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));

    TableColumn<Item, String> title = new TableColumn<>("Title");
    title.setCellValueFactory(new PropertyValueFactory<>("Title"));

    TableColumn<Item, String> dewey = new TableColumn<>("Dewey Decimal Number");
    dewey.setCellValueFactory(new PropertyValueFactory<>("DeweyNumberField"));

    TableColumn<Item, String> itemType = new TableColumn<>("Type");
    itemType.setCellValueFactory(new PropertyValueFactory<>("Item_Type"));

    TableColumn<Item, String> publisher = new TableColumn<>("Publisher");
    publisher.setCellValueFactory(new PropertyValueFactory<>("Publisher"));

    TableColumn<Item, Integer> copies = new TableColumn<>("Number Of Copies");
    copies.setCellValueFactory(new PropertyValueFactory<>("Number_Of_Copies"));

    TableColumn<Item, String> publicationDate = new TableColumn<>("Publication Date");
    publicationDate.setCellValueFactory(new PropertyValueFactory<>("Publication_Date"));

    TableColumn<Item, String> authors = new TableColumn<>("Authors");
    authors.setCellValueFactory(new PropertyValueFactory<>("Authors"));

    itemTable.getColumns().add(isbn);
    itemTable.getColumns().add(title);
    itemTable.getColumns().add(itemType);
    itemTable.getColumns().add(copies);
    itemTable.getColumns().add(publisher);
    itemTable.getColumns().add(publicationDate);
    itemTable.getColumns().add(authors);
    itemTable.getColumns().add(dewey);

    itemTable.getItems().addAll(items);

    itemTable.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      Item selectedItem = itemTable.getSelectionModel().getSelectedItem();
      if (selectedItem != null) {
        try {
          itemController.list(false, selectedItem, false);
        } catch (SQLException e1) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Someting went wrong");
          alert.setHeaderText(null);
          alert.setContentText(e1.getMessage());
          alert.showAndWait();
          e1.printStackTrace();
        }
      }
    });

    vbox.getChildren().add(itemTable);
    vbox.setPadding(new Insets(10));
    vbox.setSpacing(10);
  }
}
