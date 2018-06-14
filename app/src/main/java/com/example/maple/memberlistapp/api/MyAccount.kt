package com.example.maple.memberlistapp.api

import io.realm.RealmObject

/**
 * ログイン中のアカウント情報
 */
open class MyAccount : RealmObject() {
    var name:     String = ""
    var birthDay: String = ""
    var skill:    String = ""
    var hobby:    String = ""
    var image:    String = ""
}