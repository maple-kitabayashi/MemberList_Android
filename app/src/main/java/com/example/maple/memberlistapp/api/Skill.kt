package com.example.maple.memberlistapp.api

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class Skill: RealmObject() {
    @PrimaryKey val id:         String = "" //ID
    @Required   val skill_name: String = "" //スキル名
    @Required   val created_at: String = "" //作成日時
    @Required   val updated_at: String = "" //更新日時
}