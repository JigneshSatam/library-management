package library;

import java.sql.Connection;
import java.sql.SQLException;

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


  /**
   * start method is invoked to start JavaFX application
   *
   * @param stage is a stage for JavaFX application
   * @throws SQLException if any execption occurs while accessing the database
   */
  @Override
  public void start(Stage stage) throws SQLException {
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


  /**
   * Stop is invoked when the JavaFX application is closed.
   * It closes the database connection if any
   *
   * @throws SQLException
   */
  @Override
  public void stop() throws SQLException {
    if (connection != null) {
      connection.close();
    }
  }


  /**
   * main is the entry point of the application
   * @param args
   */
  public static void main(String[] args) {
    launch(args);
  }
}
