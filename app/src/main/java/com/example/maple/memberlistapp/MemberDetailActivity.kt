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

        //Intentから値を取得
        val intent:       Intent  = this.intent
        val strId:        String  = intent.getStringExtra(R.string.detail_activity_key_id.toString())
        val isVisibility: Boolean = intent.getBooleanExtra(R.string.detail_activity_key_visibility.toString(), false)

        //自分のアカウントの詳細画面なら、編集ボタンを表示する
        if (isVisibility) {
            mDetailEditBtn.visibility = View.VISIBLE //表示
        }

        //TODO 非同期処理に変更する
        userData = LocalDAO.LOCAL_DAO.readData(strId)
        setLayout()

        mDetailEditBtn.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("testName", mDetailName.text)
            intent.putExtra("testBirthDay", mDetailBirthDayText.text)
            intent.putExtra("testSkill", mDetailSkillText.text)
            intent.putExtra("testHobby", mDetailHobbyText.text)
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
