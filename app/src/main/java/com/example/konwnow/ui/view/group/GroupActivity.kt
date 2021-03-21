package com.example.konwnow.ui.view.group

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.konwnow.R

class GroupActivity : AppCompatActivity() {
    var btnBack : ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        btnBack = findViewById(R.id.ib_group_back)
        btnBack!!.setOnClickListener{
            finish()
        }

    }
}