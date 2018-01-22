package ad.aplication.mji.babynanas;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;


public class MyPreferencesActivity extends PreferenceActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

  }

  public static class MyPreferenceFragment extends PreferenceFragment
  {
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.preferences);

      //aixo cal?
      final ListPreference listPreference = (ListPreference) findPreference("language");
      setListPreferenceData(listPreference);


    listPreference.setOnPreferenceChangeListener(languageChangeListener);

    }


    Preference.OnPreferenceChangeListener languageChangeListener = new Preference.OnPreferenceChangeListener() {

      @Override
      public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.i("Member name: ",newValue.toString());
        switch (newValue.toString()) {
          case "en":
            setLocale("en");
            Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Locale in English!", Toast.LENGTH_LONG).show();
            break;

          case "fr":
            setLocale("fr");
            Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Locale in French!", Toast.LENGTH_LONG).show();
            break;
        }
        setLocale("ca");
        return true;
      }
    };

    //* manually changing current locale/
    public void setLocale(String lang) {
      Locale myLocale = new Locale(lang);
      Resources res = getResources();
      DisplayMetrics dm = res.getDisplayMetrics();
      Configuration conf = res.getConfiguration();
      conf.locale = myLocale;
      res.updateConfiguration(conf, dm);
      Intent i = new Intent(this,MyPreferencesActivity.class);
      startActivity(i);
    }


  }


  public static String[] getAppSupportedLaungages(Context context, String[] langs) {

    Configuration conf = context.getResources().getConfiguration();

    String[] displayNames = new String[langs.length];
    displayNames[0] = "Auto";
    int size = langs.length;
    for (int i=1; i < size; i++) {
      conf.locale = new Locale(langs[i]);
      displayNames[i] = ucFirst(conf.locale.getDisplayName());
    }

    return displayNames;
  }


  public static Map<String, String> getAppSupportedLaungages(Context context, String appDefaultLang) {
    Map<String, String> listAppLocales = new LinkedHashMap<>();

    listAppLocales.put("default", "Auto");

    DisplayMetrics metrics = new DisplayMetrics();
    Resources res = context.getResources();
    Configuration conf = res.getConfiguration();
    String[] listLocates = res.getAssets().getLocales();

    for (String locate : listLocates) {
      Log.i("Member name: ",locate);
    }


    for (String locate : listLocates) {
      conf.locale = new Locale(locate);
      Resources res1 = new Resources(context.getAssets(), metrics, conf);
      String s1 = res1.getString(R.string.configuration);
      String value = String.format(Locale.getDefault(),"%s (%s)",
              ucFirst(conf.locale.getDisplayName()),
              conf.locale.getLanguage()
      );

      conf.locale = new Locale("");
      Resources res2 = new Resources(context.getAssets(), metrics, conf);
      String s2 = res2.getString(R.string.configuration);

      if (!s1.equals(s2)) {
        //Log.d("DIFFERENT LOCALE", i + ": "+ s1+" "+s2 +" "+ loc[i]);
        listAppLocales.put(locate, value);
      } else if (locate.equals(appDefaultLang)) {
        listAppLocales.put(locate, value);
      }
    }
    return listAppLocales;
  }

  public static String ucFirst(String subject) {
    if (!subject.isEmpty()) {
      return Character.toUpperCase(subject.charAt(0)) + subject.substring(1);
    } else {
      return null;
    }
  }
  private static void setListPreferenceData(ListPreference lp) {

    String[] entryValues = MyApplication.getInstance().getApplicationContext().getResources().getStringArray(R.array.pref_language_list_values);
    CharSequence[] entries = getAppSupportedLaungages(MyApplication.getInstance().getApplicationContext(), entryValues);

    lp.setEntries(entries);
    lp.setEntryValues(entryValues);
    lp.setDefaultValue("default");
  }
}