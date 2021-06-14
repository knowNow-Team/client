package com.example.konwnow.ui.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import com.example.konwnow.App
import com.example.konwnow.R
import com.example.konwnow.utils.Constants
import com.example.konwnow.utils.HOMEWORD

class DetailSettingActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnBack : ImageButton
    private lateinit var btnApply : Button
    private var filter =""
    private var order =""

    private lateinit var doNotKnow : CheckBox
    private lateinit var know : CheckBox
    private lateinit var confuse : CheckBox
    private var selectedFilter = HashMap<String, Boolean>()

    private lateinit var desc : RadioButton
    private lateinit var asc : RadioButton
    private lateinit var newest : RadioButton
    private lateinit var random : RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_setting)

        val title = findViewById<TextView>(R.id.tv_title)
        btnBack = findViewById(R.id.ib_back)
        btnApply = findViewById(R.id.btn_make_groups)
        doNotKnow = findViewById(R.id.not_know)
        know = findViewById(R.id.know)
        confuse = findViewById(R.id.confuse)
        desc = findViewById(R.id.radio_desc)
        asc = findViewById(R.id.radio_acs)
        newest = findViewById(R.id.radio_newest)
        random = findViewById(R.id.radio_random)

        desc.setOnClickListener(this)
        asc.setOnClickListener(this)
        newest.setOnClickListener(this)
        random.setOnClickListener(this)
        doNotKnow.setOnClickListener(this)
        know.setOnClickListener(this)
        confuse.setOnClickListener(this)

        title.text = "세부 설정하기"

        getWordBookData()
        setSetting()
        setButton()
    }


    private fun setSetting(){
        if(App.sharedPrefs.getFilter1()!!){
            doNotKnow.isChecked = true
        }
        if(App.sharedPrefs.getFilter2()!!){
            confuse.isChecked = true
        }
        if(App.sharedPrefs.getFilter3()!!){
            know.isChecked = true
        }

        when(order){
            HOMEWORD.ORDER.ASC ->{
                asc.isChecked = true
            }
            HOMEWORD.ORDER.DESC -> {
                desc.isChecked = true
            }
            HOMEWORD.ORDER.NEWEST -> {
                newest.isChecked = true
            }
        }
    }

    private fun getWordBookData() {
        order = App.sharedPrefs.getOrder()!!
        Log.d(Constants.TAG,"저장된 세부설정 데이터 : $filter")
    }

    private fun setButton() {
        btnBack.setOnClickListener { finish() }
        btnApply.setOnClickListener{
            App.sharedPrefs.savedFilter(selectedFilter)
            Log.d(Constants.TAG,"checkbox ${selectedFilter["confuse"]}")
            App.sharedPrefs.saveOrder(order)
            finish()
        }
    }

    override fun onClick(v: View?) {
        when(v){
            desc -> order = HOMEWORD.ORDER.DESC
            asc -> order =HOMEWORD.ORDER.ASC
            newest -> order =HOMEWORD.ORDER.NEWEST

            doNotKnow -> {
                when(doNotKnow.isChecked){
                    true -> selectedFilter.put(HOMEWORD.FILTER.doNotKnow,true)
                    false ->selectedFilter.put(HOMEWORD.FILTER.doNotKnow,false)
                }

            }

            know -> {
                when(know.isChecked){
                    true ->selectedFilter.put(HOMEWORD.FILTER.memorized,true)
                    false ->selectedFilter.put(HOMEWORD.FILTER.memorized,false)
                }
            }

            confuse -> {
                when(confuse.isChecked){
                    true -> selectedFilter.put(HOMEWORD.FILTER.confused,true)
                    false -> selectedFilter.put(HOMEWORD.FILTER.confused,false)
                }
            }

        }
    }
}