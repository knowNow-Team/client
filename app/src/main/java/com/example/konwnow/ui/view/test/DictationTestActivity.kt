package com.example.konwnow.ui.view.test

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.TestLog
import com.example.konwnow.ui.adapter.TestLogAdapter


class DictationTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_dictation)
        setToolbar()
    }

    private fun setToolbar() {
        val tbTestDictation = findViewById<View>(R.id.tb_test_dictation)
        val tbTitle = tbTestDictation.findViewById<TextView>(R.id.tv_title)
        val tbBtnBack = tbTestDictation.findViewById<ImageButton>(R.id.ib_back)

        tbTitle.setText(R.string.test_log_title)
        tbBtnBack!!.setOnClickListener {
            finish()
        }

    }


    private fun toast(message:String){ Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }

}