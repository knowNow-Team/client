package com.example.konwnow.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Words
import com.example.konwnow.ui.view.home.WordDialog


class WordsAdapter : RecyclerView.Adapter<WordsAdapter.Holder>(){

    private lateinit var context : Context
    private var items = ArrayList<Words>()


    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvEng = itemView?.findViewById<TextView>(R.id.tv_words_eng)
        val tvKor = itemView?.findViewById<TextView>(R.id.tv_words_kor)
        val level = itemView?.findViewById<TextView>(R.id.tv_level)
        val swipeView = itemView?.findViewById<LinearLayout>(R.id.ll_word_swipe)
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
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val No = context.getString(R.string.not_know)
        val Umm = context.getString(R.string.confuse)
        val Yes = context.getString(R.string.know)
        holder.tvEng!!.text = items[position].eng
        holder.tvKor!!.text = items[position].kor

        var levelText = holder.level!!
        when(levelText.text){
            No-> {
                levelText.setTextColor(context.getColor(R.color.red))
            }
            Umm -> {
                levelText.setTextColor(context.getColor(R.color.orange))

            }
            Yes -> {
                levelText.setTextColor(context.getColor(R.color.colorMain))
            }
        }

        levelText.setOnClickListener {
            Log.d("눌렸음",levelText.text.toString())
            when(levelText.text){
                No-> {
                    levelText.setTextColor(context.getColor(R.color.orange))
                    levelText.text = Umm
                }
                Umm -> {
                    levelText.setTextColor(context.getColor(R.color.colorMain))
                    levelText.text = Yes

                }
                Yes -> {
                    levelText.setTextColor(context.getColor(R.color.red))
                    levelText.text = No

                }
            }
        }

        holder.itemView.setOnClickListener {
            val dlg = WordDialog(context)
            dlg.start(items[position].eng)
        }
    }

    fun wordsUpdateList(wordItem: ArrayList<Words>){
        this.items.addAll(wordItem)
    }

}