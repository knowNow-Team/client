package com.example.konwnow.ui.view.mypage

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.ui.adapter.FolderAdapter
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.utils.Constants
import com.example.konwnow.viewmodel.WordBookViewModel

class SetAlarmActivity : AppCompatActivity() {
    private lateinit var folderAdapter: FolderAdapter
    var folderList = arrayListOf<WordBook.WordBookData>()
    var selectedFolderList = arrayListOf<String>()
    private lateinit var notKnowCb: CheckBox
    private lateinit var confuseCb: CheckBox
    private lateinit var knowCb: CheckBox
    var checkedTag = arrayListOf<String>()
    private lateinit var viewModel: WordBookViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,defaultViewModelProviderFactory).get(WordBookViewModel::class.java)
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
//                finish()
            }else if(resultCode== RESULT_OK){
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    private fun setFolderList() {
        requestGroups()
        var folderRV = findViewById<RecyclerView>(R.id.rv_folder)
        folderRV.setHasFixedSize(true)
        folderRV.layoutManager = LinearLayoutManager(this)
        folderAdapter = FolderAdapter(folderList){ i: Int, b: Boolean ->
            if(b){
                selectedFolderList.add(folderList[i].id)
            }else{
                selectedFolderList.remove(folderList[i])
            }
            for(i in selectedFolderList){
//                Log.d("선택폴더",i.name)
            }
        }

        folderAdapter.folderUpdateList(folderList)
        folderRV.adapter = folderAdapter
    }

    private fun requestGroups() {
        viewModel.getDataReponse().observe(this, Observer {
            if (it != null){
                Log.d(Constants.TAG,"단어장 가져오기 성공!")
                folderList.clear()
                folderList.addAll(it.data)
                Log.d(Constants.TAG,folderList.toString())
            }else {
                Log.d(Constants.TAG,"단어장 get response null!")
            }
            folderAdapter.notifyDataSetChanged()
        })
        viewModel.getWordBook(MainActivity.getUserData().loginToken)
    }

    private fun setButton() {
        val btnNext = findViewById<Button>(R.id.btn_next)
        btnNext.setOnClickListener {
            //선택된 태그 체크
            if(getCheckedTag()){
                val mIntent = Intent(this,SetAlarmTimeActivity::class.java)
//                var bundle = Bundle()
//                bundle.putParcelableArrayList("folderList", selectedFolderList)
                mIntent.putExtra("folder", selectedFolderList)
                mIntent.putExtra("TagList",checkedTag)
                startActivityForResult(mIntent,2)
            }
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

    private fun getCheckedTag(): Boolean{
        checkedTag.clear()
        if(notKnowCb.isChecked){
            checkedTag.add(getString(R.string.doNotKnow))
        }
        if(confuseCb.isChecked){
            checkedTag.add(getString(R.string.confused))
        }
        if(knowCb.isChecked){
            checkedTag.add(getString(R.string.know))
        }
        if(checkedTag.isEmpty()){
            toast("필터를 선택해주세요.")
            return false
        }
        return true
    }

    private fun toast(message: String){ Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }

}