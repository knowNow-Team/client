package com.example.konwnow.ui.view.mypage

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Folder
import com.example.konwnow.ui.adapter.FolderAdapter

class SetAlarmActivity : AppCompatActivity() {
    private lateinit var folderAdapter: FolderAdapter
    var folderList = arrayListOf<Folder>()
    var selectedFolderList = arrayListOf<Folder>()
    private lateinit var notKnowCb: CheckBox
    private lateinit var confuseCb: CheckBox
    private lateinit var knowCb: CheckBox
    var checkedTag = arrayListOf<Int>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        setToolbar()
        setButton()
        setFolderList()
        setTag()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==2){
            if(resultCode== RESULT_CANCELED){
                setResult(RESULT_CANCELED)
                finish()
            }else if(resultCode== RESULT_OK){
                setResult(RESULT_OK)
                finish()
            }
        }
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
            //선택된 태그 체크
            getCheckedTag()
            val mIntent = Intent(this,SetAlarmTimeActivity::class.java)
            var bundle = Bundle()
            bundle.putParcelableArrayList("folderList", selectedFolderList)
            mIntent.putExtra("folder", bundle)
            mIntent.putExtra("TagList",checkedTag)
            startActivityForResult(mIntent,2)
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

    private fun setTag(){
        notKnowCb = findViewById(R.id.not_know)
        confuseCb = findViewById(R.id.confuse)
        knowCb = findViewById(R.id.know)
    }

    private fun getCheckedTag(){
        checkedTag.clear()
        if(notKnowCb.isChecked){
            checkedTag.add(0)
        }
        if(confuseCb.isChecked){
            checkedTag.add(1)
        }
        if(knowCb.isChecked){
            checkedTag.add(2)
        }
        for(i: Int in checkedTag){
            Log.d("선택된 태그",i.toString())
        }
    }
}