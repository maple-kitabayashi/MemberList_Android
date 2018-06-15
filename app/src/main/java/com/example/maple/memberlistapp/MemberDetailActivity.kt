package com.example.maple.memberlistapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.maple.memberlistapp.api.User
import io.realm.RealmResults

/**
 * 社員データ詳細画面
 */
class MemberDetailActivity : AppCompatActivity() {

    companion object {
        val TAG = MemberDetailActivity::class.java.simpleName
    }

    private lateinit var userData: RealmResults<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_detail)
        Log.d(TAG, "onCreate")

        //フラグメント作成
        val transaction = supportFragmentManager.beginTransaction()
        val fragment    = MemberDetailFragment()
        transaction.replace(R.id.member_detail_frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        Log.d(TAG, "onBackPressed")
        finish() //アクティビティ終了
    }
}
