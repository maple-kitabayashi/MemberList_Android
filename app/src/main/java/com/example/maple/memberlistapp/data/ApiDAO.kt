package com.example.maple.memberlistapp.data

import android.util.Log
import com.example.maple.memberlistapp.IAPI
import com.example.maple.memberlistapp.UserService
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
    fun getUserData(call: IAPI) {
        retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://ms-employees.herokuapp.com/")
                .build()

        val service: UserService = retrofit.create(UserService::class.java)

        disposable = service.getUsers("1991-12-16 00:00:00")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { list -> getUsers(list, call) },                       //onNext
                        { Log.d(TAG, it.message) },                      //onError
                        { Log.d(TAG, "getUserData is complete!") } //onComplete
                )
    }

    private fun getUsers(list :List<User>, call: IAPI) {

        LocalDAO.LOCAL_DAO.writeDataList(list) //ローカルDBへ保存
        call.onApiCompleted()
    }
}