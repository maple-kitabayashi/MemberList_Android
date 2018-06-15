package com.example.maple.memberlistapp

import android.content.Intent
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)

        //ログインしているかの有無で表示する画面を変える
        val intent: Intent
        if(isLogined()) {
            //メンバー表示画面
            intent = Intent(this, MemberListActivity::class.java)
        } else {
            //ログイン画面
            intent = Intent(this, LoginActivity::class.java)
        }
        startActivity(intent)
        finish()
    }

    private fun isLogined(): Boolean {
        val preferences = getSharedPreferences(Util.PREF_LOGIN_STATUS, Context.MODE_PRIVATE)
        val isLogin     = preferences.getBoolean(Util.PREF_KEY_LOGIN_STATUS, false)
        return isLogin
    }

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
        Log.d(TAG, "onDestroy")
    }
}
