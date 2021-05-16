package com.example.konwnow.ui.view.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.konwnow.R
import com.example.konwnow.data.local.UserDatabase
import com.example.konwnow.data.local.UserEntity
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.utils.Constants
import com.example.konwnow.utils.LOGIN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var googleSignInClient : GoogleSignInClient
    lateinit var localDB : UserDatabase
    private var originId : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        getIdToken()

        val btnGoogle = findViewById<ImageView>(R.id.btn_google)
        btnGoogle.setOnClickListener(this)
    }

    @SuppressLint("StaticFieldLeak")
    private fun getIdToken() {
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
                googleSignInClient = GoogleSignIn.getClient(this,gso);
                signIn()
            }
        }
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
            if(account.idToken == originId){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Log.d(Constants.TAG,"Login success")
            }else{
                updateGoogleLoginUi(account)
                Log.d(Constants.TAG,"sign up")
            }
        } catch (e: ApiException) {
            // 구글 로그인 실패
            Log.w("google", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun updateGoogleLoginUi(account: GoogleSignInAccount) {
        val intent = Intent(this, AddInfoActivity::class.java)
        val idToken = account.idToken.toString()
        val email = account.email.toString()
        intent.putExtra("idToken", idToken)
        intent.putExtra("email",email)
        startActivity(intent)
    }

}