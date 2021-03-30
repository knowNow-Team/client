package com.example.konwnow.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Words
import com.example.konwnow.ui.view.home.MyDialog


class WordsAdapter : RecyclerView.Adapter<WordsAdapter.Holder>(){

    private lateinit var context : Context
    private var items = ArrayList<Words>()

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvEng = itemView?.findViewById<TextView>(R.id.tv_words_eng)
        val tvKor = itemView?.findViewById<TextView>(R.id.tv_words_kor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_words, parent, false)
        context = parent.context
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvEng!!.text = items[position].eng
        holder.tvKor!!.text = items[position].kor

        holder.itemView.setOnClickListener {
            val dlg = MyDialog(context)
            dlg.start(items[position].eng)

        }
    }

    fun wordsUpdateList(wordItem: ArrayList<Words>){
        this.items.addAll(wordItem)
    }

}