package com.example.konwnow.ui.view.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.konwnow.R
import com.example.konwnow.data.local.UserDatabase
import com.example.konwnow.data.local.UserEntity
import com.example.konwnow.data.remote.dto.Users
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.utils.Constants
import com.example.konwnow.viewmodel.LoginViewModel


class AddInfoActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var db : UserDatabase

    private lateinit var idToken : String
    private lateinit var viewModel : LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_info)

        val requestIntent = intent
        idToken = requestIntent.getStringExtra("idToken")!!


        val btnSignin = findViewById<Button>(R.id.btn_sign_in)
        btnSignin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val nickname = findViewById<EditText>(R.id.et_nickname).text.toString()
        viewModel = ViewModelProvider(this,defaultViewModelProviderFactory).get(LoginViewModel::class.java)
        viewModel.getSignUpDataObserver().observe(this, Observer<Users.UserResponseBody>{
            Log.d(Constants.TAG,"SignUpBody : $it")
            if(it != null) {
                val loginToken = it.data!!.userAuth.loginToken
                val refreshToken = it.data!!.userAuth.refreshToken
                val email = it.data!!.userEmail
                val userID = it.data!!.id
                val level = it.data!!.userLevel
                val message = it.data!!.profileMessage ?: "상태 메세지가 없습니다."
                var user = UserEntity(1, idToken, loginToken, refreshToken, nickname, userID, email,level,message)
                insertData(user)

                val intent = Intent(this, MainActivity::class.java)
                Toast.makeText(this, "${email}님 KnowNow회원이 되었습니다~*^^*", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }else{
                val tvWaning = findViewById<TextView>(R.id.tv_wanning)
                tvWaning.text = "이미 존재하는 닉네임 입니다."
            }
        })
        viewModel.postSignUp(idToken,nickname)
    }

    @SuppressLint("StaticFieldLeak")
    private fun insertData(user : UserEntity) {
        db = UserDatabase.getInstance(this)!!
        val insertTask = object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                db.userDao().insert(user)
                Log.d("로그","room insert success")
            }
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
            }
        }
        insertTask.execute()
    }

}