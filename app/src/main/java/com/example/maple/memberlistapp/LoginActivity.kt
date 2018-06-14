package com.example.maple.memberlistapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class LoginActivity : AppCompatActivity() {

    companion object {
        val TAG = LoginActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_login)


    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}