package com.example.konwnow.ui.view.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.konwnow.App
import com.example.konwnow.R
import com.example.konwnow.ui.view.login.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener


class MypageFragment: Fragment() {
    private lateinit var v: View
    private lateinit var tvFriend: TextView
    private lateinit var tvSetAlarm: TextView
    private lateinit var tvManual: TextView
    private lateinit var tvComment: TextView
    private lateinit var tvLogout: TextView
    private lateinit var mIntent : Intent

    lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_mypage, container, false)
        setButton()
        return v
    }

    private fun setButton() {
        tvFriend = v.findViewById(R.id.tv_friend)
        tvSetAlarm = v.findViewById(R.id.tv_set_alarm)
        tvManual = v.findViewById(R.id.tv_manual)
        tvComment = v.findViewById(R.id.tv_comment)
        tvLogout = v.findViewById(R.id.tv_logout)



        //클릭 이벤트
        tvFriend.setOnClickListener{
            mIntent = Intent(activity, FriendActivity::class.java)
            startActivity(mIntent)
        }
        tvSetAlarm.setOnClickListener{
            mIntent = Intent(activity, SetAlarmActivity::class.java)
            startActivityForResult(mIntent,1)
        }
        tvManual.setOnClickListener{
            mIntent = Intent(activity, ManualActivity::class.java)
            startActivityForResult(mIntent,1)
        }
        tvComment.setOnClickListener{
            mIntent = Intent(activity, CommentActivity::class.java)
            startActivityForResult(mIntent,1)
        }
        tvLogout.setOnClickListener {
            signOut();
        }
    }

    private fun signOut() {
        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestEmail()
                .requestProfile()
                .requestIdToken("180417186984-phupqcsr68qvf07j15li9ldb5tc3aqo5.apps.googleusercontent.com")
                .build()

        googleSignInClient = GoogleSignIn.getClient(App.instance,gso);
        googleSignInClient.signOut()
            .addOnCompleteListener {
                mIntent = Intent(activity, LoginActivity::class.java)
                startActivity(mIntent)
            }
    }
}
