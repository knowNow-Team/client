package com.example.konwnow.ui.view.ranking

import FadeOutTransformation
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Users
import com.example.konwnow.ui.adapter.RankingAdapter
import me.relex.circleindicator.CircleIndicator3






class RankingFragment: Fragment() {
    private lateinit var v: View
    private lateinit var rankingVp: ViewPager2
    private lateinit var rankingAdapter: RankingAdapter
    private lateinit var rankingLv: LottieAnimationView
    private var userList = arrayListOf<Users>()
    private var mIndicator: CircleIndicator3? = null
    private val num_page = 3


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_ranking, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requestRanking(0)
        setViewPager()
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
        rankingAdapter.rankingUpdateList(userList)
        rankingVp.adapter = rankingAdapter

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
                    requestRanking(0)
                }else if(position == 1){
                    requestRanking(1)
                }else{
                    requestRanking(2)
                }
                rankingAdapter.rankingUpdateList(userList)
            }
        })
    }

    private fun requestRanking(flag: Int) {
        userList.clear()

//        if(flag == 0){
//            userList.add(Users("가나다", "nick1", 0))
//            userList.add(Users("서동현", "nick2", 1))
//            userList.add(Users("빈지노", "nick3", 2))
//        }else if(flag == 1){
//            userList.add(Users("아이유", "nick4", 3))
//            userList.add(Users("박재범", "nick5", 4))
//            userList.add(Users("정기석", "nick6", 5))
//        }else{
//            userList.add(Users("윤진영", "nick7", 6))
//            userList.add(Users("창모", "nick8", 7))
//            userList.add(Users("넉살", "nick9", 8))
//        }
    }

}