package network;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zaid on 4/30/2015.
 */
public class SharedPrefUtility {

    private SharedPreferences sharedPreferences;
    private static SharedPrefUtility utility;

    public static String PREFS_NAME = "app_prefs";

    private SharedPrefUtility(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = (context).getSharedPreferences(
                    PREFS_NAME, Context.MODE_PRIVATE);
        }
    }

    public static synchronized SharedPrefUtility getInstance(Context c) {
        if (utility == null) {
            utility = new SharedPrefUtility(c);
        }
        return utility;
    }

    public SharedPreferences getPreferences() {
        return sharedPreferences;
    }

    public void savePrefrences(String key, String value) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString(key, value).commit();
        }
    }

    public void savePrefrences(String key, int value) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putInt(key, value).commit();
        }
    }

    public void savePrefrences(String key, boolean value) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putBoolean(key, value).commit();
        }
    }

    public void savePrefrences(String key, long value) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().putLong(key, value).commit();
        }
    }

    public void removePrefrences(String key) {
        if (sharedPreferences != null) {
            sharedPreferences.edit().remove(key).commit();
        }
    }

    public String getStringValue(String key) {
        return sharedPreferences.getString(key, null);
    }

    public boolean getBooleanValue(String key) {
        return sharedPreferences.getBoolean(key, false);
    }
    public boolean getBooleanValueDefaultTrue(String key) {
        return sharedPreferences.getBoolean(key, true);
    }

    public int getIntValue(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public long getLongValue(String key) {
        return sharedPreferences.getLong(key, -1);
    }


}
