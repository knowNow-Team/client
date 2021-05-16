package com.example.konwnow.ui.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.konwnow.R
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnGoogle = findViewById<ImageView>(R.id.btn_google)
        btnGoogle.setOnClickListener(this)
    }


    override fun onStart() {
        super.onStart()
        val recentAccount = GoogleSignIn.getLastSignedInAccount(this)

        //구글 로그인을 한 계정
       if (recentAccount != null) {
            val intent = Intent(this, MainActivity::class.java)
            Log.d(Constants.TAG, "login automatically")
            startActivity(intent)
        }

        /*TODO : 자동로그인
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
            /*TODO : 회원가입 & 로그인 구분
            tokenID 가 DB에 저장된 tokenID랑 같으면 -> 로그인 api -> 응답 성공하면 main -> 응답 실패하면( refresh 전달 )
            같지 않거나 DB가 비어있다면 -> 회원가입 api -> 닉네임 입력 화면*/
            // 구글 로그인 성공
            Log.w("google", "signInResult:success code=" + account)
            updateGoogleLoginUi(account)
        } catch (e: ApiException) {
            // 구글 로그인 실패
            Log.w("google", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun updateGoogleLoginUi(account: GoogleSignInAccount) {
        val intent = Intent(this, AddInfoActivity::class.java)
        val idToken = account.idToken.toString()
        intent.putExtra("idToken", idToken)
        startActivity(intent)
    }

}