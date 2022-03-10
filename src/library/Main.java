package library;

import java.sql.Connection;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import library.databaseConnections.MySqlConnection;
import library.item.ItemController;
import library.item.Views.ItemForm;

public class Main extends Application {

  private ItemController itemCtrl;
  private Connection connection;

  @Override
  public void start(Stage stage) throws Exception {
    connection = MySqlConnection.getConnection();
    VBox vbox = new VBox();
    itemCtrl = new ItemController(connection, vbox);
    itemCtrl.list(false, null, false);

    BorderPane root = new BorderPane();
    root.setId("rootPane");
    root.setCenter(vbox);

    Scene scene = new Scene(root, 1000, 800);
    stage.setScene(scene);
    stage.setTitle("Library Book Entry");
    stage.show();
    ItemForm.setStatus(vbox, ItemForm.ADD_BOOK);
  }

  @Override
  public void stop() throws Exception {
    if (connection != null) {
      connection.close();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
