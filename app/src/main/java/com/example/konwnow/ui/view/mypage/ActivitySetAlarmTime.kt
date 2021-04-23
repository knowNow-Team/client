package com.example.konwnow.ui.view.mypage

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.os.SystemClock
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Words
import com.ramotion.fluidslider.FluidSlider
import java.lang.System.`in`


class ActivitySetAlarmTime : AppCompatActivity() {
    private lateinit var startTimePicker: TimePicker
    private lateinit var endTimePicker: TimePicker
    private lateinit var sbAlarmNum: FluidSlider
    private lateinit var btnSubmit: Button
    private var wordsList = arrayListOf<Words>()

    val max = 45
    val min = 0
    val total = max - min
    var alarmNum = 0

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWords()
        setContentView(R.layout.activity_alarm_time)
        setToolbar()
        setTimePicker()
        setSeekBar()
        setButton()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    private fun setButton() {

        btnSubmit = findViewById(R.id.btn_submit)
        btnSubmit.setOnClickListener {
            Log.d("클릭", "클릭!!!!!!!!!1111")
            requestWords()
            Log.d("리스트", wordsList[2].eng)

            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

            val intent = Intent(this, AlarmBroadcastReceiver::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            var bundle = Bundle()
            bundle.putParcelableArrayList("wordList",wordsList)
            intent.putExtra("word",bundle)
            val pendingIntent = PendingIntent.getBroadcast(     // 2
                this, AlarmBroadcastReceiver.NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val repeatInterval: Long = 1000
            Log.d("시간", repeatInterval.toString())
            val triggerTime = (SystemClock.elapsedRealtime()  // 4
                    + 7 * 1000)
            alarmManager.setInexactRepeating(   // 5
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerTime,
                repeatInterval,
                pendingIntent
            )
            Log.d("실행", "Onetime Alarm On")
            Toast.makeText(this, "Alarm", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestWords() {
        wordsList.clear()
        wordsList.add(Words("Complex", "복잡한", 0))
        wordsList.add(Words("movie", "영화관", 1))
        wordsList.add(Words("Fragment", "조각", 2))
        wordsList.add(Words("Complex", "복잡한", 0))
        wordsList.add(Words("movie", "영화관", 0))
        wordsList.add(Words("Fragment", "조각", 1))
        wordsList.add(Words("Complex", "복잡한", 2))
        wordsList.add(Words("movie", "영화관", 0))
        wordsList.add(Words("Fragment", "조각", 1))
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