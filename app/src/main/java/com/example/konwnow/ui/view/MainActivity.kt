package com.example.konwnow.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.konwnow.R
import com.example.konwnow.data.local.UserDatabase
import com.example.konwnow.data.local.UserEntity
import com.example.konwnow.ui.view.home.HomeFragment
import com.example.konwnow.ui.view.mypage.MypageFragment
import com.example.konwnow.ui.view.ranking.RankingFragment
import com.example.konwnow.ui.view.test.TestFragment
import com.example.konwnow.ui.view.write.WriteActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    var bnvHome: BottomNavigationView? = null

    companion object {
        private var users:UserEntity? = null
        fun getUserData():UserEntity {
            return users!!
        }
    }

    open lateinit var context_main : Context

    //Fragment
    private val homeFragment : HomeFragment by lazy { HomeFragment() }
    private val testFragment : TestFragment by lazy { TestFragment() }
    private val rankingFragment : RankingFragment by lazy { RankingFragment() }
    private val mypageFragment : MypageFragment by lazy { MypageFragment() }
    private lateinit var fab : FloatingActionButton
    private lateinit var db : UserDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context_main = this

        //bnv설정
        bnvHome = findViewById(R.id.bnv_home)
        initBottomNavigation()

        db = UserDatabase.getInstance(this)!!
        getAllUserData()

    }

    @SuppressLint("StaticFieldLeak")
    private fun getAllUserData() {
        val insertTask = object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                MainActivity.users = db.userDao().getAll()
                Log.d("idtoken",users!!.idToken)
                Log.d("logintoken",users!!.loginToken)
                Log.d("refreshToken",users!!.refreshToken)
                Log.d("nickname",users!!.nickname)
                Log.d("emaail",users!!.email)
                Log.d("userId", users!!.userID.toString())

            }
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
            }
        }
        insertTask.execute()
    }

    private fun initBottomNavigation() {
        fab = findViewById(R.id.fab)

        fab.setOnClickListener {
            val intent = Intent(this,WriteActivity::class.java)
            startActivity(intent)
        }

        bnvHome?.run {
            setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.nav_home ->{
                        fab.show()
                        setFragment(homeFragment)
                    }
                    R.id.nav_test -> {
                        fab.hide()
                        setFragment(testFragment)
                    }
                    R.id.nav_ranking -> {
                        fab.hide()
                        setFragment(rankingFragment)
                    }
                    R.id.nav_mypage ->{
                        fab.hide()
                        setFragment(mypageFragment)
                    }
                }
                true
            }
            selectedItemId = R.id.nav_home // 초기 프래그먼트
        }
    }

    //fragment 전환 함수
    fun setFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_container, fragment)
            .commit()
    }
}