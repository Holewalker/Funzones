package com.svalero.funzones.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionUtil {
    private SharedPreferences prefs;

    public SessionUtil(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUserId(long userId) {
        prefs.edit().putLong("userId", userId).commit();
    }

    public long getUserId() {
        return prefs.getLong("userId", 0L);
    }

    public void setUserName(String userName) {
        prefs.edit().putString("userName", userName).commit();
    }

    public String getUserName() {
        return prefs.getString("userName", "");
    }
}
