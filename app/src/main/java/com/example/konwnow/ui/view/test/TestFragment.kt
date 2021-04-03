package com.example.konwnow.ui.view.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Folder
import com.example.konwnow.ui.adapter.FolderAdapter


class TestFragment: Fragment() {
    var folderList = arrayListOf<Folder>()
    private lateinit var v: View
    private lateinit var puzzleTestButton: RadioButton
    private lateinit var dictationTestButton: RadioButton
    private lateinit var folderListRv: RecyclerView
    private lateinit var startButton: Button
    private lateinit var testLogButton: ImageButton
    //0: 단어퍼즐
    //1: 단어 받아쓰기
    private var testMode: Int =0

    private val testModeRg: RadioGroup by lazy {
        view?.findViewById(R.id.rg_test_mode) as RadioGroup
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_test, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setDefault()
        setFolderList()
        setStartButton()
        setRadioGroup()
        setTestLogButton()
    }

    private fun setFolderList() {
        //폴더 리스트 데이터
        folderList.add(Folder("name1"))
        folderList.add(Folder("name2"))
        folderList.add(Folder("name3"))
        folderList.add(Folder("name4"))
        folderList.add(Folder("name5"))

        folderListRv = v.findViewById(R.id.rv_word_folder) as RecyclerView
        folderListRv.setHasFixedSize(true)
        folderListRv.layoutManager = LinearLayoutManager(context)
        folderListRv.adapter = FolderAdapter(folderList)
    }

    private fun setDefault() {
        puzzleTestButton = v.findViewById(R.id.rb_word_puzzle)
        puzzleTestButton.isChecked = true

    }

    private fun setStartButton() {
        startButton = v.findViewById(R.id.btn_test_start)
        var mIntent : Intent
        startButton.setOnClickListener {
            //테스트 모드 체크
            getCheckedTestMode()
            when(testMode){
                0 -> {
                    mIntent = Intent(activity, PuzzleTestActivity::class.java)
                }
                1 -> {
                    mIntent = Intent(activity, DictationTestActivity::class.java)
                }
                else -> {
                    mIntent = Intent(activity, PuzzleTestActivity::class.java)
                }
            }
            //폴더 리스트체크
            //태그 체크
            //문제수 체크
            startActivity(mIntent)
        }
    }

    private fun setTestLogButton() {
        testLogButton = v.findViewById(R.id.btn_test_log)
        testLogButton.setOnClickListener {
            //activity 전환 to TestLog
            val testLogIntent = Intent(activity, TestLogActivity::class.java)
            startActivity(testLogIntent)
        }
    }

    private fun setRadioGroup() {
        testModeRg.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            if(group.id == R.id.rg_test_mode){
                when(checkedId){
                    R.id.rb_word_puzzle -> testMode=0
                    R.id.rb_test_dictation -> testMode=1
                }
            }
        })
    }

    private fun getCheckedTestMode() {
        var selectedTestMode = testModeRg.checkedRadioButtonId
        when(selectedTestMode){
            R.id.rb_word_puzzle -> {
                testMode=0
            }
            R.id.rb_test_dictation -> {
                testMode=1
            }
        }
    }


}
