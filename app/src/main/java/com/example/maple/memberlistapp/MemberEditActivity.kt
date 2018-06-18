package com.example.maple.memberlistapp

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.text.Editable

class MemberEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        //フラグメント作成
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment:    MemberEditFragment  = MemberEditFragment()
        transaction.replace(R.id.edit_frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}