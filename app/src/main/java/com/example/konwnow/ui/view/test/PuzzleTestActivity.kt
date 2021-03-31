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
import com.example.konwnow.R
import com.example.konwnow.ui.adapter.SpellAdapter
import java.util.*


class PuzzleTestActivity : AppCompatActivity() {
    var wordList =  arrayListOf<String>()
    var wrote =  arrayListOf<String>()
    var blankList =  arrayListOf<TextView>()
    var wordLength: Int = 0
    var RowCount: Int = 0
    var cursor: Int = 0
    var spellList = arrayListOf<String>()
    private lateinit var spellButtonRV: RecyclerView
    private lateinit var spellAdapter: SpellAdapter
    private lateinit var blankPuzzleLl: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_puzzle)
        setToolbar()
        blankPuzzleLl = findViewById<LinearLayout>(R.id.ll_blank)
        wordList.add("movie")
        setButton()
        setBlank()
        setRefreshButton()
    }


    private fun setBlank() {
        var tmpString = wordList.get(0)

        //blank 생성
        var currentRow = createRow(RowCount)
        RowCount++
        blankPuzzleLl.addView(currentRow)
        for (j in 0 until tmpString.length) {
            currentRow.addView(createBlank())
        }
    }

    private fun setRefreshButton(){
        val tbBtnBack = findViewById<ImageButton>(R.id.ib_puzzle_refresh)
        tbBtnBack!!.setOnClickListener {
            spellAdapter.removeAll()
            if(wrote.size > 0){
                while(cursor > 0){
                    blankList.get(--cursor).text = ""
                }
            }
            cursor = 0
            wrote.clear()
            setButton()
        }
    }

    private fun fillBlank(){
        Log.d("wrote", wrote.get(0))
        if(wrote.size >= 0){
            blankList.get(cursor).text = wrote.get(cursor)
            cursor++
        }
    }

    private fun setButton() {
        //스펠링 리스트
        var tmpString = wordList.get(0)
        for(i in 0 until tmpString.length){
            spellList.add(tmpString.get(i).toString())
        }
        spellList.shuffle()
        spellButtonRV = findViewById<RecyclerView>(R.id.rv_spelling)
        spellButtonRV.setHasFixedSize(true)
        spellButtonRV.layoutManager = GridLayoutManager(this, 5)
        spellAdapter = SpellAdapter(this, spellList, wrote){
            fillBlank()
        }
        spellButtonRV.adapter = spellAdapter
    }

    private fun setToolbar() {
        val tbBtnBack = findViewById<ImageButton>(R.id.ib_close)
        tbBtnBack!!.setOnClickListener {
            finish()
        }
    }

    private fun createBlank(): View{
        val text = TextView(this)
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.width = 30
        lp.height = 100
        lp.setMargins(5, 5, 5, 10)
        blankList.add(text)
        text.layoutParams = lp
        text.setBackground(ContextCompat.getDrawable(this, R.drawable.puzzle_blank))
        text.id = wordLength++
        Log.d("아이디", text.id.toString())
        return text
    }

    private fun createRow(rowCount: Int): LinearLayout{
        val ll = LinearLayout(this)
        ll.orientation = LinearLayout.HORIZONTAL
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        ll.layoutParams = params
        return ll
    }



    private fun toast(message: String){ Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }

}