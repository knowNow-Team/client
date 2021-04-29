package com.example.konwnow.ui.view.mypage

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.konwnow.App
import com.example.konwnow.R
import com.example.konwnow.ui.view.login.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener

import com.example.konwnow.data.model.dto.Users
import com.example.konwnow.ui.adapter.RankingAdapter
import com.example.konwnow.ui.view.test.PuzzleTestActivity
import com.example.konwnow.ui.view.test.TestLogDialog
import com.google.android.material.switchmaterial.SwitchMaterial
import me.relex.circleindicator.CircleIndicator3

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
    private var alarmFlag = false


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_mypage, container, false)
        setButton()
        setSwitch()
        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1){
            if(resultCode== RESULT_CANCELED){
                alarmFlag = false
                switchAlarm.isChecked = false
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
        tvSetAlarm.setOnClickListener{
            mIntent = Intent(activity, SetAlarmActivity::class.java)
            startActivityForResult(mIntent,1)
        }
        tvManual.setOnClickListener{
            mIntent = Intent(activity, ManualActivity::class.java)
            startActivityForResult(mIntent,1)
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
                startActivity(mIntent)
            }
    }

    private fun setSwitch(){
        var mIntent : Intent
        switchAlarm = v.findViewById(R.id.switch_alarm)
        switchAlarm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmFlag = isChecked
                mIntent = Intent(activity, ActivitySetAlarm::class.java)
                startActivityForResult(mIntent,1)
            } else {
                alarmFlag = isChecked
                alarmOff()
            }
        }
    }

    private fun alarmOff(){
        var am = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent = Intent(context, AlarmBroadcastReceiver::class.java)
        var PendingIntent = PendingIntent.getBroadcast(context, AlarmBroadcastReceiver.NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        if (PendingIntent != null) {
            am.cancel(PendingIntent)
            AlarmBroadcastReceiver.count = 0
            PendingIntent.cancel()
            toast("알람이 해제되었습니다.")
        }
    }

    private fun toast(message:String){ Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }

}
