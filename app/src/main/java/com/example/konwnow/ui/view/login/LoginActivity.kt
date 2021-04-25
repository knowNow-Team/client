package com.example.konwnow.ui.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.konwnow.R
import com.example.konwnow.ui.view.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val RC_SIGN_IN = 1004
    lateinit var googleSignInClient : GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // config_signin
        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestEmail()
                .requestProfile()
                .requestIdToken("180417186984-phupqcsr68qvf07j15li9ldb5tc3aqo5.apps.googleusercontent.com")
                .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso);

        val btnGoogle = findViewById<ImageView>(R.id.btn_google)
        btnGoogle.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_google -> { signIn() }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //구글로그인 응답하는 코드
        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try{
            val account = task.getResult(ApiException::class.java)
            // 구글 로그인 성공
            Log.w("google", "signInResult:success code=" + account)
            updateGoogleLoginUi()
        } catch (e: ApiException) {
            // 구글 로그인 실패
            Log.w("google", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun updateGoogleLoginUi() {
        val recentAccount = GoogleSignIn.getLastSignedInAccount(this)

        if (recentAccount != null) { // 회원가입을 했다면 홈으로
            val intent = Intent(this, MainActivity::class.java)
            Log.d("idToken", recentAccount?.idToken.toString())
            startActivity(intent)
        } else if(recentAccount == null) { // 회원가입을 안했다면 닉네임 설정으로
            val intent = Intent(this, AddInfoActivity::class.java)
            val idToken = recentAccount?.idToken.toString()
            intent.putExtra("idToken", idToken)
            startActivity(intent)
        }
    }
}