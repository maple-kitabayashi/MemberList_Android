package com.example.maple.memberlistapp.api

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class User : RealmObject() {

    @PrimaryKey var id:         String = "" //ID
    @Required   var name:       String = "" //名前
    @Required   var birthDay:   String = "" //生年月日
    @Required   var skill:      String = "" //スキル
    @Required   var hobby:      String = "" //趣味
    @Required   var image:      String = "" //画像
    @Required   var created_at: String = "" //作成日時
    @Required   var updated_at: String = "" //更新日時
}