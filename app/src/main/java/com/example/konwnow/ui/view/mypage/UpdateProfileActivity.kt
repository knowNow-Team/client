package com.example.konwnow.ui.view.mypage

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.konwnow.R
import com.example.konwnow.ui.view.MainActivity

class UpdateProfileActivity : AppCompatActivity() {
    private lateinit var originedNick : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        setToolbar()
        originedNick = findViewById(R.id.et_update_nickname)
        originedNick.setText(MainActivity.getUserData().nickname)
    }

    private fun setToolbar() {
        val title  = findViewById<TextView>(R.id.tv_title)
        title.text = "프로필 수정"

        val btnBack = findViewById<ImageButton>(R.id.ib_back)
        btnBack.setOnClickListener {
            setResult(1)
            finish()
        }
    }
}