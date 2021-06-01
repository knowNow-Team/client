package com.example.konwnow.ui.view.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.konwnow.App
import com.example.konwnow.R
import com.example.konwnow.data.local.UserDatabase
import com.example.konwnow.data.local.UserEntity
import com.example.konwnow.data.remote.dto.Users
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.utils.Constants
import com.example.konwnow.utils.LOGIN
import com.example.konwnow.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var googleSignInClient : GoogleSignInClient
    lateinit var localDB : UserDatabase

    private lateinit var viewModel : LoginViewModel

    private var originId =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        getloginToken()

        val btnGoogle = findViewById<ImageView>(R.id.btn_google)
        btnGoogle.setOnClickListener(this)
    }

    @SuppressLint("StaticFieldLeak")
    private fun getloginToken() {
        localDB = UserDatabase.getInstance(this)!!
        val insertTask = object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                originId = localDB.userDao().getIdToken()
                if(originId == null){
                    Log.d("originId","null")
                }else{
                    Log.d("originId",originId)
                }

            }
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
            }
        }
        insertTask.execute()
    }

    override fun onStart() {
        super.onStart()
        /*TODO : 자동로그인
        조건 : DB에 토큰이 저장되어 있는지 확인.
        DB에 저장된 access token을 서버로 전달하여 로그인 시도
        -> 토큰 유효하면 응딥 ok,  자동로그인 성공
        -> 토큰 만료되면 응답 no,  로그인 화면으로 이동. -> refresh
        */
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_google -> {
                val gso: GoogleSignInOptions =
                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestId()
                        .requestEmail()
                        .requestProfile()
                        .requestIdToken("180417186984-phupqcsr68qvf07j15li9ldb5tc3aqo5.apps.googleusercontent.com")
                        .build()
                googleSignInClient = GoogleSignIn.getClient(this,gso)
                signIn()
            }
        }
    }

    private fun callLogin(google_id_token: String, type: LOGIN.LOGIN_FLAG) {
        Log.d(Constants.TAG,"call login")
        viewModel = ViewModelProvider(this,defaultViewModelProviderFactory).get(LoginViewModel::class.java)
        viewModel.getLoginDataObserver().observe(this, Observer<Users.UserResponseBody>{
            if(it != null){
                Log.d(Constants.TAG,"data not null")
                val loginToken = it.data!!.userAuth.loginToken
                val refreshToken = it.data!!.userAuth.refreshToken
                val nickname = it.data.nickName
                val userID = it.data.id
                val email = it.data.userEmail
                val level = it.data.userLevel
                val message = it.data.profileMessage ?: "상태 메세지가 없습니다."
                var user = UserEntity(1, google_id_token, loginToken, refreshToken, nickname, userID, email, level, message)
                when(type){
                    LOGIN.LOGIN_FLAG.OTHER_LOGIN-> {
                        App.sharedPrefs.saveWordBookId(null)
                        App.sharedPrefs.saveTitle(null)
                        updateData(user)
                    }
                    LOGIN.LOGIN_FLAG.NULL_LOGIN -> {
                        App.sharedPrefs.saveWordBookId(null)
                        App.sharedPrefs.saveTitle(null)
                        insertData(user)
                    }
                }
                Log.d("google login body: ",it.toString())

                val intent = Intent(this, MainActivity::class.java)
                Toast.makeText(this,"${nickname}님 로그인 되었습니다.",Toast.LENGTH_SHORT).show()
                startActivity(intent)
                Log.d(Constants.TAG,"Login success")
            }else{
                signupNext(google_id_token)
                Log.d(Constants.TAG,"sign up ... ing")
            }
        })
        viewModel.postGoogleLogin(google_id_token)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, LOGIN.RC_SIGN_UP)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LOGIN.RC_SIGN_UP) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try{
            val account = task.getResult(ApiException::class.java)
            if(originId == null){
                callLogin(account.idToken,LOGIN.LOGIN_FLAG.NULL_LOGIN)
            }else{
                if(account.idToken == originId){
                    callLogin(originId,LOGIN.LOGIN_FLAG.NORMAL_LOGIN)
                }else if(account.idToken != originId){
                    callLogin(account.idToken,LOGIN.LOGIN_FLAG.OTHER_LOGIN)
                }
            }
        } catch (e: ApiException) {
            Log.w("google", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun signupNext(idToken : String) {
        val intent = Intent(this, AddInfoActivity::class.java)
        intent.putExtra("idToken", idToken)
        startActivity(intent)
    }

    @SuppressLint("StaticFieldLeak")
    private fun insertData(user : UserEntity) {
        localDB = UserDatabase.getInstance(this)!!
        val insertTask = object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                localDB.userDao().insert(user)
                Log.d("로그","room insert success")
            }
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
            }
        }
        insertTask.execute()
    }

    @SuppressLint("StaticFieldLeak")
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