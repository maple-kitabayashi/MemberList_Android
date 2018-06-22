package com.example.maple.memberlistapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.fragment_edit.*
import java.io.IOException

class MemberEditFragment : Fragment(), View.OnClickListener {

    companion object {
        val TAG: String = MemberEditFragment::class.java.simpleName
        private const val READ_REQUEST_CODE     = 42 //画像取得要求コード
        private const val TRIMMING_REQUEST_CODE = 41 //トリミング要求コード
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")

        val intent:   Intent = activity!!.intent
        //val bundle:   Bundle = intent.getParcelableExtra("bundle")

        var imageBmp: Bitmap = intent!!.extras.getParcelable("testBmp")
        val name:     String = intent.getStringExtra("testName")
        val birthDay: String = intent.getStringExtra("testBirthDay")
        val skill:    String = intent.getStringExtra("testSkill")
        val hobby:    String = intent.getStringExtra("testHobby")

        //EditTextのtextはEditable型なので、factoryを作成して設定する
        val factory: Editable.Factory = Editable.Factory.getInstance()

        //現在設定中の内容を表示する
        mEditImage.setImageBitmap(imageBmp)
        mEditSkillText.text = factory.newEditable(skill)
        mEditHobbyText.text = factory.newEditable(hobby)

        mEditImage.setOnClickListener{
            clickImage()
        }

        mEditSaveBtn.setOnClickListener {
            clickSaveBtn()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult")
        //要求コード(requestCode)がstartActivityForResultで渡した値と一致するか、
        //操作が成功した時の値であるRESULT_OKであるか確認
        if (resultCode.equals(Activity.RESULT_OK)) {
            when(requestCode) {
                READ_REQUEST_CODE     -> tryChangeProfileImage(data)

                TRIMMING_REQUEST_CODE -> setTrimmingIcon(data)
            }
        }

//        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            tryChangeProfileImage(data)
//        }
    }

    /**
     * 押下処理
     */
    override fun onClick(v: View?) {
        Log.d(TAG, "setOnClickListener")
        //アイコン押下処理
        when (v!!.id) {
            R.id.mEditImage   -> clickImage()   //アイコン画像

            R.id.mEditSaveBtn -> clickSaveBtn() //編集完了ボタン
        }
    }

    /**
     * アイコン押下処理
     */
    private fun clickImage() {
        //デフォルトのアルバムアプリを開く
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    /**
     * 編集完了ボタン押下処理
     */
    private fun clickSaveBtn() {
        //詳細画面の画像を変更
        //mEditImage.setImageBitmap(bitmap)

        //TODO ローカルDBに保存する
        //ローカルDBに画像を保存
    }

    /**
     * プロフィール画像の変更にトライする
     */
    private fun tryChangeProfileImage(data: Intent?) {
        if (data != null) {
            val uri: Uri = data.data //画像のURIを取得
            //changeProfileImage(uri)  //画像変更処理
            try {
                changeProfileImage(uri) //画像変更処理
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun setTrimmingIcon(data: Intent?) {
        val trimmingBmp: Bitmap = data!!.extras.getParcelable("tri")
        mEditImage.setImageBitmap(trimmingBmp)
    }

    /**
     * プロフィール画像を変更
     */
    private fun changeProfileImage(uri: Uri) {

        //バンドルでURIを次のフラグメントへ渡す
        val bundle: Bundle = Bundle()
        bundle.putParcelable("uri", uri)

        val transaction: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        val fragment                         = ImageTrimmingFragment()

        fragment.arguments                   = bundle //バンドル設定
        fragment.setTargetFragment(this, TRIMMING_REQUEST_CODE)
        //transaction.replace(R.id.edit_frame_layout, fragment)
        transaction.add(R.id.edit_frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        //val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, mUri)
        //mCropImageView.startLoad(mUri, object :LoadCallback{
//            override fun onSuccess() {
//                Log.d(TAG, "changeProfileImage_onSuccess")
//            }
//
//            override fun onError(e: Throwable?) {
//                Log.d(TAG, "changeProfileImage_onError")
//                e!!.printStackTrace()
//            }
//        })
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
    }

    /**
     * getter
     */
    fun getTrimmingTargetCode(): Int = TRIMMING_REQUEST_CODE
}