package com.example.konwnow.ui.view.mypage

import android.app.Notification
import android.app.NotificationManager
import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.konwnow.R
import com.example.konwnow.utils.ALARM

class ReplyReceiver: BroadcastReceiver() {
    var mContext: Context? = null
    var mIntent: Intent? = null


    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onReceive(context: Context?, intent: Intent?) {
        mContext = context
        Log.d("receiver","성공")
        mIntent = intent
        receiveInput()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    private fun receiveInput() {
        val remoteInput = RemoteInput.getResultsFromIntent(mIntent)
        remoteInput?.let{
            val userAnswer = it.getCharSequence(ALARM.REPLY_KEY).toString()
            receiveSuccessNoti(userAnswer)
        }
    }

    private fun receiveSuccessNoti(text: String) {
        var wordKor = mIntent!!.getStringExtra("wordKor")
        var wordEng = mIntent!!.getStringExtra("wordEng")
        var userAnswer = text.toLowerCase()

        Log.d("kor", wordKor.toString())
        Log.d("eng", wordEng.toString())
        Log.d("userAnswer", userAnswer)
        lateinit var repliedNotification: Notification
        if(userAnswer == wordEng!!.toLowerCase()){
            repliedNotification =  NotificationCompat.Builder(mContext!!, ALARM.PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_hit)
                .setContentTitle("정답입니다")
                .setContentText(wordKor)
                .setContentText(String.format(mContext!!.getString(R.string.rightNoti),text))
                .build()
        }else{
            repliedNotification =  NotificationCompat.Builder(mContext!!, ALARM.PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_miss)
                .setContentTitle("틀렸습니다")
                .setContentText(wordKor)
                .setContentText(String.format(mContext!!.getString(R.string.missNoti),userAnswer,wordEng))
                .build()
        }

        val notificationManager: NotificationManager = mContext!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(ALARM.NOTIFICATION_ID, repliedNotification)
        Log.d("노티파이","성공")
    }


}