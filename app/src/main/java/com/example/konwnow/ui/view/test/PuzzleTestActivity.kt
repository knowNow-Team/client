package com.example.konwnow.ui.view.test

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Quiz
import com.example.konwnow.data.remote.dto.TestLog
import com.example.konwnow.data.remote.dto.Words
import com.example.konwnow.ui.adapter.PuzzleAdapter
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.utils.Constants
import com.example.konwnow.viewmodel.TestLogViewModel
import com.example.konwnow.viewmodel.WordBookViewModel
import java.util.*
import kotlin.collections.ArrayList


class PuzzleTestActivity : AppCompatActivity() {
    var wordsList =  arrayListOf<Words.Word>()
    private var quizNum:Int =0
    private lateinit var quizVP: ViewPager2
    private lateinit var puzzleAdapter: PuzzleAdapter
    private lateinit var testLogViewModel : TestLogViewModel
    private lateinit var workBookViewModel: WordBookViewModel
    private lateinit var filters : List<String>
    private lateinit var wordbooks : HashMap<String, String>
    private lateinit var wordbookTitleList : List<String>
    private lateinit var wordbookIdList : List<String>
    private lateinit var quizlog : MutableList<Quiz.TotalQuiz>
    var point = 0
    var totalScore = 0
    var correct = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_puzzle)
        setToolbar()
        wordsList.clear()

