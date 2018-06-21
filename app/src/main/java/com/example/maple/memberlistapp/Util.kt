package com.example.maple.memberlistapp

import android.content.SharedPreferences

class Util {
    companion object {
        const val PREF_LAST_UPDATE:          String = "prefLastUpdate"
        const val DEFAULT_LAST_UPDATE_TIME:  String = "1990-01-01 00:00:00" //最終更新日
        const val PREF_KEY_LAST_UPDATE_TIME: String = "lastUpdateTimeKey"
        const val KEY_UPDATE_SKILL:          String = "key_update_skill"

        const val PREF_LOGIN_STATUS:         String  = "prefLoginStatus"
        const val DEFAULT_LOGIN_STATUS:      Boolean = false
        const val PREF_KEY_LOGIN_STATUS:     String  = "loginStatusKey"

    }


}