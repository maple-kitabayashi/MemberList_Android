package com.example.maple.memberlistapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration

open class MemberListApplication : Application() {

    lateinit var preferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        Log.d("Application", "onCreate")
        Realm.init(this)
        val realmConfiguration: RealmConfiguration = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(realmConfiguration)

        //アプリ初回起動処理
        preferences                        = getSharedPreferences(Util.PREF_LAST_UPDATE, Context.MODE_PRIVATE)
        val isFirstTime: Boolean           = preferences.getBoolean("isFirstTime", true)
        if (isFirstTime) { //初回起動か確認
            settingLastUpdateTime()
            settingLoginStatus()
        }

    }

    private fun settingLastUpdateTime() {
        var edit = preferences.edit()
        edit.putBoolean("isFirstTime", false)
        edit.putString(Util.PREF_KEY_LAST_UPDATE_TIME, Util.DEFAULT_LAST_UPDATE_TIME) //最終更新日の初期値を設定
        edit.commit()
    }

    private fun settingLoginStatus() {
        preferences = getSharedPreferences(Util.PREF_LOGIN_STATUS, Context.MODE_PRIVATE)
        var edit = preferences.edit()
        edit.putBoolean(Util.PREF_KEY_LOGIN_STATUS, Util.DEFAULT_LOGIN_STATUS)
        edit.commit()
    }
}