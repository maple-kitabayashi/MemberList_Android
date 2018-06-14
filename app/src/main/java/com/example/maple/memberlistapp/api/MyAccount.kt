package com.example.maple.memberlistapp.api

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

/**
 * ログイン中のアカウント情報
 */
open class MyAccount : RealmObject() {
    @PrimaryKey var id:       String = ""
    @Required   var name:     String = ""
//    @Required   var birthDay: String = ""
//    @Required   var skill:    String = ""
//    @Required   var hobby:    String = ""
//    @Required   var image:    String = ""
}