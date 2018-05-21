package com.asoftwaresolution.rememberme.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by AdderlyS on 22/04/2016.
 */
public class SessionManager {

    //method to save status
    public void setPreferences(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences("ASPreferences", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getPreferences(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences("ASPreferences", Context.MODE_PRIVATE);
        String position = prefs.getString(key, "");
        return position;
    }
}
