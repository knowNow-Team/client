package com.example.konwnow.ui.view.test

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.konwnow.R
import com.example.konwnow.ui.adapter.QuizAdapter
import com.example.konwnow.ui.adapter.SpellAdapter
import java.util.*


class PuzzleTestActivity : AppCompatActivity() {
    var wordList =  arrayListOf<String>()
    private lateinit var quizVP: ViewPager2
    private lateinit var quizAdapter: QuizAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_puzzle)
        setToolbar()
        setQuiz()
        wordList.add("movie1")
        wordList.add("movie2")
        wordList.add("movie3")
        wordList.add("movie4")
        wordList.add("movie5")
        wordList.add("movie6")
        wordList.add("movie7")
        wordList.add("movie8")
    }

    private fun setQuiz(){
        quizAdapter = QuizAdapter(wordList, this)
        quizVP = findViewById(R.id.vp_quiz)
        quizVP.adapter = quizAdapter
    }

    private fun setToolbar() {
        val tbBtnBack = findViewById<ImageButton>(R.id.ib_close)
        tbBtnBack!!.setOnClickListener {
            finish()
        }
    }


    private fun toast(message: String){ Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }

}