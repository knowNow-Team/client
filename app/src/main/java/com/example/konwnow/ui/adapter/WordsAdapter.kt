package com.example.konwnow.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Words
import com.example.konwnow.ui.view.home.WordDialog


class WordsAdapter() : RecyclerView.Adapter<WordsAdapter.Holder>(){

    private lateinit var context : Context
    private var items = ArrayList<Words>()
    private var toggleStatus = true

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvEng = itemView?.findViewById<TextView>(R.id.tv_words_eng)
        val tvKor = itemView?.findViewById<TextView>(R.id.tv_words_kor)
        val level = itemView?.findViewById<TextView>(R.id.tv_level)
        val btnDelete = itemView?.findViewById<ImageButton>(R.id.ib_trash)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_words, parent, false)
        context = parent.context
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        //바인딩
        holder.tvEng!!.text = items[position].eng
        holder.tvKor!!.text = items[position].kor

        var levelText = holder.level!!
        when(items[position].levelStatus){
            0-> {
                levelText.setTextColor(context.getColor(R.color.red))
                levelText.text = context.getString(R.string.not_know)
            }
            1 -> {
                levelText.setTextColor(context.getColor(R.color.orange))
                levelText.text = context.getString(R.string.confuse)
            }
            2 -> {
                levelText.setTextColor(context.getColor(R.color.colorMain))
                levelText.text = context.getString(R.string.know)
            }
        }

        // 이벤트발생 시 호출되는 함수
        changeToggle(holder)
        changeLevel(levelText,position)
        showDetail(holder,position)
        deleteWord(holder,position)

    }

    private fun deleteWord(holder: WordsAdapter.Holder, position: Int) {
        holder.btnDelete?.setOnClickListener {
            Toast.makeText(context, "${position}번 아이템 삭제!", Toast.LENGTH_SHORT).show()
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }

    private fun showDetail(holder: WordsAdapter.Holder, position: Int) {
        holder.itemView.setOnClickListener {
            val dlg = WordDialog(context)
            dlg.start(items[position].eng)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    private fun changeLevel(levelText: TextView,position: Int) {
        levelText.setOnClickListener {
            when(items[position].levelStatus){
                0-> {
                    items[position].levelStatus = 1
                }
                1 -> {
                    items[position].levelStatus = 2
                }
                2 -> {
                    items[position].levelStatus = 0
                }
            }
            notifyDataSetChanged()
        }
    }

    private fun changeToggle(holder: WordsAdapter.Holder) {
        if(toggleStatus){
            //단어 보여주기 모드
            holder.tvKor!!.visibility = VISIBLE
        }else{
            holder.tvKor!!.visibility = INVISIBLE
        }
    }

    fun wordsUpdateList(wordItem: ArrayList<Words>){
        this.items.addAll(wordItem)
    }

    fun toggleUpdate(status : Boolean){
        this.toggleStatus = status
    }
}