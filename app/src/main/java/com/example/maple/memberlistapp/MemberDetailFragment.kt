package com.example.maple.memberlistapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maple.memberlistapp.api.User
import com.example.maple.memberlistapp.data.LocalDAO
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_member_detail.*
import java.io.IOException

class MemberDetailFragment : Fragment() {

    private lateinit var userData: RealmResults<User>

    companion object {
        val TAG = MemberDetailFragment::class.java.simpleName
        private const val READ_REQUEST_CODE = 42
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_member_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")
        //Intentから値を取得
        val intent: Intent = activity!!.intent
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
            Log.d(TAG, "setOnClickListener")
            val intent = Intent(activity, EditActivity::class.java)
            intent.putExtra("testName", mDetailName.text)
            intent.putExtra("testBirthDay", mDetailBirthDayText.text)
            intent.putExtra("testSkill", mDetailSkillText.text)
            intent.putExtra("testHobby", mDetailHobbyText.text)
            startActivity(intent)
        }

        mDetailImage.setOnClickListener{
            Log.d(TAG, "setOnClickListener")
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, READ_REQUEST_CODE)
        }
    }

    private fun setLayout() {
        Log.d(TAG, "setLayout")
        val member = userData.first()

        mDetailName.text         = member!!.name
        mDetailBirthDayText.text = member!!.birthDay
        mDetailSkillText.text    = member!!.skill
        mDetailHobbyText.text    = member!!.hobby
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult")

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val uri: Uri = data.data
                try {
                    val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, uri)
                    mDetailImage.setImageBitmap(bitmap)

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
