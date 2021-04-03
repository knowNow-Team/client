package com.example.konwnow.ui.view.test

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Quiz
import com.example.konwnow.data.model.dto.Words
import com.example.konwnow.ui.adapter.PuzzleAdapter
import kotlin.collections.ArrayList


class PuzzleTestActivity : AppCompatActivity() {
    var wordsList =  arrayListOf<Words>()
    lateinit var quizNum:String
    private lateinit var quizVP: ViewPager2
    private lateinit var puzzleAdapter: PuzzleAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_puzzle)
        setToolbar()
        wordsList.clear()
        wordsList.add(Words("Complex", "복잡한"))
        wordsList.add(Words("movie", "영화관"))
        wordsList.add(Words("Fragment", "조각"))
        wordsList.add(Words("apple", "복잡한"))
        wordsList.add(Words("banana", "영화관"))
        wordsList.add(Words("carrot", "조각"))
        wordsList.add(Words("hello", "복잡한"))
        wordsList.add(Words("green", "영화관"))
        wordsList.add(Words("hoxy", "조각"))
        setQuiz()

        //문제수 표시 스트링
        setQuizNum()

    }

    private fun setQuiz(){
        puzzleAdapter = PuzzleAdapter(){
            goNext(it)
        }
        quizVP = findViewById(R.id.vp_puzzle)
        quizVP.isUserInputEnabled = false
        puzzleAdapter.wordsUpdateList(wordsList)
        quizVP.adapter = puzzleAdapter
    }

    private fun setToolbar() {
        val tbBtnBack = findViewById<ImageButton>(R.id.ib_close)
        tbBtnBack!!.setOnClickListener {
            finish()
        }
    }

    private fun setQuizNum(){
        quizNum = String.format(resources.getString(R.string.quizNum),(quizVP.currentItem+1), wordsList.size)
        val tvQuizNum = findViewById<TextView>(R.id.tv_quiz_num)
        tvQuizNum.text = quizNum
    }

    private fun goNext(answer: ArrayList<ArrayList<String>>) {
        if (quizVP.currentItem == wordsList.size-1) {
            val quizlog = ArrayList<Quiz>()
            for(i in wordsList.indices){
                var target = wordsList[i].eng
                var userAnswer = answer[i]
                var strTmp = ""
                var hit = true
                for(i in userAnswer.indices){
                    strTmp += userAnswer[i]
                }
                if(target != strTmp){
                    hit = false
                }
                quizlog.add(Quiz(target,strTmp,hit))
            }

            toast(getString(R.string.lastPage))
        } else {
                quizVP.currentItem = quizVP.currentItem + 1
                setQuizNum()
            }
    }



    private fun toast(message: String){ Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }

}