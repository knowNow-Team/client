package com.example.konwnow.ui.view.test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.App
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.ui.adapter.FolderAdapter
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.utils.Constants
import com.example.konwnow.utils.LottieDialog
import com.example.konwnow.utils.TEST
import com.example.konwnow.viewmodel.WordBookViewModel
import com.google.gson.annotations.SerializedName


class TestFragment: Fragment() {

    companion object{
        var selectedWordBook: HashMap<String,String> = HashMap()
        private  var selectedQuizNum: Int = 0
        fun clearSelectedWordBook(){
            selectedWordBook.clear()
        }
        fun clearSelectedQuizNum(){
            selectedQuizNum = 0
        }
    }
    var folderList = arrayListOf<WordBook.WordBookData>()
    //체크된 태그
    //0: 몰라요
    //1: 헷갈려요
    //2: 알아요
    var checkedTag = arrayListOf<String>()
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
    private lateinit var viewModel: WordBookViewModel
    lateinit var dlg: LottieDialog


    private val testModeRg: RadioGroup by lazy {
        view?.findViewById(R.id.rg_test_mode) as RadioGroup
    }

    private fun showDialog() {
        dlg = LottieDialog(context!!)
        dlg.start(R.raw.checkmark)
    }

    private fun finishDialog(){
        dlg.loadingComplete()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_test, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,defaultViewModelProviderFactory).get(WordBookViewModel::class.java)
//        resetView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == TEST.TEST_START){
            if(resultCode== Constants.RESULT_OK){
                showDialog()
                finishDialog()
            }else if(resultCode== Activity.RESULT_CANCELED){
                Log.d("리절트","캔슬")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        folderList.clear()
        clearSelectedWordBook()
        clearSelectedQuizNum()
        resetView()
    }


    private fun resetView() {
        //태그, 폴더, 시크바 초기화
        setDefault()
        setTag()
        notKnowCb.isChecked=false
        confuseCb.isChecked=false
        knowCb.isChecked=false
        setFolderList()
        setStartButton()
        totalQuizNum = 0
        setSeekBar()
        setTestLogButton()
        clearSelectedWordBook()
        Log.d("초기화","호출")
    }

    private fun setFolderList() {
        //폴더 리스트 데이터
        requestGroups()
        folderListRv = v.findViewById(R.id.rv_word_folder) as RecyclerView
        folderListRv.setHasFixedSize(true)
        folderListRv.layoutManager = LinearLayoutManager(context)
        folderAdapter = FolderAdapter(folderList){ i: Int, b: Boolean ->
            if(b){
                totalQuizNum += folderList[i].allCount
            }else{
                totalQuizNum -= folderList[i].allCount
                if(selectedQuizNum > totalQuizNum){
                    selectedQuizNum = totalQuizNum
                }
            }
            quizNumSb.max = totalQuizNum
            setQuizNum()
        }
        folderListRv.adapter = folderAdapter
    }



    private fun requestGroups() {
        viewModel.getDataReponse().observe(viewLifecycleOwner, Observer {
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

    private fun setDefault() {
        //단어시험 모드 디폴트 설정
        puzzleTestButton = v.findViewById(R.id.rb_word_puzzle)
        dictationTestButton = v.findViewById(R.id.rb_test_dictation)
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
            checkedTag.add(getString(R.string.doNotKnow))
        }
        if(confuseCb.isChecked){
            checkedTag.add(getString(R.string.confused))
        }
        if(knowCb.isChecked){
            checkedTag.add(getString(R.string.memorized))
        }
        for(i: String in checkedTag){
            Log.d("선택된 태그",i)
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
//            getCheckedTestMode()
            mIntent = when(testModeRg.checkedRadioButtonId){
                R.id.rb_word_puzzle-> {
                    Intent(activity, PuzzleTestActivity::class.java)
                }
                else -> {
                    Intent(activity, DictationTestActivity::class.java)
                }
            }
            if(checkValid()){
                mIntent.putExtra("selectedFolder", selectedWordBook)
                mIntent.putExtra("selectedQuizNum", selectedQuizNum)
                Log.d("퀴즈 수", selectedQuizNum.toString())
                mIntent.putExtra("selectedTag", checkedTag)
                startActivityForResult(mIntent, TEST.TEST_START)
            }

        }
    }

    private fun checkValid():Boolean{
        //태그 체크
        getCheckedTag()
        if(selectedWordBook.isEmpty()){
            toast("단어장을 선택해주세요")
            return false
        }
        if(selectedQuizNum==0){
            toast("문제 수를 선택해주세요")
            return false
        }
        if(checkedTag.isEmpty()){
            toast("태그를 선택해주세요")
            return false
        }
        return true
    }

    private fun setTestLogButton() {
        testLogButton = v.findViewById(R.id.btn_test_log)
        testLogButton.setOnClickListener {
            //activity 전환 to TestLog
            val testLogIntent = Intent(activity, TestLogActivity::class.java)
            startActivityForResult(testLogIntent,TEST.TEST_LOG)
        }
    }

    private fun toast(message: String){ Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }

}
