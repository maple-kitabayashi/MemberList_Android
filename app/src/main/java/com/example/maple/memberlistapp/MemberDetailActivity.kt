package com.example.maple.memberlistapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.maple.memberlistapp.api.User
import com.example.maple.memberlistapp.data.LocalDAO
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_member_detail.*

/**
 * 社員データ詳細画面
 */
class MemberDetailActivity : AppCompatActivity() {

    private lateinit var userData: RealmResults<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_member_detail)

        val intent: Intent = getIntent()
        val strId:  String = intent.getStringExtra(R.string.detail_activity_key.toString())

        //TODO 非同期処理に変更する
        userData = LocalDAO.LOCAL_DAO.readData(strId)
        setLayout()
    }

    private fun setLayout() {
        val member = userData.first()
        mDetailName.setText(member!!.name)
        mDetailBirthDayText.setText(member!!.birthDay)
        mDetailSkillText.setText(member!!.skill)
        mDetailHobbyText.setText(member!!.hobby)
    }
}
