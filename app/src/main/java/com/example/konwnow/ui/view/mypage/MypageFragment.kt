package com.example.konwnow.ui.view.mypage

import android.annotation.SuppressLint
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.konwnow.App
import com.example.konwnow.R
import com.example.konwnow.data.local.UserDatabase
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.ui.view.login.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.example.konwnow.utils.ALARM
import com.example.konwnow.utils.Constants
import com.example.konwnow.utils.LOGIN
import com.google.android.material.switchmaterial.SwitchMaterial

class MypageFragment: Fragment() {
    private lateinit var v: View
    private lateinit var tvFriend: TextView
    private lateinit var tvSetAlarm: TextView
    private lateinit var tvManual: TextView
    private lateinit var tvComment: TextView
    private lateinit var tvLogout: TextView
    private lateinit var mIntent : Intent
    private lateinit var btnUpdateProfile : Button

    lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var switchAlarm: SwitchMaterial

    private lateinit var db : UserDatabase

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_mypage, container, false)
        setButton()
        setSwitch()

        setDate()
        return v
    }

    private fun setDate() {
         val tvNickname = v.findViewById<TextView>(R.id.tv_user_nick)
        val tvLevel= v.findViewById<TextView>(R.id.tv_user_level)
        tvNickname.text = MainActivity.getUserData().nickname
        tvLevel.text = "Level ${MainActivity.getUserData().level}"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1){
            if(resultCode== RESULT_CANCELED){
                App.sharedPrefs.saveAlarm(false)
                switchAlarm.isChecked = false
            }else if(resultCode== RESULT_OK){
                Log.d("코드1","성공")
            }
        }else if(requestCode == 2) {
            if(resultCode== RESULT_CANCELED){
                App.sharedPrefs.saveAlarm(false)
                switchAlarm.isChecked = false
            }else if(resultCode== RESULT_OK){
                Log.d("코드2","성공")
            }
        }
    }


    private fun setButton() {
        tvFriend = v.findViewById(R.id.tv_friend)
        tvSetAlarm = v.findViewById(R.id.tv_set_alarm)
        tvManual = v.findViewById(R.id.tv_manual)
        tvComment = v.findViewById(R.id.tv_comment)
        tvLogout = v.findViewById(R.id.tv_logout)
        btnUpdateProfile = v.findViewById(R.id.btn_update_profile)



        //클릭 이벤트
        tvFriend.setOnClickListener{
            mIntent = Intent(activity, FriendActivity::class.java)
            startActivity(mIntent)
        }
//        tvSetAlarm.setOnClickListener{
//            mIntent = Intent(activity, SetAlarmActivity::class.java)
//            startActivityForResult(mIntent,1)
//        }
        tvManual.setOnClickListener{
            val dlg = ManualDialog(context!!)
            dlg.start()
        }
        tvComment.setOnClickListener{
            mIntent = Intent(activity, CommentActivity::class.java)
            startActivityForResult(mIntent,1)
        }
        tvLogout.setOnClickListener {
            signOut();
        }
        btnUpdateProfile.setOnClickListener {
            mIntent = Intent(activity, UpdateProfileActivity::class.java)
            startActivityForResult(mIntent,1)
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
                startActivityForResult(mIntent,LOGIN.RC_LOGOUT)
            }
    }

    private fun setSwitch(){
        var mIntent : Intent
        switchAlarm = v.findViewById(R.id.switch_alarm)
        switchAlarm.isChecked = App.sharedPrefs.getAlarm()
        switchAlarm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                App.sharedPrefs.saveAlarm(true)
                mIntent = Intent(activity, SetAlarmActivity::class.java)
                startActivityForResult(mIntent,1)
            } else {
                alarmOff()
            }
        }
    }

    private fun alarmOff(){
        App.sharedPrefs.saveAlarm(false)
        var am = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent = Intent(context, AlarmBroadcastReceiver::class.java)
        var PendingIntent = PendingIntent.getBroadcast(context, ALARM.NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        if (PendingIntent != null) {
            am.cancel(PendingIntent)
            AlarmBroadcastReceiver.count = 0
            PendingIntent.cancel()
            toast("알람이 해제되었습니다.")
        }
    }

    private fun toast(message:String){ Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }

}
