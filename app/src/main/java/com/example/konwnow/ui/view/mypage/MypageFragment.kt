package com.example.konwnow.ui.view.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Users
import com.example.konwnow.ui.adapter.RankingAdapter
import com.example.konwnow.ui.view.test.PuzzleTestActivity
import me.relex.circleindicator.CircleIndicator3

class MypageFragment: Fragment() {
    private lateinit var v: View
    private lateinit var tvFriend: TextView
    private lateinit var tvSetAlarm: TextView
    private lateinit var tvManual: TextView
    private lateinit var tvComment: TextView
    private lateinit var tvLogout: TextView

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_mypage, container, false)
        setButton()
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
            mIntent = Intent(activity, ActivitySetAlarm::class.java)
            startActivityForResult(mIntent,1)
        }
        tvManual.setOnClickListener{
            mIntent = Intent(activity, ActivityManual::class.java)
            startActivityForResult(mIntent,1)
        }
        tvComment.setOnClickListener{
            mIntent = Intent(activity, ActivityComment::class.java)
            startActivityForResult(mIntent,1)
        }
        tvLogout.setOnClickListener{
            Log.d("로그아웃","클릭")
        }
    }
}