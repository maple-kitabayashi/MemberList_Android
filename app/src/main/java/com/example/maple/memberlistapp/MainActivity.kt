package com.example.maple.memberlistapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.maple.memberlistapp.data.ApiDAO
import com.example.maple.memberlistapp.data.LocalDAO
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), IAPI {

    private var fragmentManager: FragmentManager? = supportFragmentManager

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref: SharedPreferences = getSharedPreferences(Util.PREF_LAST_UPDATE, Context.MODE_PRIVATE)
        val lastDate: String = pref.getString(Util.PREF_KEY_LAST_UPDATE_TIME, "")
        //APIでユーザデータ取得
        ApiDAO.API_DAO.getUserData(this, lastDate)
    }

    /**
     * API通信の成功時に呼ばれるコールバック
     */
    override fun onApiCompleted() {
        //最終更新日を更新する
        updateLastUpdateTime()

        //レイアウトの表示・非表示を行う
        mMainActLoadBar.visibility = View.GONE    //プログレスバー非表示
        mFrameLayout.visibility    = View.VISIBLE //フレームレイアウト表示
        mScrollView.visibility     = View.VISIBLE //スクロールビュー表示

        //初期フラグメント(メンバーリスト表示画面)を作成
        val fragment = MemberListFragment()
        val transaction = fragmentManager!!.beginTransaction()
        transaction.add(R.id.mFrameLayout, fragment)
        transaction.commit()
    }

    /**
     * 最終更新日を更新
     */
    private fun updateLastUpdateTime() {
        //現在の日時を取得
        val date: Date = Date()
        val nowTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
        Log.d("MainTAG", TAG)
        Log.d(TAG, nowTime)
        //セット
        val pref: SharedPreferences = getSharedPreferences(Util.PREF_LAST_UPDATE, Context.MODE_PRIVATE)
        var editor = pref.edit()
        editor.putString(Util.PREF_KEY_LAST_UPDATE_TIME, nowTime)
        editor.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
        LocalDAO.LOCAL_DAO.closeRealm()
    }
}
