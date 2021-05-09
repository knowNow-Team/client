package com.example.konwnow.ui.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import com.example.konwnow.R
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelProviders.of
import com.example.konwnow.data.model.dto.Users
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.ui.viewmodel.LoginViewModel


class AddInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_info)

        val requestIntent = intent
        val idToken = requestIntent.getStringExtra("idToken")


        val viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.getDataObserver().observe(this, Observer<Users>{
            if(it != null){
                //업데이트 되어야 할 UI에 전달해주기.
                Log.d("view","view에서 viewModel 접근 성")
            }else{
                Log.d("view","view에서 viewModel 관찰 실패")
            }
        })

        val btnSignin = findViewById<Button>(R.id.btn_sign_in)
        btnSignin.setOnClickListener {
            val nickname = findViewById<EditText>(R.id.et_nickname).text.toString()
            Log.d("idToken", idToken!!)
            Log.d("nickname", nickname)

            //viewmodel을 통해 통신하기.
            viewModel.postLogin(idToken,nickname)

//            val user = UserEntity(idToken,nickname)
//            UserDatabase
//                .getInstance(this)!!
//                .getUserDao()
//                .insert(user)
            Log.d("AddNickname","local db에 user 정보 저장!")

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}