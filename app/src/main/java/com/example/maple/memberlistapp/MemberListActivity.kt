package com.example.maple.memberlistapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class MemberListActivity : AppCompatActivity() {

    companion object {
        val TAG = MemberListActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_list)
        Log.d(TAG, "onCreate")
    }
}