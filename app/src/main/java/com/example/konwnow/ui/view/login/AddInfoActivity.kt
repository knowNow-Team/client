package com.example.konwnow.ui.view.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.konwnow.R
import com.example.konwnow.data.local.UserDatabase
import com.example.konwnow.data.local.UserEntity
import com.example.konwnow.data.remote.dto.Users
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.viewmodel.LoginViewModel


class AddInfoActivity : AppCompatActivity() {

    lateinit var db : UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_info)

        db = UserDatabase.getInstance(this)!!
        val requestIntent = intent
        val idToken = requestIntent.getStringExtra("idToken")

        val viewModel = ViewModelProvider(this,defaultViewModelProviderFactory).get(LoginViewModel::class.java)
        viewModel.getDataObserver().observe(this, Observer<Users>{
            if(it != null){
                //업데이트 되어야 할 UI에 전달해주기.
                Log.d("view","view에서 viewModel 접근 성공")
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
            var user = UserEntity(idToken,nickname)
           insertData(user)
            

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }

    @SuppressLint("StaticFieldLeak")
    private fun insertData(user : UserEntity) {
        val insertTask = object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                db.userDao().insert(user)
                Log.d("로그","room success")
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)

            }
        }
        insertTask.execute()
    }

}