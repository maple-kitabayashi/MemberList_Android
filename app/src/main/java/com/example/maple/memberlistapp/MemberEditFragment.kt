package com.example.maple.memberlistapp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_edit.*

class MemberEditFragment : Fragment() {

    companion object {
        val TAG: String = MemberEditFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_edit, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")

        val intent:   Intent = activity!!.intent
        val name:     String = intent.getStringExtra("testName")
        val birthDay: String = intent.getStringExtra("testBirthDay")
        val skill:    String = intent.getStringExtra("testSkill")
        val hobby:    String = intent.getStringExtra("testHobby")

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