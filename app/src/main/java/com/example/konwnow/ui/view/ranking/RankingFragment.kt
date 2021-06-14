package com.example.konwnow.ui.view.ranking

import FadeOutTransformation
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Friend
import com.example.konwnow.ui.adapter.RankingAdapter
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.utils.Constants
import com.example.konwnow.utils.LottieDialog
import com.example.konwnow.viewmodel.FriendViewModel
import me.relex.circleindicator.CircleIndicator3


class RankingFragment: Fragment() {
    private lateinit var v: View
    private lateinit var rankingVp: ViewPager2
    private lateinit var rankingAdapter: RankingAdapter
    private lateinit var rankingLv: LottieAnimationView
    private var rankMap = mutableMapOf<String,List<Friend.FriendData>>()
    private var mIndicator: CircleIndicator3? = null
    private val num_page = 3
    lateinit var dlg: LottieDialog


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_ranking, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setViewPager()
        requestRanking()
        setLottie()
    }

    private fun setLottie(){
        rankingLv = v.findViewById(R.id.lt_ranking)
        rankingLv.setOnClickListener {
            rankingLv.pauseAnimation()
            rankingLv.progress = 0F
            rankingLv.playAnimation()
        }
    }

    private fun setViewPager(){
        rankingVp = v.findViewById(R.id.vp_ranking) as ViewPager2
        rankingAdapter = RankingAdapter()
        rankingVp.adapter = rankingAdapter
        rankingAdapter.rankingUpdateList(rankMap["correctRank"])


        //뷰페이저 애니메이션
        rankingVp.setPageTransformer(FadeOutTransformation())
        //뷰페이저 인디케이터
        mIndicator = v.findViewById(R.id.indicator);
        mIndicator!!.setViewPager(rankingVp);
        mIndicator!!.createIndicators(num_page, 0)
        rankingVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position == 0){
                    //correctRank
                        Log.d("맵","0번 실행")
                    rankingAdapter.rankingUpdateList(rankMap["correctRank"])
                }else if(position == 1){
                    //examRank
                    Log.d("맵","1번 실행")
                    rankingAdapter.rankingUpdateList(rankMap["examRank"])
                }else{
                    //wordRank
                    Log.d("맵","2번 실행")
                    rankingAdapter.rankingUpdateList(rankMap["wordRank"])
                }
            }
        })
    }

    private fun requestRanking() {
        var friendViewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(
            FriendViewModel::class.java
        )
            friendViewModel.getRankListObserver().observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    Log.d("응답",it.data.correctRank.toString())
                    rankMap["correctRank"] = it.data.correctRank
                    rankMap["examRank"] = it.data.examRank
                    rankMap["wordRank"] = it.data.wordRank
                    Log.d("맵",rankMap.toString())
                    rankingAdapter.rankingUpdateList(rankMap["correctRank"])
                } else {
                    val dlg: AlertDialog.Builder = AlertDialog.Builder(context,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
                    dlg.setTitle("친구 목록이 없습니다.")
                    dlg.setMessage("친구를 추가해주세요.")
                    dlg.setNeutralButton("확인", DialogInterface.OnClickListener { dialog, which ->
                    })
                    dlg.show()
                }
            })


        //TODO: 토큰 변경
        friendViewModel.getRank(MainActivity.getUserData().loginToken)
        //friendViewModel.getRank("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJrbm93bm93IiwidXNlciI6ImFhYUBnbWFpbC5jb20iLCJ1c2VySWQiOjEsImlhdCI6MTYyMjUzODI2Mn0.tgUstyfdHJqjjIaErUNl_OFPHF_hmwvx10mdBmnHDXg")
    }

}