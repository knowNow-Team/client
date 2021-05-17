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
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.konwnow.R
import com.example.konwnow.data.local.UserDatabase
import com.example.konwnow.data.local.UserEntity
import com.example.konwnow.data.remote.dto.Users
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.viewmodel.LoginViewModel


class AddInfoActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var db : UserDatabase

    private lateinit var idToken : String
    private val refreshToken = "refresh"
    private lateinit var email : String

    private lateinit var viewModel : LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_info)

        val requestIntent = intent
        idToken = requestIntent.getStringExtra("idToken")!!
        email =requestIntent.getStringExtra("email")!!


        val btnSignin = findViewById<Button>(R.id.btn_sign_in)
        btnSignin.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        viewModel = ViewModelProvider(this,defaultViewModelProviderFactory).get(LoginViewModel::class.java)
        viewModel.getDataObserver().observe(this, Observer<Users.SignUpBody>{
            if(it != null){
                Log.d("view","view에서 viewModel 접근 성공")
            }else{
                Log.d("view","view에서 viewModel 관찰 실패")
            }
        })
        val nickname = findViewById<EditText>(R.id.et_nickname).text.toString()

        //viewmodel을 통해 통신하기.
        viewModel.postLogin(idToken,nickname)
        var user = UserEntity(idToken,refreshToken,nickname,email!!)
        insertData(user)

        val intent = Intent(this,MainActivity::class.java)
        Toast.makeText(this,"${email}님 KnowNow회원이 되었습니다~*^^*", Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }

}