package library.publisher;

public class Publisher {
  private int Publisher_ID;
  private String Name;

  public Publisher(int Publisher_ID, String Name) {
    this.Publisher_ID = Publisher_ID;
    this.Name = Name;
  }

  public int getPublisher_ID() {
    return this.Publisher_ID;
  }

  public void setPublisher_ID(int Publisher_ID) {
    this.Publisher_ID = Publisher_ID;
  }

  public String getName() {
    return this.Name;
  }

  public void setName(String Name) {
    this.Name = Name;
  }

}
