package com.example.maple.memberlistapp

import android.content.SharedPreferences

class Util {
    companion object {
        val PREF_LAST_UPDATE:          String = "prefLastUpdate"
        val DEFAULT_LAST_UPDATE_TIME:  String = "1990-01-01 00:00:00" //最終更新日
        val PREF_KEY_LAST_UPDATE_TIME: String = "lastUpdateTimeKey"

        val PREF_LOGIN_STATUS:         String  = "prefLoginStatus"
        val DEFAULT_LOGIN_STATUS:      Boolean = false
        val PREF_KEY_LOGIN_STATUS:     String  = "loginStatusKey"
    }


}