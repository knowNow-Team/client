package com.example.konwnow.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R

class QuizAdapter(private val quizList: ArrayList<String>, mcontext: Context) : RecyclerView.Adapter<QuizAdapter.Holder>() {
    var myContext = mcontext
    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var cursor = arrayListOf<Int>()
        var totalWrote = arrayListOf<ArrayList<String>>()
        var wrote =  arrayListOf<String>()
        lateinit var spellAdapter: SpellAdapter
        var spellList = arrayListOf<String>()
        var blankList =  arrayListOf<TextView>()
        val tvQuizKor = itemView?.findViewById<TextView>(R.id.tv_puzzle_kor)
        val blankPuzzleLl = itemView?.findViewById<LinearLayout>(R.id.ll_blank)
        val spellButtonRV = itemView?.findViewById<RecyclerView>(R.id.rv_spelling)
        val tbBtnBack = itemView?.findViewById<ImageButton>(R.id.ib_puzzle_refresh)
        val btnLeft = itemView?.findViewById<ImageButton>(R.id.ib_prev)
        val btnRight = itemView?.findViewById<ImageButton>(R.id.ib_next)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quiz, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        for(i in 0 until quizList.size){
            holder.cursor.add(0)
            holder.totalWrote.add(ArrayList<String>())
        }
        holder.tvQuizKor!!.text = quizList[position]
        setBlank(holder, position)
        setButton(holder, position)
        setRefreshButton(holder, position)
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    private fun setArrowBtn(holder: Holder, position: Int){
        holder.btnLeft!!.setOnClickListener {

        }

        holder.btnRight!!.setOnClickListener {

        }
    }

    private fun setBlank(holder: Holder, position: Int) {
        var tmpString = quizList[position]

        //blank 생성
        for (j in tmpString.indices) {
            holder.blankPuzzleLl!!.addView(createBlank(holder, j))
        }
    }

    private fun createBlank(holder: Holder, order: Int): View{
        val text = TextView(myContext)
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.width = 30
        lp.height = 100
        lp.setMargins(5, 5, 5, 10)
        text.layoutParams = lp
        text.setBackground(ContextCompat.getDrawable(myContext, R.drawable.puzzle_blank))
        text.id = order
        holder.blankList.add(text)
        return text
    }

    private fun setButton(holder: Holder, position: Int) {
        //스펠링 리스트
        var tmpString = quizList[position]
        for(element in tmpString){
            holder.spellList.add(element.toString())
        }
        holder.spellList.shuffle()
        holder.spellButtonRV!!.setHasFixedSize(true)
        holder.spellButtonRV!!.layoutManager = GridLayoutManager(myContext, 5)
        holder.spellAdapter = SpellAdapter(myContext, holder.spellList, holder.totalWrote[position]){
            fillBlank(holder, position)
        }
        holder.spellButtonRV.adapter = holder.spellAdapter
    }


    private fun fillBlank(holder: Holder, position: Int){
        Log.d("클릭", "되는데")
        holder.wrote = holder.totalWrote[position]
        if(holder.wrote != null){
            Log.d("클릭", holder.cursor[position].toString())
            holder.blankList[holder.cursor[position]].text = holder.wrote[holder.cursor[position]]
            holder.cursor[position] += 1
        }
    }

    private fun setRefreshButton(holder: Holder, position: Int){
        holder.tbBtnBack!!.setOnClickListener {
            holder.spellAdapter.removeAll()
            holder.wrote = holder.totalWrote[position]
            if(holder.wrote != null){
                while(holder.cursor[position] > 0){
                    holder.blankList[--holder.cursor[position]].text = ""
                }
            }
            holder.cursor[position] = 0
            holder.wrote.clear()
            setButton(holder, position)
        }
    }


    }