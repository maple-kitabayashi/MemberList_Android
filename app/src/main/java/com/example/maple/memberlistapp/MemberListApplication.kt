package com.example.maple.memberlistapp

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

open class MemberListApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val realmConfiguration: RealmConfiguration = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }
}