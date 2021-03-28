package com.example.konwnow.ui.view.test

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.TestLog
import com.example.konwnow.ui.adapter.TestLogAdapter


class TestLogActivity : AppCompatActivity() {
    var testLogList = arrayListOf<TestLog>()
    private lateinit var testLogRv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_log)
        setToolbar()
        setTestLog()
    }

    private fun setToolbar() {
        val tbTestLog = findViewById<View>(R.id.tb_test_log)
        val tbTitle = tbTestLog.findViewById<TextView>(R.id.tv_title)
        val tbBtnBack = tbTestLog.findViewById<ImageButton>(R.id.ib_back)

        tbTitle.setText(R.string.test_log_title)
        tbBtnBack!!.setOnClickListener {
            finish()
        }

    }

    private fun setTestLog() {
        //테스트 로그 데이터
        testLogList.add(TestLog(20,"30/50","전체","2021.03.10"))
        testLogList.add(TestLog(50,"30/50","토익 영단어, 영어2","2021.02.10"))
        testLogList.add(TestLog(100,"30/50","영어2","2021.01.10"))
        testLogList.add(TestLog(70,"30/50","몰라","2021.04.10"))
        testLogList.add(TestLog(68,"30/50","중요","2021.01.10"))

        testLogRv = findViewById<RecyclerView>(R.id.rv_test_log)
        testLogRv.setHasFixedSize(true)
        testLogRv.layoutManager = LinearLayoutManager(this)
        testLogRv.adapter = TestLogAdapter(this,testLogList)
    }
}