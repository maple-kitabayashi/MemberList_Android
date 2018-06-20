package com.example.maple.memberlistapp.data

import com.example.maple.memberlistapp.api.MyAccount
import com.example.maple.memberlistapp.api.User
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
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
    fun readData(): RealmResults<User> {
        mRealm = Realm.getDefaultInstance()
        return mRealm.where(User::class.java).findAll()
    }

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
            for ((index, user) in list.withIndex()) {
                val data = mRealm.createObject(User::class.java, user.id)
                data.name     = user.name
                data.birthDay = user.birthDay
                data.skill    = user.skill
                data.hobby    = user.hobby
                //TODO 実データで実施まで50個のデータで行う
//                if (index == 50)
//                    break
            }
        }
    }

    //マイアカウントを保存
    fun saveMyAccount(newAccount: MyAccount?) {
        val account = getMyAccount()

        mRealm = Realm.getDefaultInstance()

        mRealm.executeTransaction {
            account!!.name     = newAccount!!.name
            account.birthDay = newAccount.birthDay
            account.skill    = newAccount.skill
            account.hobby    = newAccount.hobby

            //画像アイコンは設定慣れていない場合があるので注意
            //TODO サーバDBにデフォルトアイコンを用意すると楽
            if (newAccount.image != null) {
                account.image = newAccount.image
            }

        }
    }

    //マイアカウント初期保存
    fun InitSaveMyAccount(account: MyAccount?) {
        mRealm = Realm.getDefaultInstance()

        mRealm.executeTransaction {
            val saveDate  = mRealm.createObject(MyAccount::class.java, account!!.id)
            saveDate.name = account.name
        }
    }

    /**
     * マイアカウントデータを取得
     */
//    fun getMyAccount(): MyAccount?/*RealmResults<MyAccount>?*/ {
//        var account: RealmResults<MyAccount>? = null
//        mRealm = Realm.getDefaultInstance()
//
//        mRealm.executeTransaction {
//            account = mRealm.where(MyAccount::class.java).findAll()
//        }
//        return account!!.first()
//    }

    fun getMyAccount(): MyAccount? = mRealm.where(MyAccount::class.java).findFirst()
//    fun isLogined(): Boolean {
//        mRealm = Realm.getDefaultInstance()
//        return mRealm.where(MyAccount::class.java).findAll().size != 0
//    }

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