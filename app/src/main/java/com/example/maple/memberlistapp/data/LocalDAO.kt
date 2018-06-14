package com.example.maple.memberlistapp.data

import com.example.maple.memberlistapp.api.MyAccount
import com.example.maple.memberlistapp.api.User
import io.realm.Realm
import io.realm.RealmResults
import java.util.*

/**
 * メンバーのDBへアクセスするクラス
 * 他のオブジェクトはこのDAOクラスを通して、ローカルDBへアクセスする
 */
class LocalDAO {

    private lateinit var mRealm: Realm

    companion object {
        val LOCAL_DAO: LocalDAO by lazy { LocalDAO() }
    }

    //全データ取得
    fun readData(): RealmResults<User> = mRealm.where(User::class.java).findAll()

    //IDが一致するデータを取得
    fun readData(id: String): RealmResults<User> = mRealm.where(User::class.java).equalTo("id", id).findAll()

    //データ保存
    fun writeData(id: String, name: String, birthDay: String, skill: String, hobby: String) {
        mRealm = Realm.getDefaultInstance()
        mRealm.executeTransaction {
            var user = mRealm.createObject(User::class.java, UUID.randomUUID().toString())
            //user.id       = id
            user.name     = name
            user.birthDay = birthDay
            user.skill    = skill
            user.hobby    = hobby
            //mRealm.copyToRealm(user) //追加
        }
    }

    //複数のデータを保存する
    fun writeDataList(list: List<User>) {
        mRealm = Realm.getDefaultInstance()

        mRealm.executeTransaction {
            for (user in list) {
                val data = mRealm.createObject(User::class.java, user.id)
                data.name     = user.name
                data.birthDay = user.birthDay
                data.skill    = user.skill
                data.hobby    = user.hobby
            }
        }
    }

    //マイアカウントを保存
    fun saveMyAccount(list: List<MyAccount>) {
        mRealm = Realm.getDefaultInstance()

        mRealm.executeTransaction {
            val account = list.first()
            val saveData = mRealm.createObject(MyAccount::class.java)
            saveData.name = account.name
        }
    }

    fun writeDataJava(name: String, birthDay: String, skill: String, hobby: String) {
        mRealm = Realm.getDefaultInstance()
        mRealm.executeTransaction {
            var nameData = mRealm.createObject(MemberDataJava::class.java, UUID.randomUUID().toString())
            nameData.name
        }
    }

    //データ更新
    fun updateData() {

    }

    fun closeRealm() = mRealm.close()
}