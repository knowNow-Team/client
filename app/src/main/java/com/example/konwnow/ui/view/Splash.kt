package com.example.konwnow.ui.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.konwnow.R
import com.example.konwnow.ui.view.login.LoginActivity


class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        startSplash()
    }

    fun startSplash() {
        Log.d("스플래시","실행")
        val handler = Handler()
        handler.postDelayed(Runnable {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }


}