//        printInent(intent)
        wordbooks = intent.extras!!.get("selectedFolder") as HashMap<String, String>
        quizNum = intent.extras!!.get("selectedQuizNum") as Int
        filters = intent.extras!!.get("selectedTag") as List<String>

        wordbookIdList = wordbooks.keys.toList()
        wordbookTitleList = wordbooks.values.toList()
        requestAllWord()
        setQuiz()

        //????????? ?????? ?????????
        setQuizNum()

    }

    private fun getRandom(totalSize: Int, selectSize: Int): TreeSet<Int>{
        var set: TreeSet<Int> = TreeSet()
        while(set.size < selectSize){
            val random = Random()
            val num = random.nextInt(totalSize)
            set.add(num)
        }
        return set
    }

    private fun requestAllWord() {
        workBookViewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(
            WordBookViewModel::class.java
        )
        workBookViewModel.getWordDataResponse().observe(this, Observer {
            if (it != null) {
                Log.d(Constants.TAG, "?????? ???????????? ??????!")
                Log.d(Constants.TAG, "response Body : ${it}")
                var allWord = ArrayList<Words.Word>()
                allWord.clear()
                for (item in it.data) {
                    if (!item.words.isRemoved && item.words.filter in filters) {
                        var tempWord = item.wordsDoc
                        for (word in tempWord) {
                            allWord.add(word)
                        }
                    }
                }
                Log.d("???????????????",allWord.toString())

                if (allWord.isEmpty()) {
                    Log.d("???????????????", allWord.toString())
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("??????????????? ????????????.").setMessage("????????? ??????????????????")
                    builder.setNeutralButton("??????") { dialogInterface: DialogInterface, i: Int ->
                        finish()
                    }
                    val alertDialog = builder.create()
                    alertDialog.setOnShowListener {
                        alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(ContextCompat.getColor(applicationContext,R.color.colorMain))
                    }
                    alertDialog.show()
                }else{
                    val quizIndexSet = getRandom(allWord.size, quizNum)
                    for (i in quizIndexSet) {
                        wordsList.add(allWord[i])
                    }
                    Log.d(Constants.TAG, "wordList: ${wordsList}")
                }
            } else {
                Log.d(Constants.TAG, "????????? get response null!")
            }
            puzzleAdapter.notifyDataSetChanged()
        })
        workBookViewModel.getAllWord(MainActivity.getUserData().loginToken, wordbookIdList.joinToString(","))
    }

    fun printInent(i: Intent) {
        try {
            Log.d(Constants.TAG, "-------------------------------------------------------")
            Log.d(Constants.TAG, "intent = " + i)
            if (i != null) {
                var extras = i.extras
                Log.d(Constants.TAG, "extras = " + extras);
                if (extras != null) {
                    var keys = extras.keySet()
                    Log.d(Constants.TAG, "++ bundle key count = " + keys.size)

                    for (_key in keys) {
                        Log.d(Constants.TAG, "key=" + _key + " : " + extras.get(_key))
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(Constants.TAG, e.toString())
        } finally {
            Log.d(Constants.TAG, "-------------------------------------------------------")
        }
    }



    override fun onBackPressed() {
        val dlg: AlertDialog.Builder = AlertDialog.Builder(
            this,
            android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth
        )
        dlg.setTitle(R.string.close)
        dlg.setMessage(R.string.closeSub)
        dlg.setPositiveButton("???", DialogInterface.OnClickListener { dialog, which ->
            setResult(Constants.RESULT_CANCEL)
            finish()
        })
        dlg.setNegativeButton("?????????") { dialog, which ->
        }
        dlg.show()
    }

    private fun setQuiz(){
        puzzleAdapter = PuzzleAdapter(wordsList){
            goNext(it)
        }
        quizVP = findViewById(R.id.vp_puzzle)
        quizVP.isUserInputEnabled = false
//        puzzleAdapter.wordsUpdateList(wordsList)
        quizVP.adapter = puzzleAdapter
    }

    private fun setToolbar() {
        val tbBtnBack = findViewById<ImageButton>(R.id.ib_close)
        tbBtnBack!!.setOnClickListener {
            val dlg: AlertDialog.Builder = AlertDialog.Builder(
                this,
                android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth
            )
            dlg.setTitle(R.string.close)
            dlg.setMessage(R.string.closeSub)
            dlg.setPositiveButton("???", DialogInterface.OnClickListener { dialog, which ->
                finish()
            })
            dlg.setNegativeButton("?????????") { dialog, which ->
            }
            dlg.show()
        }
    }

    private fun setQuizNum(){
        val tvQuizNum = findViewById<TextView>(R.id.tv_quiz_num)
        tvQuizNum.text =  String.format(
            resources.getString(R.string.quizNum),
            (quizVP.currentItem + 1),
            quizNum
        )
    }


    private fun goNext(answer: ArrayList<ArrayList<String>>) {
        point = 100 / wordsList.size as Int
        totalScore = 0
        correct = 0
        quizlog = mutableListOf()
        if (quizVP.currentItem == wordsList.size-1) {
            for(i in wordsList.indices){
                var target = wordsList[i].word
                var userAnswer = answer[i]
                var strTmp = ""
                var hit = true
                for(i in userAnswer.indices){
                    strTmp += userAnswer[i]
                }
                if(target != strTmp){
                    hit = false
                }
                if(hit){
                    correct++
                    totalScore += point
                }
                quizlog.add(
                    Quiz.TotalQuiz(
                        wordId = wordsList[i].id,
                        isCorrect = hit,
                        answer = strTmp
                    )
                )
            }
            if(correct == wordsList.size){
                totalScore=100
            }

            //post
            postTestLog()
            setResult(Constants.RESULT_OK)
//            val dlg: AlertDialog.Builder = AlertDialog.Builder(
//                this,
//                android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth
//            )
//            dlg.setTitle("????????? ?????????????????????.")
////            dlg.setMessage(R.string.closeSub)
//            dlg.setNeutralButton("??????", DialogInterface.OnClickListener { dialog, which ->
//                finish()
//            })
//            dlg.show()
            finish()
        } else {
                quizVP.currentItem = quizVP.currentItem + 1
                setQuizNum()
            }
    }

    private fun postTestLog() {
        testLogViewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(
            TestLogViewModel::class.java
        )
        testLogViewModel.getTestCreateResponseObserver().observe(
            this,
            Observer<TestLog.TestCreateResponse> {
                Log.d(Constants.TAG, "Response : $it")
                if (it != null) {
                    Log.d("?????? ??????", "??????")
                } else {
                    Log.d("?????? ??????", "??????")
                }
            })
        testLogViewModel.postTestLog(
            MainActivity.getUserData().loginToken,
            correct,
            "hard",
            filters,
            totalScore,
            MainActivity.getUserData().userID,
            wordsList.size,
            wordbookTitleList,
            quizlog.toList()
        )
    }


    private fun toast(message: String){ Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }

}