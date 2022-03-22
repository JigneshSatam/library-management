package library.item.Views;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import library.item.Item;
import library.item.ItemController;

public class ItemForm {

  public static final String ADD_BOOK = "Add Book";
  public static final String UPDATE_BOOK = "Update Book";

  private VBox vbox;
  private ObservableList<String> publisherList;
  private ItemController itemController;
  private ObservableList<String> types;
  private Item selectedItem;
  private String datePattern = "yyyy-MM-dd";
  private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);

  public ItemForm(VBox vbox, ObservableList<String> publisherList, ObservableList<String> types, Item item, ItemController itemController) {
    this.vbox = vbox;
    this.publisherList = publisherList;
    this.types = types;
    this.selectedItem = item;
    this.itemController = itemController;
  }


  /**
   * Sets the status of screen on the status bar
   * @param vbox is the child of the root node
   * @param status is the status to be displayed
   */
  public static void setStatus(VBox vbox, String status) {
    if (vbox.getParent() != null) {
      Label titleLabel = new Label();
      titleLabel.setText(status);

      HBox statusbar = new HBox();
      statusbar.getChildren().add(titleLabel);
      statusbar.setAlignment(Pos.CENTER);

      BorderPane root = (BorderPane) vbox.getParent();
      root.setBottom(statusbar);
    }
  }


  /**
   * Creates an item form which is used to pass the properites of the item to be created, updated, deleted, and searched.
   */
  public void createItemForm() {
    GridPane gridPane = createFormPane();
    addComponents(gridPane);
    vbox.getChildren().add(gridPane);
    String mode = ADD_BOOK;
    if (
      selectedItem != null && selectedItem.getItem_Description_ID() > 0
    ) {
      mode = UPDATE_BOOK;
    }
    setStatus(vbox, mode);
  }


  /**
   * Creates grid pane for the item form
   *
   * @return GridPane
   */
  private GridPane createFormPane() {

    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(20, 20, 20, 20));

    gridPane.setHgap(10);
    gridPane.setVgap(10);

    ColumnConstraints columnOneConstraints = new ColumnConstraints();
    columnOneConstraints.setHalignment(HPos.RIGHT);
    columnOneConstraints.setPercentWidth(15);

    ColumnConstraints columnTwoConstrains = new ColumnConstraints();
    columnTwoConstrains.setPercentWidth(85);
    columnTwoConstrains.setHgrow(Priority.ALWAYS);

    gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

    return gridPane;
  }


  /**
   * Adds the UI fields to the grid pane
   * @param gridPane
   */
  private void addComponents(GridPane gridPane) {

    Label titleLabel = new Label("Title");
    gridPane.add(titleLabel, 0, 0);

    TextField titleField = new TextField();
    gridPane.add(titleField, 1, 0);

    Label authorsLabel = new Label("Author(s)");
    gridPane.add(authorsLabel, 0, 1);

    TextField authorsField = new TextField();
    gridPane.add(authorsField, 1, 1);

    Label isbnLabel = new Label("ISBN");
    gridPane.add(isbnLabel, 0, 2);

    TextField isbnField = new TextField();
    gridPane.add(isbnField, 1, 2);
    applyIntegerFormatToField(isbnField);

    Label deweyNumberLabel = new Label("Dewey Number");
    gridPane.add(deweyNumberLabel, 0, 3);

    TextField deweyNumberField = new TextField();
    gridPane.add(deweyNumberField, 1, 3);

    Label publisherLabel = new Label("Publisher");
    gridPane.add(publisherLabel, 0, 4);

    ComboBox<String> publisherComboBox = new ComboBox<String>(publisherList);
    gridPane.add(publisherComboBox, 1, 4);

    Label numberOfCopiesLabel = new Label("Number Of Copies");
    gridPane.add(numberOfCopiesLabel, 0, 5);

    TextField numberOfCopiesField = new TextField();
    gridPane.add(numberOfCopiesField, 1, 5);
    applyIntegerFormatToField(numberOfCopiesField);

    Label typeLabel = new Label("Type");
    gridPane.add(typeLabel, 0, 6);

    ComboBox<String> typeComboBox = new ComboBox<String>(types);
    gridPane.add(typeComboBox, 1, 6);

    Label publicationDateLabel = new Label("Publication Date");
    gridPane.add(publicationDateLabel, 0, 7);

    DatePicker publicationDateField = new DatePicker();
    publicationDateField.setEditable(false);

    // No future dates constraint
    publicationDateField.setDayCellFactory(parameter -> new DateCell() {
      @Override
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
      }
    });

    // Format to yyyy-MM-dd
    publicationDateField.setConverter(new StringConverter<LocalDate>() {
      { publicationDateField.setPromptText(datePattern.toLowerCase()); }

      @Override public String toString(LocalDate date) {
        return dateToString(date);
      }

      @Override public LocalDate fromString(String string) {
        return stringToDate(string);
      }
    });
    gridPane.add(publicationDateField, 1, 7);


    setFieldValues(titleField, authorsField, isbnField, publisherComboBox,
    numberOfCopiesField, publicationDateField, deweyNumberField, typeComboBox);

    Button saveButton = getSaveButton(titleField, authorsField, isbnField, publisherComboBox, numberOfCopiesField, publicationDateField, deweyNumberField, typeComboBox);

    Button searchButton = getSearchButton(titleField, authorsField, isbnField, publisherComboBox, numberOfCopiesField, publicationDateField, deweyNumberField, typeComboBox);

    HBox buttonBox = new HBox();
    buttonBox.setSpacing(20);
    buttonBox.getChildren().addAll(saveButton,getClearButton(), searchButton, getDeleteButton());

    gridPane.add(buttonBox, 1, 8);
  }


  /**
   * Returns the save button
   *
   * @param titleField
   * @param authorsField
   * @param isbnField
   * @param publisherComboBox
   * @param numberOfCopiesField
   * @param publicationDateField
   * @param deweyNumberField
   * @param typeComboBox
   * @return Button
   */
  public Button getSaveButton(
    TextField titleField, TextField authorsField,
    TextField isbnField, ComboBox<String> publisherComboBox,
    TextField numberOfCopiesField, DatePicker publicationDateField,
    TextField deweyNumberField, ComboBox<String> typeComboBox
  ) {
    Button saveButton = new Button("Save");
    saveButton.setDefaultButton(true);
    saveButton.setPrefHeight(30);
    saveButton.setPrefWidth(80);
    saveButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if (titleField.getText().isEmpty()) {
          showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter the Title");
          return;
        }

        if (authorsField.getText().isEmpty()) {
          showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter the Author(s)");
          return;
        }

        if (isbnField.getText().isEmpty()) {
          showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter the ISBN");
          return;
        }

        if (isbnField.getText().length() != 10 && isbnField.getText().length() != 13) {
          showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter the correct ISBN");
          return;
        }

        if (publisherComboBox.getSelectionModel().isEmpty()) {
          showAlert(Alert.AlertType.ERROR, "Form Error!", "Please select the publisher");
          return;
        }

        if (numberOfCopiesField.getText().isEmpty()) {
          showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter the Number Of Copies");
          return;
        }

        if (typeComboBox.getSelectionModel().isEmpty()) {
          showAlert(Alert.AlertType.ERROR, "Form Error!", "Please select the book type");
          return;
        }

        if (publicationDateField.getValue() == null) {
          showAlert(Alert.AlertType.ERROR, "Form Error!",
              "Please enter the Publication Date");
          return;
        }

        try {
          Item item = new Item(isbnField.getText().trim());
          item.setTitle(titleField.getText().trim());
          item.setAuthors(authorsField.getText().trim());
          item.setDeweyNumberField(deweyNumberField.getText().trim());
          item.setPublisher(publisherComboBox.getSelectionModel().selectedItemProperty().getValue());
          item.setNumber_Of_Copies(Integer.parseInt(numberOfCopiesField.getText()));
          item.setItem_Type(typeComboBox.getSelectionModel().selectedItemProperty().getValue());
          item.setPublication_Date(publicationDateField.getValue().toString());
          if (
            selectedItem != null &&
            selectedItem.getItem_Description_ID() > 0
          ) {
            item.setItem_ID(selectedItem.getItem_ID());
            item.setItem_Description_ID(selectedItem.getItem_Description_ID());
            itemController.updateItem(item);
            showAlert(
                Alert.AlertType.CONFIRMATION,
                "Item Update", "Item " + titleField.getText().trim() + " updated successfully.");
          } else {
            itemController.createItem(item);
            showAlert(
              Alert.AlertType.CONFIRMATION,
              "Item Create", "Item " + titleField.getText().trim() + " created successfully.");
          }
          itemController.list(false, null, false);
        } catch (Exception e) {
          showAlert(
              Alert.AlertType.ERROR,
              "Item not saved", e.getMessage());
          e.printStackTrace();
        }
      }
    });
    return saveButton;
  }


  /**
   * Returns the clear button
   *
   * @return Button
   */
  public Button getClearButton() {
    Button clearButton = new Button("Clear");
    clearButton.setPrefHeight(30);
    clearButton.setPrefWidth(80);
    clearButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          itemController.list(true, null, false);
        } catch (SQLException e) {
          showAlert(
              Alert.AlertType.ERROR,
              "Someting went wrong", e.getMessage());
          e.printStackTrace();
        }
      }
    });
    return clearButton;
  }


  /**
   *
   * Returns the search button
   *
   * @param titleField
   * @param authorsField
   * @param isbnField
   * @param publisherComboBox
   * @param numberOfCopiesField
   * @param publicationDateField
   * @param deweyNumberField
   * @param typeComboBox
   * @return Button
   */
  public Button getSearchButton(
    TextField titleField, TextField authorsField,
    TextField isbnField, ComboBox<String> publisherComboBox,
    TextField numberOfCopiesField, DatePicker publicationDateField,
    TextField deweyNumberField, ComboBox<String> typeComboBox
  ) {
    Button searchButton = new Button("Search");
    searchButton.setPrefHeight(30);
    searchButton.setPrefWidth(80);
    searchButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          Boolean allFieldsEmpty = true;
          Item item = new Item();

          if (!titleField.getText().isEmpty()) {
            item.setTitle(titleField.getText());
            allFieldsEmpty = false;
          }

          if (!authorsField.getText().isEmpty()) {
            item.setAuthors(authorsField.getText());
            allFieldsEmpty = false;
          }

          if (!deweyNumberField.getText().isEmpty()) {
            item.setDeweyNumberField(deweyNumberField.getText());
            allFieldsEmpty = false;
          }

          if (!isbnField.getText().isEmpty()) {
            item.setISBN(isbnField.getText());
            allFieldsEmpty = false;
          }

          if (!publisherComboBox.getSelectionModel().isEmpty()) {
            item.setPublisher(publisherComboBox.getSelectionModel().selectedItemProperty().getValue());
          }

          if (!numberOfCopiesField.getText().isEmpty()) {
            item.setNumber_Of_Copies(Integer.parseInt(numberOfCopiesField.getText()));
            allFieldsEmpty = false;
          }

          if (!typeComboBox.getSelectionModel().isEmpty()) {
            item.setItem_Type(typeComboBox.getSelectionModel().selectedItemProperty().getValue());
            allFieldsEmpty = false;
          }

          if (publicationDateField.getValue() != null) {
            item.setPublication_Date(publicationDateField.getValue().toString());
            allFieldsEmpty = false;
          }

          if (allFieldsEmpty) {
            showAlert(
              Alert.AlertType.ERROR,
              "Item not searched", "Nothing to search"
            );
            return;
          }
          itemController.list(false, item, true);
        } catch (Exception e) {
          showAlert(
              Alert.AlertType.ERROR,
              "Item not searched", e.getMessage());
          e.printStackTrace();
        }
      }
    });
    return searchButton;
  }


  /**
   * Returns the delete button
   *
   * @return Button
   */
  public Button getDeleteButton() {
    Button deleteButton = new Button("Delete");
    deleteButton.setPrefHeight(30);
    deleteButton.setPrefWidth(80);
    deleteButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          if (
            selectedItem != null &&
            selectedItem.getItem_Description_ID() > 0
          ) {
            itemController.deleteItem(selectedItem);
            itemController.list(false, null, false);
          }
        } catch (SQLException e) {
          showAlert(
              Alert.AlertType.ERROR,
              "Someting went wrong", e.getMessage());
          e.printStackTrace();
        }
      }
    });
    return deleteButton;
  }


  /**
   * showAlert is used to display the success notice or an error
   *
   * @param alertType
   * @param title
   * @param message
   */
  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }


  /**
   * sets the field values of the item form from the selected item
   * @param titleField
   * @param authorsField
   * @param isbnField
   * @param publisherComboBox
   * @param numberOfCopiesField
   * @param publicationDateField
   * @param deweyNumberField
   * @param typeComboBox
   */
  public void setFieldValues(
    TextField titleField, TextField authorsField,
    TextField isbnField, ComboBox<String> publisherComboBox,
    TextField numberOfCopiesField, DatePicker publicationDateField,
    TextField deweyNumberField, ComboBox<String> typeComboBox
  ) {
    if (selectedItem != null) {
      if (selectedItem.getTitle() != null) {titleField.setText(selectedItem.getTitle());}
      if (selectedItem.getAuthors() != null) {authorsField.setText(selectedItem.getAuthors());}
      if (selectedItem.getISBN() != null) {isbnField.setText(selectedItem.getISBN());}
      if (selectedItem.getDeweyNumberField() != null)
        {deweyNumberField.setText(selectedItem.getDeweyNumberField());}
      if (selectedItem.getPublisher() != null)
        {publisherComboBox.getSelectionModel().select(selectedItem.getPublisher());}
      if (selectedItem.getNumber_Of_Copies() > 0)
        {numberOfCopiesField.setText(String.valueOf(selectedItem.getNumber_Of_Copies()));}
      if (selectedItem.getItem_Type() != null)
        {typeComboBox.getSelectionModel().select(selectedItem.getItem_Type());}
      if (selectedItem.getPublication_Date() != null)
        {publicationDateField.setValue(stringToDate(selectedItem.getPublication_Date()));}
    }
  }


  /**
   * Applies integer formatter to the provided text field
   * @param field
   */
  public void applyIntegerFormatToField(TextField field) {
    final Pattern NumberRegx = Pattern.compile("[-]?[\\d]*");
    field.setTextFormatter(new TextFormatter<>(f -> {
      if (f.getControlNewText().isEmpty()) {
        return f;
      }

      return (NumberRegx.matcher(f.getControlNewText()).matches()) ? f : null;
    }));
  }


  /**
   * Converts the date to String
   * @param date
   * @return String
   */
  private String dateToString(LocalDate date){
    if (date != null) {
      return dateFormatter.format(date);
    } else {
      return "";
    }
  }


  /**
   * Converts the String to LocalDate
   *
   * @param string
   * @return LocalDate
   */
  private LocalDate stringToDate(String string){
    if (string != null && !string.isEmpty()) {
      return LocalDate.parse(string, dateFormatter);
    } else {
      return null;
    }
  }

}
