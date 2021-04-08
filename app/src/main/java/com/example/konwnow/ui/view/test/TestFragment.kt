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
    //체크된 태그
    //0: 몰라요
    //1: 헷갈려요
    //2: 알아요
    var checkedTag = arrayListOf<Int>()
    private lateinit var v: View
    private lateinit var puzzleTestButton: RadioButton
    private lateinit var dictationTestButton: RadioButton
    private lateinit var folderListRv: RecyclerView
    private lateinit var startButton: Button
    private lateinit var testLogButton: ImageButton
    private lateinit var quizNumTv: TextView
    private lateinit var quizNumSb: SeekBar
    private lateinit var notKnowCb: CheckBox
    private lateinit var confuseCb: CheckBox
    private lateinit var knowCb: CheckBox
    private lateinit var folderAdapter: FolderAdapter
    private var totalQuizNum: Int = 0
    private  var selectedQuizNum: Int = 0

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
        setTag()
        setStartButton()
        setRadioGroup()
        setTestLogButton()
        setSeekBar()
    }

    private fun setFolderList() {
        //폴더 리스트 데이터
        folderList.add(Folder("name1",4))
        folderList.add(Folder("name2",20))
        folderList.add(Folder("name3",30))
        folderList.add(Folder("name4",2))
        folderList.add(Folder("name5",12))

        folderListRv = v.findViewById(R.id.rv_word_folder) as RecyclerView
        folderListRv.setHasFixedSize(true)
        folderListRv.layoutManager = LinearLayoutManager(context)
        folderAdapter = FolderAdapter(){ i: Int, b: Boolean ->
            if(b){
                totalQuizNum += folderList[i].wordsCount
            }else{
                totalQuizNum -= folderList[i].wordsCount
                if(selectedQuizNum > totalQuizNum){
                    selectedQuizNum = totalQuizNum
                }
            }
            quizNumSb.max = totalQuizNum
            setQuizNum()
        }
        folderAdapter.folderUpdateList(folderList)
        folderListRv.adapter = folderAdapter
    }

    private fun setDefault() {
        //단어시험 모드 디폴트 설정
        puzzleTestButton = v.findViewById(R.id.rb_word_puzzle)
        puzzleTestButton.isChecked = true

        //seekbar 퀴즈 수 설정
        quizNumTv = v.findViewById(R.id.tv_quiz_num)
        setQuizNum()
    }

    private fun setQuizNum(){
        quizNumTv!!.text = String.format(resources.getString(R.string.quizNum),selectedQuizNum, totalQuizNum)
    }

    private fun setTag(){
        notKnowCb = v.findViewById(R.id.not_know)
        confuseCb = v.findViewById(R.id.confuse)
        knowCb = v.findViewById(R.id.know)
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


    private fun setSeekBar(){
        quizNumSb = v.findViewById(R.id.sb_quiz_num)
        quizNumSb.max = totalQuizNum
        quizNumSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                selectedQuizNum = progress
                setQuizNum()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
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
            for(item in folderAdapter.checkedFolder){
                Log.d("선택된 폴더", item.name)
            }
            //문제수 체크
//            Log.d("선택", selectedQuizNum.toString())
//            Log.d("전체",totalQuizNum.toString())

            //태그 체크
            getCheckedTag()
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
