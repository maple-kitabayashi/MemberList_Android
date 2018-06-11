package com.example.maple.memberlistapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.CardView
import android.widget.ImageView
import android.widget.TextView
import com.example.maple.memberlistapp.api.User
import com.example.maple.memberlistapp.data.LocalDAO
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_member_list.*
import kotlinx.coroutines.experimental.Deferred


/**
 * メンバーリスト表示フラグメント
 */
class MemberListFragment : Fragment() {

    private var  mJob: Deferred<Unit>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_member_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mCardLinear.removeAllViews()

        //TODO 別スレッドでDB読み込み コールバック用意する
//        mJob = async {
//            myTask()
//        }

        //ローカルDBからデータ読み込み
        var userData = LocalDAO.LOCAL_DAO.readData()
        //カードビューにデータをセット
        setCardData(userData)
    }

//    private suspend fun myTask() {
//
//        async(UI) {
//            Log.d("Task", "task_start")
//        }
//
//        async {
//            //端末DBから、全メンバーデータを取得
//            var memberData = LocalDAO.LOCAL_DAO.readData()
//            //取得したメンバーデータをカードにセット
//            setCardData(memberData)
//        }
//
//        async(UI) {
//            Log.d("Task", "task_end")
//        }
//    }

    private fun setCardData(data: RealmResults<User>) {

        //取得したカードデータ分処理する
        for ((index, user) in data.withIndex()) {
            val inflater     = activity!!.getSystemService(LAYOUT_INFLATER_SERVICE)       as LayoutInflater
            val linearLayout = inflater.inflate(R.layout.item_member_cardview, null) as LinearLayout
            val cardImage    = linearLayout.findViewById(R.id.card_image)                 as ImageView
            val cardView     = linearLayout.findViewById(R.id.member_card_view)           as CardView
            val cardName     = linearLayout.findViewById(R.id.mDetailName)                as TextView
            val cardBirthDay = linearLayout.findViewById(R.id.card_birthday)              as TextView

            cardName.setText(user.name)
            cardBirthDay.setText(user.birthDay)

            //押下時、詳細画面へ遷移
            cardView.setOnClickListener {
                val compat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, cardImage, cardImage.transitionName)
                val intent = Intent(activity, MemberDetailActivity::class.java)
                
                intent.putExtra(R.string.detail_activity_key.toString(), user.id)
                startActivity(intent, compat.toBundle())
            }
            //カード追加
            mCardLinear.addView(linearLayout, index)
        }
    }
}
