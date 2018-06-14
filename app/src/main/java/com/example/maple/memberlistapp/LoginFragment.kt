package com.example.maple.memberlistapp

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maple.memberlistapp.data.ApiDAO
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), IAPI {

    companion object {
        val TAG: String = LoginFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        //container!!.removeAllViews()
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")

        mSignInBtn.setOnClickListener {
            Log.d(TAG, "SignInButton Clicked")
            val email:    String = mLoginMailText.text.toString()
            val password: String = mLoginPWText.text.toString()

            ApiDAO.API_DAO.tryLogin(this, email, password)
        }
    }

    override fun onApiCompleted() {
        Log.d(TAG, "onApiCompleted")

        //ログイン状態に変更
        val pref   = activity!!.getSharedPreferences(Util.PREF_LOGIN_STATUS, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean(Util.PREF_KEY_LOGIN_STATUS, true)

        //Activityにコールバック、リスト表示画面を作成する

    }

    override fun onApiFailed() {
        Log.d(TAG, "onApiFailed")

    }
}