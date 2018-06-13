package com.example.maple.memberlistapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val intent   = this.intent
        val name     = intent.getStringExtra("testName")
        val birthDay = intent.getStringExtra("testBirthDay")
        val skill    = intent.getStringExtra("testSkill")
        val hobby    = intent.getStringExtra("testHobby")

        //EditTextのtextはEditable型なので、factoryを作成して設定する
        val factory: Editable.Factory = Editable.Factory.getInstance()
        //現在設定中の内容を表示する
        mEditSkillText.text = factory.newEditable(skill)
        mEditHobbyText.text = factory.newEditable(hobby)

        mEditSaveBtn.setOnClickListener {
            //TODO UpdateAPI ローカルDBへ保存
        }
    }
}