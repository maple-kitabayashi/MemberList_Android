package com.example.maple.memberlistapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class LoginActivity : AppCompatActivity(), LoginFragment.CallBack {

    companion object {
        val TAG = LoginActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.d(TAG, "onCreate")

        //ログインのフラグメントを生成
        val transaction = supportFragmentManager.beginTransaction()
        val fragment    = LoginFragment()
        transaction.replace(R.id.login_frame_layout, fragment)
        transaction.commit()
    }

    /**
     * フラグメントからのコールバック
     * メンバーリスト画面を生成する
     */
    override fun callback() {
        val intent = Intent(this, MemberListActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}