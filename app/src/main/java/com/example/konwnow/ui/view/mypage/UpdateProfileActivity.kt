package com.example.konwnow.ui.view.mypage

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.konwnow.R
import com.example.konwnow.data.local.UserDatabase
import com.example.konwnow.data.local.UserEntity
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.viewmodel.LoginViewModel
import com.example.konwnow.viewmodel.WordBookViewModel

class UpdateProfileActivity : AppCompatActivity() {
    private var originedNick =""
    private var originedMessage =""

    private lateinit var nickname : EditText
    private lateinit var message : EditText

    private lateinit var btnApply : Button
    private lateinit var userViewModel : LoginViewModel

    lateinit var localDB : UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        userViewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(LoginViewModel::class.java)

        nickname = findViewById(R.id.et_update_nickname)
        message = findViewById(R.id.et_update_emotion)
        val title  = findViewById<TextView>(R.id.tv_title)
        title.text = "프로필 수정"

        setButton()
        setText()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun setText() {
        originedNick = MainActivity.getUserData().nickname
        originedMessage = MainActivity.getUserData().message

        nickname.setText(originedNick)
        message.setText(originedMessage)
    }

    private fun setButton() {
        val btnBack = findViewById<ImageButton>(R.id.ib_back)
        btnBack.setOnClickListener {
            finish()
        }

        btnApply = findViewById(R.id.btn_apply_update)
        btnApply.setOnClickListener {
            when(checkValid()){
                0 -> finish()
                1 -> changeNickname()
                2 ->changeMessage()
            }
        }
    }

    private fun changeNickname() {
        TODO("Not yet implemented")
    }

    private fun changeMessage() {
        userViewModel.putMessageObserver().observe(this, {
            if(it != null ){
                val loginToken = MainActivity.getUserData().loginToken
                val google_id_token = MainActivity.getUserData().idToken
                val refreshToken = MainActivity.getUserData().refreshToken
                val nickname = MainActivity.getUserData().nickname
                val userID = MainActivity.getUserData().userID
                val level = MainActivity.getUserData().level
                val email = MainActivity.getUserData().email
                var user = UserEntity(1, google_id_token, loginToken, refreshToken, nickname, userID, email, level, message.text.toString())
                updateData(user)
                finish()
            }else{

            }
        })
    }


    private fun checkValid(): Int {
        if(originedNick == nickname.text.toString() && originedMessage == message.text.toString() ){
            return 0
        }else{
            when(true){
                originedNick != nickname.text.toString() -> return 1
                originedMessage != message.text.toString() -> return 2
            }
        }
        return 0
    }

    private fun updateData(user : UserEntity) {
        localDB = UserDatabase.getInstance(this)!!
        val insertTask = object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                localDB.userDao().updateUser(user)
                Log.d("로그","room update success")
            }
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
            }
        }
        insertTask.execute()
    }
}