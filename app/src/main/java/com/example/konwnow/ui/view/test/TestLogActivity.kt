package com.example.konwnow.ui.view.test

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.broooapps.graphview.CurveGraphConfig
import com.broooapps.graphview.CurveGraphView
import com.broooapps.graphview.models.GraphData
import com.broooapps.graphview.models.PointMap
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.TestLog
import com.example.konwnow.ui.adapter.TestLogAdapter
import com.example.konwnow.viewmodel.TestLogViewModel


class TestLogActivity : AppCompatActivity() {
    var testLogList = arrayListOf<TestLog.TestLogData>()
    private lateinit var testLogRv: RecyclerView
    private lateinit var curveGraphView : CurveGraphView
    private lateinit var viewModel: TestLogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_log)
        setTestLog()
        requestTest()
        setToolbar()
    }


    private fun requestTest() {
        viewModel = ViewModelProvider(this,defaultViewModelProviderFactory).get(TestLogViewModel::class.java)
        viewModel.getDataObserver().observe(this, Observer<TestLog.TestLogData>{
            if(it != null){
                testLogList.add(it)
                testLogRv.adapter?.notifyDataSetChanged()
                Log.d("observer 성공",testLogList.toString())
            }else{
                Log.d("view","view에서 viewModel 관찰 실패")
            }
        })
        viewModel.getTest()
        setGrapgh()
    }

    private fun setGrapgh() {
        curveGraphView = findViewById(R.id.cgv_test)
        curveGraphView.configure(
            CurveGraphConfig.Builder(this)
                .setAxisColor(R.color.white) // Set number of values to be displayed in X ax
                .setVerticalGuideline(4) // Set number of background guidelines to be shown.
                .setHorizontalGuideline(2)
                .setGuidelineColor(R.color.bw_g4) // Set color of the visible guidelines.
                .setNoDataMsg(" No Data ") // Message when no data is provided to the view.
                .setxAxisScaleTextColor(R.color.Black) // Set X axis scale text color.
                .setyAxisScaleTextColor(R.color.Black) // Set Y axis scale text color
                .setAnimationDuration(2000) // Set Animation Duration
                .build()
        )
        val pointMap = PointMap()
        for( i in testLogList.indices){
            Log.d("점수",testLogList[i].score.toString())
            pointMap.addPoint(i, testLogList[i].score)
        }

        val gd = GraphData.builder(this)
            .setPointMap(pointMap)
            .setGraphStroke(R.color.colorMain)
            .setGraphGradient(R.color.colorAccent, R.color.white)
            .build()

        Handler().postDelayed(Runnable { curveGraphView.setData(testLogList.size-1, 100, gd) }, 250)
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
//        testLogList.add(TestLog(20, "30/50", "전체", "2021.03.10"))
//        testLogList.add(TestLog(50, "30/50", "토익 영단어, 영어2", "2021.02.10"))
//        testLogList.add(TestLog(100, "30/50", "영어2", "2021.01.10"))

        Log.d("테스트 로그",testLogList.size.toString())

        testLogRv = findViewById<RecyclerView>(R.id.rv_test_log)
        testLogRv.setHasFixedSize(true)
        testLogRv.layoutManager = LinearLayoutManager(this)
        testLogRv.adapter = TestLogAdapter(this, testLogList){
            reTestDialog(it)
        }
    }

    private fun reTestDialog(position: Int) {
        val dlg: AlertDialog.Builder = AlertDialog.Builder(
            this,
            android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth
        )
        dlg.setTitle(R.string.reTest)
        dlg.setPositiveButton("네", DialogInterface.OnClickListener { dialog, which ->
            toast(position.toString())
        })
        dlg.setNegativeButton("아니요") { dialog, which ->
            toast("아니요")
        }
        dlg.show()
    }

    private fun toast(message: String){ Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }

}