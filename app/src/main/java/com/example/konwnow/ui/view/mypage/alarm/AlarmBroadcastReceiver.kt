package com.example.konwnow.ui.view.mypage.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Words
import com.example.konwnow.utils.ALARM
import java.util.*
import kotlin.collections.ArrayList


class AlarmBroadcastReceiver: BroadcastReceiver() {
    companion object {
        const val TAG = "AlarmReceiver"
        var count = 0
    }
    lateinit var notificationManager: NotificationManager
    lateinit var wordsList: ArrayList<Words.Word>
    private var startHour = 0
    private var startMinute = 0
    private var endHour = 0
    private var endMinute = 0


    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onReceive(context: Context, intent: Intent?) {

        wordsList = intent!!.getBundleExtra("word")!!.getParcelableArrayList<Words.Word>("wordList") as ArrayList<Words.Word>
        startHour = intent!!.getIntExtra("startHour",0)
        startMinute = intent!!.getIntExtra("startMinute",0)
        endHour = intent!!.getIntExtra("endHour",0)
        endMinute = intent!!.getIntExtra("endMinute",0)


        if(wordsList!= null){
            notificationManager = context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

            createNotificationChannel()
            deliverNotification(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    private fun deliverNotification(context: Context) {
        val contentIntent = Intent(context, ReplyReceiver::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        contentIntent.putExtra("wordEng",wordsList[count].word.toLowerCase(Locale.ROOT))
        contentIntent.putExtra("wordKor",wordsList[count].meanings[0])
        val contentPendingIntent = PendingIntent.getBroadcast(
            context,
            ALARM.NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        var remoteInput = RemoteInput.Builder(ALARM.REPLY_KEY)
            .setLabel(ALARM.REPLY_LABEL)
            .build()

        val notiAction = NotificationCompat.Action.Builder(
            R.drawable.ic_right,
            ALARM.REPLY_LABEL,
            contentPendingIntent
        )
            .addRemoteInput(remoteInput)
            .setAllowGeneratedReplies(true)
            .build()

        val builder =
            NotificationCompat.Builder(context, ALARM.PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_hit)
                .setContentTitle("단어 알림")
                .setContentText(wordsList[count++].meanings[0])
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .addAction(notiAction)
                .setAutoCancel(false)

        if(checkTimeZone()){
            notificationManager.notify(ALARM.NOTIFICATION_ID, builder.build())
        }
    }

    private fun checkTimeZone(): Boolean {
        var calendar = Calendar.getInstance()
        var currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        var currentMinute = calendar.get(Calendar.MINUTE)


        if(currentHour in startHour..endHour){
            if(currentHour == startHour && currentMinute >= startMinute){
                Log.d("리시버","체크 성공")
                return true
            }else if(currentHour == endHour && currentMinute <= endMinute){
                Log.d("리시버","체크 성공")
                return true
            }
        }
        return false
    }


    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                ALARM.PRIMARY_CHANNEL_ID,
                "Stand up notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.description = "AlarmManager Tests"
            notificationManager.createNotificationChannel(
                notificationChannel
            )
        }
    }

}