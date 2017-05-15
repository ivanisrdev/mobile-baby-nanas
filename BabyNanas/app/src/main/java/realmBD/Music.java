package realmBD;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by j2agm on 22/02/2017.
 */

public class Music extends RealmObject {
  public static final String ID = "id";
  public static final String TITLE = "title";
  public static final String TYPE = "type";
  public static final String COUNT = "count";
  public static final String IMAGENAME = "image";

  @PrimaryKey
  @Index
  private long id;

  private String title;
  private String type;
  private int count;
  private String image;

  public Music(long id, String title, String type, int count, String image) {
    setId(id);
    setTitle(title);
    setType(type);
    setCount(count);
    setImageName(image);
  }

  public Music(){

  }

  public long getId() { return id; }
  public void setId(long id) { this.id = id; }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public String getType() { return type; }
  public void setType(String type) { this.type = type; }
  public int getCount() { return count; }
  public void setCount(int count) { this.count = count; }
  public void setImageName (String image) { this.image = image; }
  public String getImageName() { return image; }
}
