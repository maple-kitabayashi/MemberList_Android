package com.example.maple.memberlistapp

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.maple.memberlistapp.data.ApiDAO
import com.example.maple.memberlistapp.data.LocalDAO

class MainActivity : AppCompatActivity(), IAPI {

    private var fragmentManager: FragmentManager? = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ApiDAO.API_DAO.getUserData(this)
        //createData() //仮データ作成

        //初期フラグメント(メンバーリスト表示画面)を作成
//        val fragment = MemberListFragment()
//        val transaction = fragmentManager!!.beginTransaction()
//        transaction.add(R.id.frameLayout, fragment)
//        transaction.commit()
    }

    //仮データをDBへ挿入
//    private fun createData() {
//        LocalDAO.LOCAL_DAO.writeData("0", "北林 純樹", "1996/07/23", "C++", "映画")
//        LocalDAO.LOCAL_DAO.writeData("1", "キタバヤシ ジュンキ", "1990/00/00", "Unity", "ゲーム")
//
//        //LocalDAO.LOCAL_DAO.writeData("0","北林 純樹", "1996/07/23", "C++", "映画")
//    }

    /**
     * API通信の成功時に呼ばれるコールバック
     */
    override fun onApiCompleted() {
        //初期フラグメント(メンバーリスト表示画面)を作成
        val fragment = MemberListFragment()
        val transaction = fragmentManager!!.beginTransaction()
        transaction.add(R.id.frameLayout, fragment)
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
        LocalDAO.LOCAL_DAO.closeRealm()
    }
}
