package com.example.konwnow.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Words
import com.example.konwnow.ui.view.write.WriteActivity

class WordListAdapter(var wordlist: ArrayList<Words.Word>) :
        RecyclerView.Adapter<WordListAdapter.Holder>() {
    private var items = wordlist
    var checkedWords = ArrayList<Words.Word>()

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val cbWord = itemView?.findViewById<CheckBox>(R.id.cb_word)
        val tvWord = itemView?.findViewById<TextView>(R.id.tv_word)
        val tvMeaning = itemView?.findViewById<TextView>(R.id.tv_meaning)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_word_checkbox, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvWord!!.text = items[position].word
        holder.tvMeaning!!.text = items[position].meanings[0]
        holder.cbWord!!.isChecked = items[position].id in WriteActivity.selectedWords
        holder.cbWord!!.setOnClickListener {
            if(holder.cbWord!!.isChecked){
                WriteActivity.addList(items[position].id)
            }else{
                WriteActivity.popList(items[position].id)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun wordUpdateList(wordItem: ArrayList<Words.Word>){
        this.items.addAll(wordItem)
    }
}