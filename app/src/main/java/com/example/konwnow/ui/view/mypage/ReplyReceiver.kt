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

class ReplyReceiver: BroadcastReceiver() {
    var mContext: Context? = null
    var mIntent: Intent? = null
    companion object {
        const val NOTIFICATION_ID = 33
        const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
        val REPLY_KEY = "reply"
        val REPLY_LABEL = "단어 입력" // Action 에 표시되는 Label
    }

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
        val repliedNotification =  NotificationCompat.Builder(mContext!!, PRIMARY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_hit)
            .setContentTitle("단어 알림")
            .setContentText("단어 내용")
            .setContentText(text)
            .build()
        val notificationManager: NotificationManager = mContext!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, repliedNotification)
        Log.d("노티파이","성공")
    }


}