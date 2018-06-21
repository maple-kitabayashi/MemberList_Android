package com.example.maple.memberlistapp.data

import android.app.Activity
import android.content.SharedPreferences
import android.util.Log
import com.example.maple.memberlistapp.IAPI
import com.example.maple.memberlistapp.LoginActivity
import com.example.maple.memberlistapp.UserService
import com.example.maple.memberlistapp.Util
import com.example.maple.memberlistapp.api.MyAccount
import com.example.maple.memberlistapp.api.Skill
import com.example.maple.memberlistapp.api.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * APIへアクセスするクラス
 * 他のオブジェクトはこのDAOクラスを通して、サーバDBへアクセスする
 */
class ApiDAO {

    private lateinit var disposable: Disposable //通信が中断した際に使用する
    private lateinit var retrofit:   Retrofit

    private val gson: Gson = GsonBuilder().setLenient().create()

    companion object {
        val API_DAO: ApiDAO by lazy { ApiDAO() } //この変数を使い、全てのオブジェクトからアクセス可能にする
        val TAG: String by lazy { ApiDAO::class.java.simpleName }
    }

    /**
     * サーバDBに保存しているユーザデータを取得し、
     * ローカルDBへ保存する
     */
    fun fetchUserData(call: IAPI, lastDate: String) {
        Log.d(TAG, "fetchUserData")

        retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://ms-employees.herokuapp.com/")
                .build()

        val service: UserService = retrofit.create(UserService::class.java)

        disposable = service.getUsers(lastDate )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { list -> getUsers(list, call) },                //onNext
                        { throwable ->  throw throwable },               //onError
                        { Log.d(TAG, "getUserData is complete!") } //onComplete
                )
    }

    private fun getUsers(list :List<User>, call: IAPI) {
        LocalDAO.LOCAL_DAO.writeDataList(list) //ローカルDBへ保存
        call.onApiCompleted()
    }

    /**
     * サーバDBに保存しているスキルデータを取得し、
     * ローカルDBへ保存する
     */
    fun fetchSkillData(call: IAPI, lastData: String) {
        Log.d(TAG, "fetchSkillData")

        retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://ms-employees.herokuapp.com/")
                .build()

        val service: UserService = retrofit.create(UserService::class.java)

        //非同期でAPIと通信
        disposable = service.getSkills(lastData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { list -> saveSkillData(list, call) },
                        { throwable -> throw throwable },
                        { Log.d(TAG, "fetchSkillData is complete!") }
                )
    }

    /**
     * スキルデータをローカルDBへ保存
     */
    private fun saveSkillData(list: List<Skill>, call: IAPI) {
        LocalDAO.LOCAL_DAO.saveSkillData(list)
        call.onApiCompleted()
    }

    /**
     * ユーザーから入力されたデータでログインを試す
     */
    fun tryLogin(call: IAPI, email: String, password: String) {
        Log.d(TAG, "tryLogin")

        retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://ms-employees.herokuapp.com/")
                .build()

        val service: UserService = retrofit.create(UserService::class.java)

        disposable = service.tryLogin(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { list -> saveMyAccount(list, call) },
                        { throwable -> /*Log.e(TAG, throwable.message)*/ throw throwable },
                        { Log.d(TAG, "tryLogin is complete!") }
                )
    }

    fun saveMyAccount(list: List<MyAccount>, call: IAPI) {
        Log.d(TAG, "saveMyAccount")

        val account: MyAccount = list.first()
        if (account.id != "-1") {
            LocalDAO.LOCAL_DAO.InitSaveMyAccount(account) //初期保存(IDとNAMEのみ)
            call.onApiCompleted() //成功
        } else {
            call.onApiFailed()    //失敗
        }

    }
}