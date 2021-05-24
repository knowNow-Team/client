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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Quiz
import com.example.konwnow.data.remote.dto.TestLog
import com.example.konwnow.data.remote.dto.WordId
import com.example.konwnow.ui.adapter.DictationAdapter
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.utils.Constants
import com.example.konwnow.viewmodel.TestLogViewModel
import com.example.konwnow.viewmodel.WordBookViewModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class DictationTestActivity : AppCompatActivity() {
    var wordsList =  arrayListOf<WordId>()
    var quizNum = 0
    private lateinit var quizVP: ViewPager2
    private lateinit var dictationAdapter: DictationAdapter
    private lateinit var filters : List<String>
    private lateinit var wordbooks : HashMap<String,String>
    private lateinit var wordbookTitleList : List<String>
    private lateinit var wordbookIdList : List<String>
    private lateinit var quizlog : MutableList<Quiz.TotalQuiz>
    private lateinit var viewModel : TestLogViewModel
    private lateinit var workBookViewModel: WordBookViewModel

    var point = 0
    var totalScore = 0
    var correct = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_dictation)
        setToolbar()
        printInent(intent)

        wordsList.clear()

        wordbooks = intent.extras!!.get("selectedFolder") as HashMap<String, String>
        quizNum = intent.extras!!.get("selectedQuizNum") as Int
        filters = intent.extras!!.get("selectedTag") as List<String>

        wordbookIdList = wordbooks.keys.toList()
        wordbookTitleList = wordbooks.values.toList()
        requestAllWord()
        setQuiz()


//        var imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//	    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        //문제수 표시 스트링
        setQuizNum()
    }

    fun printInent(i: Intent) {
        try {
            Log.d(Constants.TAG,"-------------------------------------------------------")
            Log.d(Constants.TAG,"intent = " + i)
            if (i != null) {
                var extras = i.extras
                Log.d(Constants.TAG,"extras = " + extras);
                if (extras != null) {
                    var keys = extras.keySet()
                    Log.d(Constants.TAG,"++ bundle key count = " + keys.size)

                    for (_key in keys) {
                        Log.d(Constants.TAG,"key=" + _key + " : " + extras.get(_key))
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(Constants.TAG,e.toString())
        } finally {
            Log.d(Constants.TAG,"-------------------------------------------------------")
        }
    }



    private fun getRandom(totalSize: Int, selectSize:Int): TreeSet<Int> {
        var set: TreeSet<Int> = TreeSet()
        while(set.size < selectSize){
            val random = Random()
            val num = random.nextInt(totalSize)
            set.add(num)
        }
        return set
    }

    private fun requestAllWord() {
        workBookViewModel = ViewModelProvider(this,defaultViewModelProviderFactory).get(WordBookViewModel::class.java)
        workBookViewModel.getWordDataResponse().observe(this, Observer {
            if (it != null){
                Log.d(Constants.TAG,"단어 가져오기 성공!")
                Log.d(Constants.TAG,"response Body : ${it}")
                var allWord = ArrayList<WordId>()

                for (item in it.data){
                    if(!item.words.isRemoved && item.words.filter in filters){
                        var tempWord = item.wordsDoc
                        for(word in tempWord){
                            allWord.add(word)
                        }
                    }
                }
                val quizIndexSet = getRandom(allWord.size,quizNum)

                for(i in quizIndexSet){
                    wordsList.add(allWord[i])
                }
                Log.d(Constants.TAG,"wordList: ${wordsList}")
            }else {
                Log.d(Constants.TAG,"단어장 get response null!")
            }
//            puzzleAdapter.wordsUpdateList(wordsList)
            dictationAdapter.notifyDataSetChanged()
        })
        workBookViewModel.getAllWord(MainActivity.getUserData().loginToken,wordbookIdList)
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
        dictationAdapter = DictationAdapter(wordsList){
            goNext(it)
        }
        quizVP = findViewById(R.id.vp_dictation)
        quizVP.isUserInputEnabled = false
//        dictationAdapter.wordsUpdateList(wordsList)
        quizVP.adapter = dictationAdapter
    }

    private fun setQuizNum(){
        val tvQuizNum = findViewById<TextView>(R.id.tv_quiz_num)
        tvQuizNum.text = String.format(resources.getString(R.string.quizNum),(quizVP.currentItem+1), quizNum)
    }


    private fun goNext(answer: ArrayList<String>) {
        point = 100 / wordsList.size as Int
        totalScore = 0
        correct = 0
        if (quizVP.currentItem == wordsList.size-1) {
            quizlog = mutableListOf()

            for(i in wordsList.indices){
                var target = wordsList[i].word
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
                quizlog.add(Quiz.TotalQuiz(wordId = wordsList[i].id,isCorrect = hit,answer = userAnswer))

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
        viewModel.postTestLog(MainActivity.getUserData().loginToken,correct, "hard",filters,totalScore,MainActivity.getUserData().userID,wordsList.size,wordbookTitleList,quizlog.toList())
    }



    private fun toast(message:String){ Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }

}