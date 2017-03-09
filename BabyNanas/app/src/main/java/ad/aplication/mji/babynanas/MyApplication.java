package ad.aplication.mji.babynanas;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import realmBD.Music;

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
        .name("music.realm")
        .deleteRealmIfMigrationNeeded()
        .schemaVersion(1)
        .build();
// Use the config
    Realm.setDefaultConfiguration(config);

    Realm realm = Realm.getDefaultInstance();

    PackageInfo pInfo = null;
    try {
      pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
    } catch (NameNotFoundException e) {
      e.printStackTrace();
    }
    int version = pInfo.versionCode;

    boolean alreadyLoad = Preferences.getAppAlreadyLoadPref(this);
    int versionCode = Preferences.getVersionCodePref(this);

    if (!alreadyLoad || versionCode < version) {
      //create elemnts en BBDD
      //delete all
      /*realm.close();
      Realm.deleteRealm(realm.getConfiguration());*/

      realm.beginTransaction();
      // Add a music
      Music music = new Music();
      music.setId(1L);
      music.setTitle("Angel_Feliz");
      music.setType("Nana");
      music.setCount(0);
      music.setImageName("baby5");
      realm.copyToRealmOrUpdate(music);

      Music music2 = new Music();
      music2.setId(2L);
      music2.setTitle("Nana_Arrorro");
      music2.setType("Nana");
      music2.setCount(0);
      music2.setImageName("baby6");
      realm.copyToRealmOrUpdate(music2);

      Music music3 = new Music();
      music3.setId(3L);
      music3.setTitle("Nana_Duermete_nino");
      music3.setType("Nana");
      music3.setCount(0);
      music3.setImageName("baby7");
      realm.copyToRealmOrUpdate(music3);

      Music music4 = new Music();
      music4.setId(4L);
      music4.setTitle("Nana_Estrellita_donde_estas");
      music4.setType("Nana");
      music4.setCount(0);
      music4.setImageName("baby8");
      realm.copyToRealmOrUpdate(music4);

      Music music5 = new Music();
      music5.setId(5L);
      music5.setTitle("Nana_Mama_naturaleza");
      music5.setType("Nana");
      music5.setCount(0);
      music5.setImageName("baby9");
      realm.copyToRealmOrUpdate(music5);

      Music music6 = new Music();
      music6.setId(6L);
      music6.setTitle("Nana_Muñequita_linda");
      music6.setType("Nana");
      music6.setCount(0);
      music6.setImageName("baby10");
      realm.copyToRealmOrUpdate(music6);

      Music music7 = new Music();
      music7.setId(7L);
      music7.setTitle("Sonido_Pajaros");
      music7.setType("Relax");
      music7.setCount(0);
      music7.setImageName("baby11");
      realm.copyToRealmOrUpdate(music7);

      Music music8 = new Music();
      music8.setId(8L);
      music8.setTitle("Sonido_Viento");
      music8.setType("Relax");
      music8.setCount(0);
      music8.setImageName("baby12");
      realm.copyToRealmOrUpdate(music8);

      Music music9 = new Music();
      music9.setId(9L);
      music9.setTitle("Sonido_Vientre");
      music9.setType("Relax");
      music9.setCount(0);
      music9.setImageName("baby13");
      realm.copyToRealmOrUpdate(music9);

   /* realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        RealmResults<Music> result = realm.where(Music.class).equalTo(Music.ID,15L).findAll();
        result.deleteAllFromRealm();
      }
    });*/
      realm.commitTransaction();

    }
    Preferences.seAppAlreadyLoadPref(this, true);
    Preferences.setVersionCodePref(this, version);

  }
}