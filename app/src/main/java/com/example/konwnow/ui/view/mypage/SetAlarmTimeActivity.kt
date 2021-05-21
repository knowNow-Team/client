package com.example.konwnow.ui.view.mypage

import android.app.*
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.data.remote.dto.Words
import com.example.konwnow.utils.ALARM
import com.ramotion.fluidslider.FluidSlider
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SetAlarmTimeActivity : AppCompatActivity() {
    private lateinit var startTimePicker: TimePicker
    private lateinit var endTimePicker: TimePicker
    private lateinit var sbAlarmNum: FluidSlider
    private lateinit var btnSubmit: Button
    private var wordsList = arrayListOf<Words>()
    private var startHour = 0
    private var startMinute = 0
    private var endHour = 0
    private var endMinute = 0
    private var startTime:Long = 0
    private var endTime:Long = 0
    var folderList = arrayListOf<WordBook>()
    var checkedTag = arrayListOf<Int>()


    val max = 45
    val min = 0
    val total = max - min
    var alarmNum = 0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_time)
        setData()
        setToolbar()
        setTimePicker()
        setSeekBar()
        setButton()
    }

    override fun onBackPressed() {
        val dlg: AlertDialog.Builder = AlertDialog.Builder(this,  android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
        dlg.setTitle(R.string.close)
        dlg.setMessage(R.string.alarmCancel)
        dlg.setPositiveButton("네", DialogInterface.OnClickListener { dialog, which ->
            setResult(RESULT_CANCELED)
            finish()
        })
        dlg.setNegativeButton("아니요") { dialog, which ->
        }
        dlg.show()
    }

    private fun setData() {
        //folderList = intent!!.getBundleExtra("folder")!!.getParcelableArrayList<WordBook>("folderList") as ArrayList<WordBook>
        checkedTag = intent!!.getIntegerArrayListExtra("TagList") as ArrayList<Int>

        for(item in folderList){
            //Log.d("폴더",item.name)
        }

        for(item in checkedTag){
            Log.d("태그",item.toString())
        }
        requestWords()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    private fun setButton() {
        btnSubmit = findViewById(R.id.btn_submit)
        btnSubmit.setOnClickListener {
            if(!checkTimeZone()){
                Toast.makeText(this, getString(R.string.wrongAlarmTime), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

            val intent = Intent(this, AlarmBroadcastReceiver::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            var bundle = Bundle()
            bundle.putParcelableArrayList("wordList", wordsList)
            intent.putExtra("word", bundle)
            intent.putExtra("startHour", startHour)
            intent.putExtra("startMinute", startMinute)
            intent.putExtra("endHour", endHour)
            intent.putExtra("endMinute", endMinute)

            val pendingIntent = PendingIntent.getBroadcast(
                this, ALARM.NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )


            val repeatInterval: Long = (endTime-startTime) / alarmNum


            var calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
            }
            Log.d("현재시간", longTimeToDatetimeAsString(calendar.timeInMillis)!!)


            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                startTime+10*1000,
                repeatInterval,
                pendingIntent
            )
            setResult(RESULT_OK)
            finish()
            Toast.makeText(this, getString(R.string.setAlarm), Toast.LENGTH_SHORT).show()
        }
    }


    private val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

    //long형 타임을 String으로 변환.
    fun longTimeToDatetimeAsString(resultTime: Long): String? {
        val dateFormat = SimpleDateFormat(DATE_FORMAT)
        return dateFormat.format(resultTime)
    }

    private fun checkTimeZone(): Boolean {
        if(startHour > endHour){
            return false
        }else if(startHour == endHour){
            if(startMinute >= endMinute){
                return false
            }
        }
        return true
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setTimePicker() {
        //기본 설정
        startTimePicker = findViewById<TimePicker>(R.id.tp_start)
        endTimePicker = findViewById<TimePicker>(R.id.tp_end)
        startTimePicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        endTimePicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        //시간 default
        startHour = startTimePicker.hour
        startMinute = startTimePicker.minute
        endHour = endTimePicker.hour
        endMinute = endTimePicker.minute
        var calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
        }
        startTime = calendar.timeInMillis
        endTime = calendar.timeInMillis

        //리스너
        startTimePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            startHour = hourOfDay
            startMinute = minute

            calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }
            startTime = calendar.timeInMillis
        }

        endTimePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            endHour = hourOfDay
            endMinute = minute
            calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }
            endTime = calendar.timeInMillis
        }

    }

    private fun setToolbar() {
        val title = findViewById<TextView>(R.id.tv_title)
        val btnBack = findViewById<ImageButton>(R.id.ib_back)
        title.text = getString(R.string.AlarmTitle)

        btnBack.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}