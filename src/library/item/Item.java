package library.item;

public class Item {
  private int Item_ID;
  private String Item_Type;
  private final int Item_Type_MaxLenght = 100;
  private int Number_Of_Copies;
  private boolean Is_Rentable;
  private String ISBN;
  private final int ISBN_MinLenght = 10;
  private final int ISBN_MaxLenght = 13;
  private int Number_Of_Pages;
  private String Link;
  private final int Link_MaxLenght = 1000;
  private int Item_Description_ID;
  private String Title;
  private final int Title_MaxLenght = 255;
  private String Authors;
  private String DeweyNumberField;
  private String Publisher;
  private String Publication_Date;
  private int Publisher_ID;

  public Item(String ISBN) throws Exception {
    setISBN(ISBN);
  }

  public Item() {
  }

  public int getItem_ID() {
    return this.Item_ID;
  }

  public void setItem_ID(int Item_ID) {
    this.Item_ID = Item_ID;
  }

  public String getItem_Type() {
    return this.Item_Type;
  }

  public void setItem_Type(String Item_Type) throws Exception {
    if (Item_Type.length() > Item_Type_MaxLenght) {
      throw new Exception("Item type exceeded max limit of " + Item_Type_MaxLenght);
    }
    this.Item_Type = Item_Type;
  }

  public int getNumber_Of_Copies() {
    return this.Number_Of_Copies;
  }

  public void setNumber_Of_Copies(int Number_Of_Copies) {
    this.Number_Of_Copies = Number_Of_Copies;
  }

  public boolean getIs_Rentable() {
    return this.Is_Rentable;
  }

  public void setIs_Rentable(boolean Is_Rentable) {
    this.Is_Rentable = Is_Rentable;
  }

  public String getISBN() {
    return this.ISBN;
  }

  public void setISBN(String isbn) throws Exception {
    if (isbn.length() != ISBN_MinLenght && isbn.length() != ISBN_MaxLenght) {
      throw new Exception("Incorrect ISBN");
    }
    this.ISBN = isbn;
  }

  public int getNumber_Of_Pages() {
    return this.Number_Of_Pages;
  }

  public void setNumber_Of_Pages(int Number_Of_Pages) {
    this.Number_Of_Pages = Number_Of_Pages;
  }

  public String getLink() {
    return this.Link;
  }

  public void setLink(String Link) throws Exception {
    if (Link.length() > Link_MaxLenght) {
      throw new Exception("Link exceeded max limit of " + Link_MaxLenght);
    }
    this.Link = Link;
  }

  public int getItem_Description_ID() {
    return this.Item_Description_ID;
  }

  public void setItem_Description_ID(int Item_Description_ID) {
    this.Item_Description_ID = Item_Description_ID;
  }

  public String getTitle() {
    return this.Title;
  }

  public void setTitle(String Title) throws Exception {
    if (Title.length() > Title_MaxLenght) {
      throw new Exception("Title exceeded max limit of " + Title_MaxLenght);
    }
    this.Title = Title;
  }

  public String getAuthors() {
    return this.Authors;
  }

  public void setAuthors(String Authors) {
    this.Authors = Authors;
  }

  public String getDeweyNumberField() {
    return this.DeweyNumberField;
  }

  public void setDeweyNumberField(String DeweyNumberField) {
    this.DeweyNumberField = DeweyNumberField;
  }

  public String getPublisher() {
    return this.Publisher;
  }

  public void setPublisher(String Publisher) {
    this.Publisher = Publisher;
  }

  public String getPublication_Date() {
    return this.Publication_Date;
  }

  public void setPublication_Date(String Publication_Date) {
    this.Publication_Date = Publication_Date;
  }

  public int getPublisher_ID() {
    return this.Publisher_ID;
  }

  public void setPublisher_ID(int Publisher_ID) {
    this.Publisher_ID = Publisher_ID;
  }
}
