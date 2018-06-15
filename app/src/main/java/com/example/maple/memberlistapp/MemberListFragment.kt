package com.example.maple.memberlistapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.CardView
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.maple.memberlistapp.api.User
import com.example.maple.memberlistapp.data.ApiDAO
import com.example.maple.memberlistapp.data.LocalDAO
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_member_list.*
import kotlinx.coroutines.experimental.Deferred


/**
 * メンバーリスト表示フラグメント
 */
class MemberListFragment : Fragment(), IAPI {

    interface CallBack {
        fun callback()
    }

    companion object {
        val TAG = MemberListFragment::class.java.simpleName
    }

    private var  mJob: Deferred<Unit>? = null
    private val  callBack: MemberListFragment.CallBack by lazy { activity as MemberListActivity }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_member_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        mCardLinear.removeAllViews()

        //最終更新時間を取得
        val preference = activity!!.getSharedPreferences(Util.PREF_LAST_UPDATE, MODE_PRIVATE)
        val lastDate: String = preference.getString(Util.PREF_KEY_LAST_UPDATE_TIME, "")
        //サーバーからユーザーデータ取得
        ApiDAO.API_DAO.fetchUserData(this, lastDate)
    }

    private fun setCardData(data: RealmResults<User>) {
        Log.d(TAG, "setCardData_start")

        //取得したカードデータ分処理する
        for ((index, user) in data.withIndex()) {
            val inflater     = activity!!.getSystemService(LAYOUT_INFLATER_SERVICE)       as LayoutInflater
            val linearLayout = inflater.inflate(R.layout.item_member_cardview, null) as LinearLayout
            val cardImage    = linearLayout.findViewById(R.id.card_image)                 as ImageView
            val cardView     = linearLayout.findViewById(R.id.member_card_view)           as CardView
            val cardName     = linearLayout.findViewById(R.id.mCardName)                as TextView
            val cardBirthDay = linearLayout.findViewById(R.id.card_birthday)              as TextView

            cardName.setText(user.name)
            cardBirthDay.setText(user.birthDay)

            //押下時、詳細画面を表示
            cardView.setOnClickListener {
                val compat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, cardImage, cardImage.transitionName)
                val intent = Intent(activity, MemberDetailActivity::class.java)
                
                intent.putExtra(R.string.detail_activity_key_id.toString(), user.id)
                intent.putExtra(R.string.detail_activity_key_visibility.toString(), false)
                startActivity(intent, compat.toBundle())
            }
            //カード追加
            mCardLinear.addView(linearLayout, index)
        }
        Log.d(TAG, "setCardData_end")
    }

    /**
     * ユーザーデータ取得成功コールバック
     * ローカルDBからユーザーデータを取得し、レイアウトに反映
     */
    override fun onApiCompleted() {
        //ローカルDBからデータ読み込み
        var userData = LocalDAO.LOCAL_DAO.readData()
        //カードビューにデータをセット
        setCardData(userData)
        //セットが完了したらコールバックでリスト表示処理を実行
        callBack.callback()
    }

    override fun onApiFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
