package com.example.konwnow

import android.app.Application


/**
 * instance를 반환하는 class
 * Context가 필요할 때 사용하면 됩니다
 * App.intance
 */

class App: Application() {

    companion object {
        lateinit var instance : App

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}