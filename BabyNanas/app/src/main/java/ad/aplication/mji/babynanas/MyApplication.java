package ad.aplication.mji.babynanas;

import android.app.Application;
import android.util.Log;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

/**
 * Created by j2agm on 21/02/2017.
 */

public class MyApplication extends Application {
  static final String TAG = "MyApplication";

  @Override
  public void onCreate() {
    super.onCreate();
    Realm.init(this);

    Stetho.initialize(
        Stetho.newInitializerBuilder(this)
            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
            .build());
    Log.i(TAG, "onCreate: Realm Object");

    RealmConfiguration config = new RealmConfiguration.Builder()
        .name("myrealm.realm")
        .schemaVersion(42)
        .migration(new MyMigration())
        .build();
// Use the config
    Realm.setDefaultConfiguration(config);

  }

  private class MyMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
      Log.w(TAG, "migrate() called with: " + "realm = [" + realm + "]," +
          "oldVersion = [" + oldVersion + "], newVersion = [" + newVersion + "]");
    }
  }
}