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
import com.example.konwnow.App
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
                val message = it.data!!.profileMessage ?: "?????? ???????????? ????????????."
                var user = UserEntity(1, idToken, loginToken, refreshToken, nickname, userID, email,level,message)
                insertData(user)

                App.sharedPrefs.saveWordBookId(null)
                App.sharedPrefs.saveTitle(null)
                val intent = Intent(this, MainActivity::class.java)
                Toast.makeText(this, "${email}??? KnowNow????????? ???????????????~*^^*", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }else{
                val tvWaning = findViewById<TextView>(R.id.tv_wanning)
                tvWaning.text = "?????? ???????????? ????????? ?????????."
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
                Log.d("??????","room insert success")
            }
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
            }
        }
        insertTask.execute()
    }

}