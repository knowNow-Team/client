package com.example.konwnow.ui.view.mypage

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.example.konwnow.R
import com.ramotion.fluidslider.FluidSlider


class ActivitySetAlarmTime : AppCompatActivity() {
    private lateinit var startTimePicker: TimePicker
    private lateinit var endTimePicker: TimePicker
    private lateinit var sbAlarmNum: FluidSlider
    private lateinit var btnSubmit: Button
    val max = 45
    val min = 0
    val total = max - min
    var alarmNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_time)
        setToolbar()
        setTimePicker()
        setSeekBar()
        setButton()
    }

    private fun setButton() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notificationBuilder: NotificationCompat.Builder? = null
        notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelID = "channel_01" //알림채널 식별자
            val channelName = "MyChannel01" //알림채널의 이름(별명)

            //알림채널 객체 만들기
            val channel =
                NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT)

            //알림매니저에게 채널 객체의 생성을 요청
            notificationManager.createNotificationChannel(channel)

            NotificationCompat.Builder(this, channelID)
        } else {
            NotificationCompat.Builder(this, "channel_01")
        }

        val REPLY_KEY = "reply"
        val REPLY_LABEL = "단어 입력" // Action 에 표시되는 Label

        val replyIntent = Intent(this, ActivityReply::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        var replyPendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 33 , replyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        var remoteInput = RemoteInput.Builder(REPLY_KEY)
            .setLabel(REPLY_LABEL)
            .build()

        val notiAction = NotificationCompat.Action.Builder(R.drawable.ic_right, REPLY_LABEL, replyPendingIntent)
            .addRemoteInput(remoteInput)
            .setAllowGeneratedReplies(true)
            .build()


        notificationBuilder.setSmallIcon(R.drawable.ic_hit)
        notificationBuilder.setContentTitle("단어 알림")
        notificationBuilder.setContentText("단어 내용")
        notificationBuilder.setStyle(NotificationCompat.DecoratedCustomViewStyle())
        notificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
        notificationBuilder.addAction(notiAction)
        notificationBuilder.setAutoCancel(true)


        val notification = notificationBuilder.build()

        btnSubmit = findViewById(R.id.btn_submit)
        btnSubmit.setOnClickListener {
            Log.d("클릭","클릭!!!!!!!!!1111")
            notificationManager.notify(33, notification)
        }
    }

    private fun setSeekBar() {
        sbAlarmNum = findViewById(R.id.sb_alarm_num)
        sbAlarmNum.positionListener = { pos ->
            sbAlarmNum.bubbleText = "${min + (total  * pos).toInt()}"
            alarmNum = min + (total  * pos).toInt()
        }
        sbAlarmNum.position = 0.3f
        sbAlarmNum.startText ="$min"
        sbAlarmNum.endText = "$max"

    }

    private fun setTimePicker() {
        startTimePicker = findViewById<TimePicker>(R.id.tp_start)
        endTimePicker = findViewById<TimePicker>(R.id.tp_end)
        startTimePicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        endTimePicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        startTimePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            Log.d("시 ", hourOfDay.toString())
            Log.d("분 ", minute.toString())
        }

        endTimePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            Log.d("시 ", hourOfDay.toString())
            Log.d("분 ", minute.toString())
        }

    }

    private fun setToolbar() {
        val title = findViewById<TextView>(R.id.tv_title)
        val btnBack = findViewById<ImageButton>(R.id.ib_back)
        title.text = getString(R.string.AlarmTitle)

        btnBack.setOnClickListener { finish() }
    }
}