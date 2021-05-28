package com.example.konwnow

import android.app.Application
import com.example.konwnow.data.local.SharedPreference


/**
 * instance를 반환하는 class
 * Context가 필요할 때 사용하면 됩니다
 * App.intance
 */

class App: Application() {

    companion object {
        lateinit var instance : App
        lateinit var sharedPrefs: SharedPreference
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        sharedPrefs = SharedPreference(applicationContext)
    }
}