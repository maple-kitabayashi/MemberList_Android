package com.example.maple.memberlistapp

import android.content.Intent
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.maple.memberlistapp.data.ApiDAO
import com.example.maple.memberlistapp.data.LocalDAO
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), IAPI, NavigationView.OnNavigationItemSelectedListener {

    private var fragmentManager: FragmentManager? = supportFragmentManager

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)

        //ログインしているか確認
        if(isLogined()) {
            //ログイン画面
        } else {
            //メンバー表示フラグメント
        }

//        val pref: SharedPreferences = getSharedPreferences(Util.PREF_LAST_UPDATE, Context.MODE_PRIVATE)
//        val lastDate: String = pref.getString(Util.PREF_KEY_LAST_UPDATE_TIME, "")
//        //ナビゲーションビューの設定
//        navigastionViewSetting()
//
//        //APIでユーザデータ取得
//        ApiDAO.API_DAO.fetchUserData(this, lastDate)
    }

    private fun isLogined(): Boolean {
        val preferences = getSharedPreferences(Util.PREF_LOGIN_STATUS, Context.MODE_PRIVATE)
        val isLogin     = preferences.getBoolean(Util.PREF_KEY_LOGIN_STATUS, false)
        return isLogin
    }

//    private fun navigastionViewSetting() {
//        val header   = mNavigationView.getHeaderView(0)
//        val nameText = header.findViewById(R.id.nav_header_name) as TextView
//        val menu     = mNavigationView.menu
//
//        mNavigationView.setNavigationItemSelectedListener(this)
//    }

    /**
     * ユーザーの詳細画面を表示
     */
    private fun createMyDetailActivity() {
        val intent = Intent(this, MemberDetailActivity::class.java)
        intent.putExtra(R.string.detail_activity_key_id.toString(), "1")
        intent.putExtra(R.string.detail_activity_key_visibility.toString(), true)
        startActivity(intent)
    }

    /**
     * ナビゲーションビューのメニュー押下時に呼ばれる
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //プロフィールメニュー
            R.id.nc_profile -> {
                createMyDetailActivity()
            }
        }
        return false
    }

    /**
     * API通信の成功時に呼ばれるコールバック
     */
    override fun onApiCompleted() {
//        //最終更新日を更新する
//        updateLastUpdateTime()
//
//        //レイアウトの表示・非表示を行う
//        mMainActLoadBar.visibility = View.GONE    //プログレスバー非表示
//        mFrameLayout.visibility    = View.VISIBLE //フレームレイアウト表示
//        mScrollView.visibility     = View.VISIBLE //スクロールビュー表示
//
//        //初期フラグメント(メンバーリスト表示画面)を作成
//        val fragment    = MemberListFragment()
//        val transaction = fragmentManager!!.beginTransaction()
//
//        transaction.add(R.id.mFrameLayout, fragment)
//        transaction.commit()
    }

    override fun onApiFailed() {

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

//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d(TAG, "onDestroy")
//        LocalDAO.LOCAL_DAO.closeRealm()
//    }
}
