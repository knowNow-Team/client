package com.example.konwnow.ui.view.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Users
import com.example.konwnow.ui.adapter.RankingAdapter
import com.example.konwnow.ui.view.test.PuzzleTestActivity
import com.example.konwnow.ui.view.test.TestLogDialog
import com.google.android.material.switchmaterial.SwitchMaterial
import me.relex.circleindicator.CircleIndicator3

class MypageFragment: Fragment() {
    private lateinit var v: View
    private lateinit var tvFriend: TextView
    private lateinit var tvSetAlarm: TextView
    private lateinit var tvManual: TextView
    private lateinit var tvComment: TextView
    private lateinit var tvLogout: TextView
    private lateinit var switchAlarm: SwitchMaterial
    private var alarmFlag = false

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_mypage, container, false)
        setButton()
        setSwitch()
        return v
    }

    private fun setButton() {
        tvFriend = v.findViewById(R.id.tv_friend)
        tvSetAlarm = v.findViewById(R.id.tv_set_alarm)
        tvManual = v.findViewById(R.id.tv_manual)
        tvComment = v.findViewById(R.id.tv_comment)
        tvLogout = v.findViewById(R.id.tv_logout)

        var mIntent : Intent

        //클릭 이벤트
        tvFriend.setOnClickListener{
            mIntent = Intent(activity, ActivityFriend::class.java)
            startActivity(mIntent)
        }
        tvSetAlarm.setOnClickListener{
            if(alarmFlag){
                mIntent = Intent(activity, ActivitySetAlarm::class.java)
                startActivityForResult(mIntent,1)
            }else{
                toast("알람 키세요!!")
            }
        }
        tvManual.setOnClickListener{
            val dlg = ManualDialog(context!!)
            dlg.start()
        }
        tvComment.setOnClickListener{
            mIntent = Intent(activity, ActivityComment::class.java)
            startActivityForResult(mIntent,1)
        }
        tvLogout.setOnClickListener{
            Log.d("로그아웃","클릭")
        }
    }

    private fun setSwitch(){
        switchAlarm = v.findViewById(R.id.switch_alarm)
        switchAlarm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmFlag = isChecked
                Log.d("스위치","on")
            } else {
                alarmFlag = isChecked
                Log.d("스위치","off")
            }
        }
    }

    private fun toast(message:String){ Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }

}