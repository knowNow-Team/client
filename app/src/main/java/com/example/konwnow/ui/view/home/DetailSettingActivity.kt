package com.example.konwnow.ui.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.konwnow.R

class DetailSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_setting)

        val title = findViewById<TextView>(R.id.tv_title)
        val btnBack = findViewById<ImageButton>(R.id.ib_back)
        val btnApply = findViewById<Button>(R.id.btn_apply_setting)
        title.text = "세부 설정하기"

        btnBack.setOnClickListener { finish() }

        btnApply.setOnClickListener{ finish() }
    }
}