package library.item;

public class Item {
  private int Item_ID;
  private String Item_Type;
  private int Number_Of_Copies;
  private boolean Is_Rentable;
  private String ISBN;
  private int Number_Of_Pages;
  private String Link;
  private int Item_Description_ID;
  private String Title;
  private String Authors;
  private String DeweyNumberField;
  private String Publisher;
  private String Publication_Date;
  private int Publisher_ID;

  /**
   * Maximum number of copies allowed
   */
  private final int Number_Of_Copies_MaxLenght = 10000;
  /**
   * Minimum length of ISBN
   */
  private final int ISBN_MinLenght = 10;
  /**
   * Maximum length of ISBN
   */
  private final int ISBN_MaxLenght = 13;
  /**
   * Maximum length of item link
   */
  private final int Link_MaxLenght = 1000;
  /**
   * Maximum length of item title
   */
  private final int Title_MaxLenght = 255;
  /**
   * Maximum length of item type
   */
  private final int Item_Type_MaxLenght = 100;

  public Item(String ISBN) throws Exception {
    setISBN(ISBN);
  }

  public Item() {
  }


  /**
   * @return int
   */
  public int getItem_ID() {
    return this.Item_ID;
  }


  /**
   * @param Item_ID
   */
  public void setItem_ID(int Item_ID) {
    this.Item_ID = Item_ID;
  }


  /**
   * @return String
   */
  public String getItem_Type() {
    return this.Item_Type;
  }


  /**
   * @param Item_Type
   * @throws Exception
   */
  public void setItem_Type(String Item_Type) throws Exception {
    if (Item_Type.length() > Item_Type_MaxLenght) {
      throw new Exception("Item type exceeded max limit of " + Item_Type_MaxLenght);
    }
    this.Item_Type = Item_Type;
  }


  /**
   * @return int
   */
  public int getNumber_Of_Copies() {
    return this.Number_Of_Copies;
  }


  /**
   * @param Number_Of_Copies
   * @throws Exception
   */
  public void setNumber_Of_Copies(int Number_Of_Copies) throws Exception {
    if (Number_Of_Copies > Number_Of_Copies_MaxLenght) {
      throw new Exception("Number Of Copies exceeded max limit of " + Number_Of_Copies_MaxLenght);
    }
    this.Number_Of_Copies = Number_Of_Copies;
  }


  /**
   * @return boolean
   */
  public boolean getIs_Rentable() {
    return this.Is_Rentable;
  }


  /**
   * @param Is_Rentable
   */
  public void setIs_Rentable(boolean Is_Rentable) {
    this.Is_Rentable = Is_Rentable;
  }


  /**
   * @return String
   */
  public String getISBN() {
    return this.ISBN;
  }


  /**
   * @param isbn
   * @throws Exception
   */
  public void setISBN(String isbn) throws Exception {
    if (isbn.length() != ISBN_MinLenght && isbn.length() != ISBN_MaxLenght) {
      throw new Exception("Incorrect ISBN");
    }
    this.ISBN = isbn;
  }


  /**
   * @return int
   */
  public int getNumber_Of_Pages() {
    return this.Number_Of_Pages;
  }


  /**
   * @param Number_Of_Pages
   */
  public void setNumber_Of_Pages(int Number_Of_Pages) {
    this.Number_Of_Pages = Number_Of_Pages;
  }


  /**
   * @return String
   */
  public String getLink() {
    return this.Link;
  }


  /**
   * @param Link
   * @throws Exception
   */
  public void setLink(String Link) throws Exception {
    if (Link.length() > Link_MaxLenght) {
      throw new Exception("Link exceeded max limit of " + Link_MaxLenght);
    }
    this.Link = Link;
  }


  /**
   * @return int
   */
  public int getItem_Description_ID() {
    return this.Item_Description_ID;
  }


  /**
   * @param Item_Description_ID
   */
  public void setItem_Description_ID(int Item_Description_ID) {
    this.Item_Description_ID = Item_Description_ID;
  }


  /**
   * @return String
   */
  public String getTitle() {
    return this.Title;
  }


  /**
   * @param Title
   * @throws Exception
   */
  public void setTitle(String Title) throws Exception {
    if (Title.length() > Title_MaxLenght) {
      throw new Exception("Title exceeded max limit of " + Title_MaxLenght);
    }
    this.Title = Title;
  }


  /**
   * @return String
   */
  public String getAuthors() {
    return this.Authors;
  }


  /**
   * @param Authors
   */
  public void setAuthors(String Authors) {
    this.Authors = Authors;
  }


  /**
   * @return String
   */
  public String getDeweyNumberField() {
    return this.DeweyNumberField;
  }


  /**
   * @param DeweyNumberField
   */
  public void setDeweyNumberField(String DeweyNumberField) {
    this.DeweyNumberField = DeweyNumberField;
  }


  /**
   * @return String
   */
  public String getPublisher() {
    return this.Publisher;
  }


  /**
   * @param Publisher
   */
  public void setPublisher(String Publisher) {
    this.Publisher = Publisher;
  }


  /**
   * @return String
   */
  public String getPublication_Date() {
    return this.Publication_Date;
  }


  /**
   * @param Publication_Date
   */
  public void setPublication_Date(String Publication_Date) {
    this.Publication_Date = Publication_Date;
  }


  /**
   * @return int
   */
  public int getPublisher_ID() {
    return this.Publisher_ID;
  }


  /**
   * @param Publisher_ID
   */
  public void setPublisher_ID(int Publisher_ID) {
    this.Publisher_ID = Publisher_ID;
  }
}
