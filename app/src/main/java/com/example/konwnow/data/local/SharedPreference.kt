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

    fun getWordBookId() : String? = sharedPreference!!.getString("id",null)
    fun getTitle() : String? = sharedPreference!!.getString("title",null)
    fun getCount() : Int? = sharedPreference!!.getInt("count",0)

}