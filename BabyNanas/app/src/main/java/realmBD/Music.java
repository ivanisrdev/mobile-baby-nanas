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

  @PrimaryKey
  @Index
  private long id;

  private String title;
  private String type;
  private int count;

  public long getId() { return id; }
  public void setId(long id) { this.id = id; }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public String getType() { return type; }
  public void setType(String type) { this.type = type; }
  public int getCount() { return count; }
  public void setCount(int count) { this.count = count; }
}
