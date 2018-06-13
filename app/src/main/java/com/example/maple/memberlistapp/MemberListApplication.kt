package com.example.maple.memberlistapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration

open class MemberListApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Log.d("Application", "onCreate")
        Realm.init(this)
        val realmConfiguration: RealmConfiguration = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(realmConfiguration)

        val preferences: SharedPreferences = getSharedPreferences("testPre", Context.MODE_PRIVATE)

        val isStart: Boolean = preferences.getBoolean("isStart", true)
        if (isStart) {
            var edit = preferences.edit()
            edit.putBoolean("isStart", false)
            edit.putString("lastDate", "1990-01-01  00:00:00")
            edit.commit()
        }

    }
}