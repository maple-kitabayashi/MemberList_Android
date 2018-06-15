package com.example.maple.memberlistapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_member_list.*
import java.text.SimpleDateFormat
import java.util.*

class MemberListActivity : AppCompatActivity(), MemberListFragment.CallBack {

    companion object {
        val TAG = MemberListActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_list)
        Log.d(TAG, "onCreate")

        val transaction = supportFragmentManager.beginTransaction()
        val fragment    = MemberListFragment()
        transaction.replace(R.id.mMemListFrame, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    /**
     * フラグメントからのコールバック
     * メンバーリストを表示する
     */
    override fun callback() {
        Log.d(TAG, "callback")
        updateLastUpdateTime()                  //最終更新時間を更新
        mMemListBar.visibility = View.GONE      //プログレスバー非表示
        mMemListFrame.visibility = View.VISIBLE //フレームレイアウト(メンバーリスト)表示
    }

    /**
     * 端末で保存している最終更新時間を更新
     */
    fun updateLastUpdateTime() {
        Log.d(TAG, "updateLastUpdateTime")

        //現在の日時を取得
        val date: Date = Date()
        val nowTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)

        Log.d(TAG, "nowTime = " + nowTime)

        //プリファレンスに保存
        val pref: SharedPreferences = getSharedPreferences(Util.PREF_LAST_UPDATE, Context.MODE_PRIVATE)
        var editor = pref.edit()
        editor.putString(Util.PREF_KEY_LAST_UPDATE_TIME, nowTime)
        editor.commit()
    }

}