package com.example.konwnow.ui.view.write

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.konwnow.App
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.ui.adapter.WriteAdapter
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.utils.Constants
import com.example.konwnow.viewmodel.WordBookViewModel
import com.google.android.material.tabs.TabLayout


class WriteActivity: AppCompatActivity() {
    companion object{
        val selectedWords= mutableSetOf<String>()
        fun addList(item:String){
            selectedWords.add(item)
        }
        fun popList(item : String){
            selectedWords.remove(item)
            Log.d("삭제",selectedWords.toString())
        }
    }
    private lateinit var btnBack : ImageButton
    private lateinit var btnSubmit : Button
    private lateinit var spnFolder : Spinner
    private lateinit var tabNav : TabLayout
    private lateinit var vpWrite : ViewPager
    private var wordBookID =""
    private var firstTitle =""
    private var size =0
    private lateinit var viewModel: WordBookViewModel
    private var groupsList = arrayListOf<WordBook.WordBooks>()
    private var selectedGroupId: String = ""
    val spinnerArray: MutableList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        viewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(WordBookViewModel::class.java)
        getWordBookData()
        setDefault()
        requestGroups()
    }

    private fun setFolders() {
        val adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, spinnerArray
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnFolder.adapter = adapter
        spnFolder.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                selectedGroupId = groupsList[position].wordBookID
                Log.d("선택",selectedGroupId)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }

    private fun requestGroups() {
        viewModel.getDataReponse().observe(this, Observer {
            if (it != null) {
                Log.d(Constants.TAG, "단어장 가져오기 성공!")
                Log.d(Constants.TAG, "response Body : ${it}")
                for (datas in it.data) {
                    groupsList.add(WordBook.WordBooks(datas.title, datas.allCount, datas.id))
                    spinnerArray.add(datas.title)
                }
                setFolders()
            } else {
                Log.d(Constants.TAG, "단어장 get response null!")
            }
        })
        viewModel.getWordBook(MainActivity.getUserData().loginToken)
    }

    private fun getWordBookData() {
        if(App.sharedPrefs.checkValid()!!){
            firstTitle = App.sharedPrefs.getTitle()!!
            wordBookID= App.sharedPrefs.getWordBookId()!!
            size = App.sharedPrefs.getCount()!!
        }else{
            firstTitle="단어장을 선택해주세요"
        }
        Log.d(Constants.TAG,"저장된 단어장 데이터 : ${firstTitle} , ${wordBookID}, ${size}")
    }


    override fun onBackPressed() {
            setResult(RESULT_CANCELED)
            finish()
    }


    private fun setDefault(){
        btnBack = findViewById<ImageButton>(R.id.ib_back)
        btnSubmit = findViewById<Button>(R.id.btn_submit)
        spnFolder = findViewById<Spinner>(R.id.spn_folder)
        tabNav = findViewById<TabLayout>(R.id.tab_input_mode)
        vpWrite = findViewById<ViewPager>(R.id.vp_write)

        btnBack.setOnClickListener { finish() }
        btnSubmit.setOnClickListener{
            putWords()
        }

        val PageAdapter = WriteAdapter(supportFragmentManager)
        PageAdapter.addFragment(TextWriteFragment(), "직접등록")
        PageAdapter.addFragment(ImageWriteFragment(), "사진")
        vpWrite.adapter = PageAdapter
        tabNav.setupWithViewPager(vpWrite)
    }

    private fun putWords() {
        viewModel.putWordsResponse().observe(this, Observer {
            if (it != null) {
                Log.d(Constants.TAG, "단어장 업데이트 성공!")
                Log.d(Constants.TAG, "response Body : $it")
            } else {
                Log.d(Constants.TAG, "단어장 put response null!")
            }
            finish()
        })

        viewModel.putWords(MainActivity.getUserData().loginToken,selectedGroupId,
            WordBook.PutWordRequestBody(MainActivity.getUserData().userID,selectedWords.toList()))
    }
}