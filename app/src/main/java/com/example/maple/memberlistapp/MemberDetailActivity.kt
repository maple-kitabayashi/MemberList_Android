package com.example.maple.memberlistapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
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

        val intent:       Intent  = this.intent
        val strId:        String  = intent.getStringExtra(R.string.detail_activity_key_id.toString())
        val isVisibility: Boolean = intent.getBooleanExtra(R.string.detail_activity_key_visibility.toString(), false)

        if (isVisibility) {
            mDetailEditBtn.visibility = View.VISIBLE
        }

        //TODO 非同期処理に変更する
        userData = LocalDAO.LOCAL_DAO.readData(strId)
        setLayout()

        mDetailEditBtn.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setLayout() {
        val member = userData.first()
        
        mDetailName.text         = member!!.name
        mDetailBirthDayText.text = member!!.birthDay
        mDetailSkillText.text    = member!!.skill
        mDetailHobbyText.text    = member!!.hobby
    }
}
