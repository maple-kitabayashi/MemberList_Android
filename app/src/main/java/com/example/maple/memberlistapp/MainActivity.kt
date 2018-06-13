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

class MainActivity : AppCompatActivity(), IAPI {

    private var fragmentManager: FragmentManager? = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val pref: SharedPreferences = getSharedPreferences("testPre", Context.MODE_PRIVATE)
        val lastDate: String = pref.getString("lastDate", "")
        //APIでユーザデータ取得
        ApiDAO.API_DAO.getUserData(this, lastDate)
    }

    /**
     * API通信の成功時に呼ばれるコールバック
     */
    override fun onApiCompleted() {
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

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
        LocalDAO.LOCAL_DAO.closeRealm()
    }
}
