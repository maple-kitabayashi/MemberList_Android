package com.example.maple.memberlistapp.api

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class Skill: RealmObject() {
    @PrimaryKey var id:         String = "" //ID
    @Required   var skill_name: String = "" //スキル名
    @Required   var created_at: String = "" //作成日時
    @Required   var updated_at: String = "" //更新日時
}