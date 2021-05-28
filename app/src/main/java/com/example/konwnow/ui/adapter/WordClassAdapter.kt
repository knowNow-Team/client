package com.example.konwnow.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.konwnow.R


class WordClassAdapter() : RecyclerView.Adapter<WordClassAdapter.Holder>() {
    private var items = ArrayList<String>()

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvWordClass = itemView!!.findViewById<TextView>(R.id.tv_word_class)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_word_class, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvWordClass.text = items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun wordUpdateList(wordClass: String) {
        this.items.add(wordClass)
    }
}