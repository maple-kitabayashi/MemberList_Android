package com.example.maple.memberlistapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.maple.memberlistapp.api.MyAccount
import com.example.maple.memberlistapp.data.ApiDAO
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), IAPI {

    companion object {
        val TAG = LoginActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        if (isLogined()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        setContentView(R.layout.activity_login)

        mSignInBtn.setOnClickListener {
            Log.d(TAG, "SignInButton Clicked")
            val email: String    = mLoginMailText.text.toString()
            val password: String = mLoginPWText.text.toString()
            ApiDAO.API_DAO.tryLogin(this, email, password)
        }
    }

    private fun isLogined(): Boolean {
        val preferences = getSharedPreferences(Util.PREF_LOGIN_STATUS, Context.MODE_PRIVATE)
        val isLogin = preferences.getBoolean(Util.PREF_KEY_LOGIN_STATUS, false)
        return isLogin
    }

    override fun onApiCompleted() {
        Log.d(TAG, "onApiCompleted")
        val pref = getSharedPreferences(Util.PREF_LOGIN_STATUS, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean(Util.PREF_KEY_LOGIN_STATUS, true)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onApiFailed() {
        Log.d(TAG, "onApiFailed")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}