package com.example.konwnow.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Folder
import com.example.konwnow.data.model.dto.Words

class WordListAdapter() :
        RecyclerView.Adapter<WordListAdapter.Holder>() {
    private var items = ArrayList<Words>()
    var checkedWords = mutableSetOf<Words>()

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val cbFolder = itemView?.findViewById<CheckBox>(R.id.cb_folder_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_folder_checkbox, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.cbFolder!!.text = items[position].eng
        holder.cbFolder!!.setOnClickListener {
            if(holder.cbFolder!!.isChecked){
                checkedWords.add(items[position])
            }else{
                checkedWords.remove(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun wordUpdateList(wordItem: ArrayList<Words>){
        this.items.addAll(wordItem)
    }
}