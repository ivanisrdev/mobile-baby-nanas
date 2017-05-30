package ad.aplication.mji.babynanas;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
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
      // Add a music type Nana
      Music musicNana1 = new Music(1L,"Angel feliz","Nana",0,"baby5");
      realm.copyToRealmOrUpdate(musicNana1);
      Music musicNana2 = new Music(2L,"Arrorro","Nana",0,"baby6");
      realm.copyToRealmOrUpdate(musicNana2);
      Music musicNana3 = new Music(3L,"Duermete bebe","Nana",0,"baby7");
      realm.copyToRealmOrUpdate(musicNana3);
      Music musicNana4 = new Music(4L,"Estrellita donde estas","Nana",0,"baby8");
      realm.copyToRealmOrUpdate(musicNana4);
      Music musicNana5 = new Music(5L,"Mama naturaleza","Nana",0,"baby3");
      realm.copyToRealmOrUpdate(musicNana5);
      Music musicNana6 = new Music(6L,"Muñequita linda","Nana",0,"baby12");
      realm.copyToRealmOrUpdate(musicNana6);

      // Add a music type Relax
      Music musicRelax1 = new Music(7L,"Agua","Relax",0,"agua");
      realm.copyToRealmOrUpdate(musicRelax1);
      Music musicRelax2 = new Music(8L,"Corazon","Relax",0,"corazon");
      realm.copyToRealmOrUpdate(musicRelax2);
      Music musicRelax3 = new Music(9L,"Fuego","Relax",0,"fuego");
      realm.copyToRealmOrUpdate(musicRelax3);
      Music musicRelax4 = new Music(10L,"Lluvia","Relax",0,"lluvia");
      realm.copyToRealmOrUpdate(musicRelax4);
      Music musicRelax5 = new Music(11L,"Mar","Relax",0,"mar");
      realm.copyToRealmOrUpdate(musicRelax5);
      Music musicRelax6 = new Music(12L,"Pajaros","Relax",0,"pajaros");
      realm.copyToRealmOrUpdate(musicRelax6);
      Music musicRelax7 = new Music(13L,"Viento","Relax",0,"viento");
      realm.copyToRealmOrUpdate(musicRelax7);
      Music musicRelax8 = new Music(14L,"Vientre","Relax",0,"baby4");
      realm.copyToRealmOrUpdate(musicRelax8);

      // Add a music type Classical
      Music musicClassical1 = new Music(15L,"Bach prelude","Classical",0,"baby10");
      realm.copyToRealmOrUpdate(musicClassical1);
      Music musicClassical2 = new Music(16L,"Chopin nocturne","Classical",0,"baby11");
      realm.copyToRealmOrUpdate(musicClassical2);
      Music musicClassical3 = new Music(17L,"Schubert impromptu","Classical",0,"baby13");
      realm.copyToRealmOrUpdate(musicClassical3);
      Music musicClassical4 = new Music(18L,"Schubert ave maria","Classical",0,"baby14");
      realm.copyToRealmOrUpdate(musicClassical4);
      Music musicClassical5 = new Music(19L,"Tzvi erez bach","Classical",0,"baby2");
      realm.copyToRealmOrUpdate(musicClassical5);

    /*realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        RealmResults<Music> result = realm.where(Music.class).equalTo(Music.ID,19L).findAll();
        result.deleteAllFromRealm();
      }
    });*/
      realm.commitTransaction();

    }
    Preferences.seAppAlreadyLoadPref(this, true);
    Preferences.setVersionCodePref(this, version);

  }
}