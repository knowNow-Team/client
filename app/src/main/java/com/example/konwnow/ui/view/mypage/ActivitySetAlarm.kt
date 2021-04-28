package com.example.konwnow.ui.view.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Folder
import com.example.konwnow.ui.adapter.FolderAdapter
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import com.ramotion.foldingcell.FoldingCell

class ActivitySetAlarm : AppCompatActivity() {
    private lateinit var folderAdapter: FolderAdapter
    var folderList = arrayListOf<Folder>()
    var selectedFolderList = arrayListOf<Folder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        setToolbar()
        setButton()
        setFolderList()
    }

    private fun setFolderList() {
        requestFolder()
        var folderRV = findViewById<RecyclerView>(R.id.rv_folder)
        folderRV.setHasFixedSize(true)
        folderRV.layoutManager = LinearLayoutManager(this)
        folderAdapter = FolderAdapter(){ i: Int, b: Boolean ->
            if(b){
                selectedFolderList.add(folderList[i])
            }else{
                selectedFolderList.remove(folderList[i])
            }
            for(i in selectedFolderList){
                Log.d("선택폴더",i.name)
            }
        }

        folderAdapter.folderUpdateList(folderList)
        folderRV.adapter = folderAdapter
    }

    private fun requestFolder() {
        folderList.add(Folder("name1",4))
        folderList.add(Folder("name2",20))
        folderList.add(Folder("name3",30))
        folderList.add(Folder("name4",2))
        folderList.add(Folder("name5",12))
    }

    private fun setButton() {
        val btnNext = findViewById<Button>(R.id.btn_next)
        btnNext.setOnClickListener {
            val mIntent = Intent(this,ActivitySetAlarmTime::class.java)
            startActivity(mIntent)
            finish()
        }
    }


    private fun setToolbar() {
        val title = findViewById<TextView>(R.id.tv_title)
        val btnBack = findViewById<ImageButton>(R.id.ib_back)
        title.text = getString(R.string.AlarmTitle)

        btnBack.setOnClickListener { finish() }
    }
}