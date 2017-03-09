package ad.aplication.mji.babynanas;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by j2agm on 09/03/2017.
 */

class Preferences {
  private static String APP_ALREADY_LOAD_PREF = "alreadyLoad";
  private static String VERSION_CODE_PREF = "versionCode";

  private static SharedPreferences getPreferences(Context context) {
    return context.getSharedPreferences("myPrefs", 0);
  }

  static boolean getAppAlreadyLoadPref(Context context) {
    return getPreferences(context).getBoolean(APP_ALREADY_LOAD_PREF, false);
  }

  static int getVersionCodePref(Context context) {
    return getPreferences(context).getInt(VERSION_CODE_PREF, 0);
  }

  public static void seAppAlreadyLoadPref(Context context, boolean value) {
    getPreferences(context).edit().putBoolean(APP_ALREADY_LOAD_PREF, value).apply();
  }

  public static void setVersionCodePref(Context context, int value) {
    getPreferences(context).edit().putInt(VERSION_CODE_PREF, value).apply();
  }
}
