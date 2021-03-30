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


class PuzzleTestActivity : AppCompatActivity() {
    var wordLength: Int = 0
    var RowCount: Int = 0
    var spellList = arrayListOf<String>()
    private lateinit var spellButtonRV: RecyclerView
    private lateinit var blankPuzzleLl: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_puzzle)
        setToolbar()
        blankPuzzleLl = findViewById<LinearLayout>(R.id.ll_blank)
        setButton()

        //blank 생성
        for(i in 0 until 2){
            var currentRow = createRow(RowCount)
            RowCount++
            blankPuzzleLl.addView(currentRow)
            for (j in 0 until 7) {
                currentRow.addView(createBlank())
            }
        }

    }

    private fun setButton() {
        //스펠링 리스트
        spellList.add("A")
        spellList.add("B")
        spellList.add("C")
        spellList.add("D")
        spellList.add("E")
        spellList.add("F")
        spellList.add("G")
        spellList.add("H")

        spellButtonRV = findViewById<RecyclerView>(R.id.rv_spelling)
        spellButtonRV.setHasFixedSize(true)
        spellButtonRV.layoutManager = GridLayoutManager(this, 4)
        spellButtonRV.adapter = SpellAdapter(this, spellList){
            toast("클릭")
        }
    }

    private fun setToolbar() {
        val tbTestPuzzle = findViewById<View>(R.id.tb_test_puzzle)
        val tbTitle = tbTestPuzzle.findViewById<TextView>(R.id.tv_title)
        val tbBtnBack = tbTestPuzzle.findViewById<ImageButton>(R.id.ib_back)

        tbTitle.setText(R.string.test_log_title)
        tbBtnBack!!.setOnClickListener {
            finish()
        }
    }

    private fun createBlank(): View{
        val text = TextView(this)
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.width = 100
        lp.height = 100
        lp.setMargins(5, 5, 5, 10)
        text.layoutParams = lp
        text.setBackground(ContextCompat.getDrawable(this, R.drawable.puzzle_blank))
        text.setOnClickListener {
            Toast.makeText(this, "${text.id}번째 버튼입니다.", Toast.LENGTH_SHORT).show()
        }
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