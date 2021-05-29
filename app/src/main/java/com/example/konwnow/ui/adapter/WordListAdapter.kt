package com.example.konwnow.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Words
import com.example.konwnow.ui.view.write.WriteActivity

class WordListAdapter(var wordlist: ArrayList<Words.Word>) :
        RecyclerView.Adapter<WordListAdapter.Holder>() {
    private var items = wordlist
//    var checkedWords = mutableSetOf<Words.Word>()

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val cbFolder = itemView?.findViewById<CheckBox>(R.id.cb_folder_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_folder_checkbox, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.cbFolder!!.text = items[position].word
        holder.cbFolder!!.setOnClickListener {
            if(holder.cbFolder!!.isChecked){
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