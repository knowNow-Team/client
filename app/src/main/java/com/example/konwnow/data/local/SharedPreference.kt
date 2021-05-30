package com.example.konwnow.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


class SharedPreference(context: Context) {

    private val sharedPreference: SharedPreferences? = context.getSharedPreferences("wordBook",MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreference!!.edit()

    fun saveWordBookId(id : String){
        editor.putString("id",id).apply()
    }

    fun saveTitle(title : String){
        editor.putString("title",title).apply()
    }

    fun saveCount(count : Int){
        editor.putInt("count",count).apply()
    }

    fun saveAlarm(flag : Boolean){
        editor.putBoolean("alarm",flag).apply()
    }

    fun saveFirst(flag:Boolean){
        editor.putBoolean("first",flag).apply()
    }


    fun getFirst() : Boolean = sharedPreference!!.getBoolean("first",true)


    fun getWordBookId() : String? = sharedPreference!!.getString("id",null)
    fun getTitle() : String? = sharedPreference!!.getString("title",null)
    fun getCount() : Int? = sharedPreference!!.getInt("count",0)
    fun getAlarm() : Boolean = sharedPreference!!.getBoolean("alarm",false)
    fun checkValid() : Boolean? {
        if(sharedPreference!!.getString("id",null) == null || sharedPreference!!.getString("title",null) == null){
            return false
        }
        return true
    }

}