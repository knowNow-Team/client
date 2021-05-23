package com.example.konwnow.ui.view.test

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Quiz
import com.example.konwnow.data.remote.dto.TestLog
import com.example.konwnow.data.remote.dto.Words
import com.example.konwnow.ui.adapter.DictationAdapter
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.utils.Constants
import com.example.konwnow.viewmodel.TestLogViewModel


class DictationTestActivity : AppCompatActivity() {
    var wordsList =  arrayListOf<Words>()
    lateinit var quizNum:String
    private lateinit var quizVP: ViewPager2
    private lateinit var dictationAdapter: DictationAdapter
    private lateinit var filters : List<String>
    private lateinit var wordbooks : List<String>
    private lateinit var quizlog : MutableList<Quiz.TotalQuiz>
    private lateinit var viewModel : TestLogViewModel

    var point = 0
    var totalScore = 0
    var correct = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_dictation)
        setToolbar()

        wordsList.clear()
        wordsList.add(Words("Complex", "복잡한",0))
        wordsList.add(Words("movie", "영화관",1))
        wordsList.add(Words("Fragment", "조각",2))
        wordsList.add(Words("apple", "복잡한",1))
        wordsList.add(Words("banana", "영화관",2))
        wordsList.add(Words("carrot", "조각",1))
        wordsList.add(Words("hello", "복잡한",0))
        wordsList.add(Words("green", "영화관",1))
        wordsList.add(Words("hoxy", "조각",0))
        filters = listOf("memorized","confused")
        wordbooks = listOf("60a3ca4f25ac7300576e8c00")
        setQuiz()

//        var imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//	    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        //문제수 표시 스트링
        setQuizNum()
    }

    private fun setToolbar() {
        val tbBtnBack = findViewById<ImageButton>(R.id.ib_close)
        tbBtnBack!!.setOnClickListener {
            val dlg: AlertDialog.Builder = AlertDialog.Builder(this,  android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
            dlg.setTitle(R.string.close)
            dlg.setMessage(R.string.closeSub)
            dlg.setPositiveButton("네", DialogInterface.OnClickListener { dialog, which ->
                finish()
            })
            dlg.setNegativeButton("아니요") { dialog, which ->
            }
            dlg.show()
        }
    }

    override fun onBackPressed() {
        val dlg: AlertDialog.Builder = AlertDialog.Builder(this,  android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
        dlg.setTitle(R.string.close)
        dlg.setMessage(R.string.closeSub)
        dlg.setPositiveButton("네", DialogInterface.OnClickListener { dialog, which ->
            finish()
        })
        dlg.setNegativeButton("아니요") { dialog, which ->
        }
        dlg.show()
    }

    private fun setQuiz(){
        dictationAdapter = DictationAdapter(){
            goNext(it)
        }
        quizVP = findViewById(R.id.vp_dictation)
        quizVP.isUserInputEnabled = false
        dictationAdapter.wordsUpdateList(wordsList)
        quizVP.adapter = dictationAdapter
    }

    private fun setQuizNum(){
        quizNum = String.format(resources.getString(R.string.quizNum),(quizVP.currentItem+1), wordsList.size)
        val tvQuizNum = findViewById<TextView>(R.id.tv_quiz_num)
        tvQuizNum.text = quizNum
    }


    private fun goNext(answer: ArrayList<String>) {
        point = 100 / wordsList.size as Int
        totalScore = 0
        correct = 0
        if (quizVP.currentItem == wordsList.size-1) {
            quizlog = mutableListOf()

            for(i in wordsList.indices){
                var target = wordsList[i].eng
                var kor = wordsList[i].kor
                var userAnswer = answer[i]
                var hit = true

                if(target != userAnswer){
                    hit = false
                }
                if(hit){
                    correct++
                    totalScore += point
                }
//                quizLog.add(Quiz(target!!,kor!!,userAnswer,hit))
                quizlog.add(Quiz.TotalQuiz(wordId = "60a3e24bd3faa00058331b2b",isCorrect = hit,answer = userAnswer))

            }

            if(correct == wordsList.size){
                totalScore=100
            }

//            toast(getString(R.string.lastPage))
            postTestLog()
            finish()

        } else {
            quizVP.currentItem = quizVP.currentItem + 1
            setQuizNum()
        }
    }


    private fun postTestLog() {
        viewModel = ViewModelProvider(this,defaultViewModelProviderFactory).get(TestLogViewModel::class.java)
        viewModel.getTestCreateResponseObserver().observe(this, Observer<TestLog.TestCreateResponse>{
            Log.d(Constants.TAG,"Response : $it")
            if(it != null) {
                Log.d("로그 생성","성공")
            }else{
                Log.d("로그 생성","실패")
            }
        })
        viewModel.postTestLog(MainActivity.getUserData().loginToken,correct, "hard",filters,totalScore,MainActivity.getUserData().userID,wordsList.size,wordbooks,quizlog.toList())
    }



    private fun toast(message:String){ Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }

}