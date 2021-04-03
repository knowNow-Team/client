  package com.example.konwnow.ui.adapter

import android.content.Context
import android.service.voice.AlwaysOnHotwordDetector
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Words

class QuizAdapter(val itemClick: (ArrayList<ArrayList<String>>) -> Unit) : RecyclerView.Adapter<QuizAdapter.Holder>() {
    lateinit var myContext: Context
    private var quizList = ArrayList<Words>()

    var cursor = arrayListOf<Int>()
    lateinit var mHloder:Holder
    var mPosition = 0
    var totalWrote = arrayListOf<ArrayList<String>>()
    var totalBlank = arrayListOf<ArrayList<TextView>>()


    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvQuizKor = itemView?.findViewById<TextView>(R.id.tv_puzzle_kor)
        val blankPuzzleLl = itemView?.findViewById<LinearLayout>(R.id.ll_blank)
        val spellButtonRV = itemView?.findViewById<RecyclerView>(R.id.rv_spelling)
        val tbBtnBack = itemView?.findViewById<ImageButton>(R.id.ib_puzzle_refresh)
        val btnSubmit = itemView?.findViewById<Button>(R.id.btn_submit)
        lateinit var spellAdapter: SpellAdapter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quiz, parent, false)
        myContext = parent.context
        mHloder = Holder(view)
        return mHloder
    }

    override fun onBindViewHolder(holder: Holder, position: Int){
        Log.d("포지션",position.toString())
        holder.tvQuizKor!!.text = quizList[position].eng
        cursor.add(0)
        totalBlank.add(ArrayList<TextView>())
        totalWrote.add(ArrayList<String>())
        setSubmitBtn()
        setBlank(holder, position)
        setButton(holder, position)
        setRefreshButton(holder, position)
    }


    override fun getItemCount(): Int {
        return quizList.size
    }

    private fun setSubmitBtn(){
        mHloder.btnSubmit!!.setOnClickListener{
            itemClick(totalWrote)
        }
    }


    private fun setBlank(holder: Holder, position: Int) {
        var tmpString = quizList[position].eng
        //reset
        totalBlank[position].clear()
        holder.blankPuzzleLl!!.removeAllViews()
        //blank 생성
        for (i in tmpString.indices) {
            var newBlank = createBlank(i,position)
            totalBlank[position].add(newBlank)
            holder.blankPuzzleLl!!.addView(newBlank)
        }
    }

    private fun createBlank(order: Int, position: Int): TextView{
        val tvNew = TextView(myContext)
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.width = 30
        lp.height = 100
        lp.setMargins(5, 5, 5, 10)
        tvNew.layoutParams = lp
        tvNew.setBackground(ContextCompat.getDrawable(myContext, R.drawable.puzzle_blank))
        tvNew.id = position * 10 + order

        return tvNew
    }

    private fun setButton(holder: Holder, position: Int) {
        //스펠링 리스트
        var tmpString = quizList[position].eng
        var spellList = arrayListOf<String>()

        for(element in tmpString){
            spellList.add(element.toString())
        }
        Log.d("문제 스트링",spellList.toString())

        spellList.shuffle()
        holder.spellButtonRV!!.setHasFixedSize(true)
        holder.spellButtonRV!!.layoutManager = GridLayoutManager(myContext, 5)
        holder.spellAdapter = SpellAdapter(){
            fillBlank(holder, position)
        }
        holder.spellAdapter.removeAll()
        holder.spellAdapter.wordsUpdateList(spellList, totalWrote[position])
        holder.spellButtonRV.adapter = holder.spellAdapter
    }



    private fun fillBlank(holder: Holder, position: Int){
        var wrote = totalWrote[position]
        var currentCursor = cursor[position]
        Log.d("현재 커서",currentCursor.toString())
        if(wrote != null){
            var blankList = totalBlank[position]
            blankList[currentCursor].text = wrote[currentCursor]
            cursor[position] = currentCursor + 1
        }
    }

    private fun setRefreshButton(holder: Holder, position: Int){
        holder.tbBtnBack!!.setOnClickListener {
            var currentCursor = cursor[position]
            Log.d("(리프레시)포지션",position.toString())
            Log.d("(리프레시)현재 커서",currentCursor.toString())
            holder.spellAdapter.removeAll()
            var wrote = totalWrote[position]
            if(wrote != null){
                while(currentCursor > 0){
                    totalBlank[position][--currentCursor].text = ""
                }
            }
            cursor[position] = 0
            wrote.clear()
            setButton(holder, position)
        }
    }

    fun wordsUpdateList(quizItem: ArrayList<Words>){
        this.quizList.addAll(quizItem)
    }


}