package com.example.konwnow.ui.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.konwnow.R
import com.example.konwnow.ui.view.MainActivity

class AddInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_info)

        val requestIntent = intent
        val idToken = requestIntent.getStringExtra("idToken")

        val btnSignin = findViewById<Button>(R.id.btn_sign_in)
        btnSignin.setOnClickListener {
            val getNickname = findViewById<EditText>(R.id.et_nickname)
            Log.d("idToken", idToken!!)
            Log.d("nickname", getNickname.text.toString())

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}