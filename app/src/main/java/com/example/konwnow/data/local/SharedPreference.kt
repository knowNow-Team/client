package com.example.konwnow.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.konwnow.utils.Constants
import com.example.konwnow.utils.HOMEWORD


class SharedPreference(context: Context) {

    private val sharedPreference: SharedPreferences? = context.getSharedPreferences("wordBook",MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreference!!.edit()

    fun saveWordBookId(id : String?){
        editor.putString("id",id).apply()
    }

    fun saveTitle(title : String?){
        editor.putString("title",title).apply()
    }

    fun saveCount(count : Int){
        editor.putInt("count",count).apply()
    }

    fun savedFilter(checked : HashMap<String,Boolean>){
        for(key in checked.keys){
            editor.putBoolean(key, checked[key]!!).apply()
        }
    }

    fun saveOrder(order: String){
        editor.putString("order",order).apply()
    }
    
        fun saveAlarm(flag : Boolean){
        editor.putBoolean("alarm",flag).apply()
    }

    fun saveFirst(flag:Boolean){
        editor.putBoolean("first",flag).apply()
    }

    fun getWordBookId() : String? = sharedPreference!!.getString("id",null)
    fun getTitle() : String? = sharedPreference!!.getString("title",null)
    fun getCount() : Int? = sharedPreference!!.getInt("count",0)
    fun getFilter1() : Boolean? = sharedPreference!!.getBoolean(HOMEWORD.FILTER.doNotKnow,false)
    fun getFilter2() : Boolean? = sharedPreference!!.getBoolean(HOMEWORD.FILTER.confused,false)
    fun getFilter3() : Boolean? = sharedPreference!!.getBoolean(HOMEWORD.FILTER.memorized,false)
    fun getOrder(): String? = sharedPreference!!.getString("order",HOMEWORD.ORDER.NEWEST)
    fun getFirst() : Boolean = sharedPreference!!.getBoolean("first",true)
    fun getAlarm() : Boolean = sharedPreference!!.getBoolean("alarm",false)

    fun checkValid() : Boolean? {
        if(sharedPreference!!.getString("id",null) == null || sharedPreference!!.getString("title",null) == null){
            return false
        }
        return true
    }

    fun selectedFilter() : String {
        val selectedList = arrayListOf<String>()
        if(getFilter1()!!) {
            selectedList.add(HOMEWORD.FILTER.doNotKnow)
        }
        if (getFilter2()!!){
            selectedList.add(HOMEWORD.FILTER.confused)
        }
        if(getFilter3()!!){
            selectedList.add(HOMEWORD.FILTER.memorized)
        }
        return selectedList.joinToString(",")
    }

}