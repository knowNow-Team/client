package com.example.konwnow.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.konwnow.R
import com.example.konwnow.ui.view.home.HomeFragment
import com.example.konwnow.ui.view.mypage.MypageFragment
import com.example.konwnow.ui.view.ranking.RankingFragment
import com.example.konwnow.ui.view.test.TestFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    var bnvHome: BottomNavigationView? = null

    //Fragment
    private val homeFragment : HomeFragment by lazy { HomeFragment() }
    private val testFragment : TestFragment by lazy { TestFragment() }
    private val rankingFragment : RankingFragment by lazy { RankingFragment() }
    private val mypageFragment : MypageFragment by lazy { MypageFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //bnv설정
        bnvHome = findViewById(R.id.bnv_home)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        bnvHome?.run {
            setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.nav_home ->{
                        setFragment(homeFragment)
                    }
                    R.id.nav_test -> {
                        setFragment(testFragment)
                    }
                    R.id.nav_ranking -> {
                        setFragment(rankingFragment)
                    }
                    R.id.nav_mypage ->{
                        setFragment(mypageFragment)
                    }
                }
                true
            }
            selectedItemId = R.id.nav_home // 초기 프래그먼트
        }
    }

    //fragment 전환 함수
    private fun setFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_container, fragment)
            .commit()
    }
}