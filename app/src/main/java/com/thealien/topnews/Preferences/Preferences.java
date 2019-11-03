
package com.thealien.topnews.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

public class Preferences {

    public static final String KEY_FIRST_TIME_RUN = "first_time";
    public static final String KEY_SELECTED_COUNTRY = "selected_country";
    private static Preferences preferences;
    private SharedPreferences sharedPref;
    private String prefs = "prefs";

    //by making this private no one can make an object out of this class
    private Preferences(Context context) {
        sharedPref = context.getSharedPreferences(prefs,Context.MODE_PRIVATE);
    }

    public static Preferences getInstance(Context context) {

        if (preferences == null) {
            preferences = new Preferences(context);
        }
        return preferences;
    }


    public void saveKey(String key, Object value) {

        SharedPreferences.Editor prefsEditor = sharedPref.edit();

        if (value instanceof String)
            prefsEditor.putString(key, (String) value);
        else if (value instanceof Integer)
            prefsEditor.putInt(key, (Integer) value);
        else if (value instanceof Float)
            prefsEditor.putFloat(key, (Float) value);
        else if (value instanceof Boolean)
            prefsEditor.putBoolean(key, (Boolean) value);

        prefsEditor.apply();
    }

    public String getKey(String key){

        if(sharedPref != null){
          return sharedPref.getString(key,"null");
        }
        return "null";
    }
}
