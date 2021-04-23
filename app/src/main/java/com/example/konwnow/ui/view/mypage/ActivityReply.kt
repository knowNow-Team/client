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
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.konwnow.R

class ActivityReply: BroadcastReceiver() {
    var mContext: Context? = null
    val notificationId = 33
    var mIntent: Intent? = null
    val CHANNEL_ID = "channel_01"

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onReceive(context: Context?, intent: Intent?) {
        mContext = context
        Log.d("receiver","성공")
        mIntent = intent
        receiveInput()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    private fun receiveInput() {
        val REPLY_KEY = "reply"
        val remoteInput = RemoteInput.getResultsFromIntent(mIntent)
        remoteInput?.let{
            val text = it.getCharSequence(REPLY_KEY).toString()
            receiveSuccessNoti(text)
        }

    }

    private fun receiveSuccessNoti(text: String) {
        val repliedNotification =  NotificationCompat.Builder(mContext!!, CHANNEL_ID.toString())
            .setSmallIcon(R.drawable.ic_hit)
            .setContentTitle("단어 알림")
            .setContentText("단어 내용")
            .setContentText(text)
            .build()
        val notificationManager: NotificationManager = mContext!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, repliedNotification)
        Log.d("노티파이","성공")
    }


}