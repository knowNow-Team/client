package com.example.konwnow.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R

class SpellAdapter(val itemClick: (Int) -> Unit) :
        RecyclerView.Adapter<SpellAdapter.Holder>() {
    private var spellList = ArrayList<String>()
    private var wrote = ArrayList<String>()
    private lateinit var mContext:Context

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvSpell = itemView?.findViewById<TextView>(R.id.tv_spell)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_spell, parent, false)
        mContext = view.context
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvSpell!!.text = spellList[position].toString()
        holder.tvSpell!!.setOnClickListener {
            wrote.add(spellList[position].toString())
            Log.d("spell",wrote.toString())
            removeItem(position)
            itemClick(position)
        }
    }

    fun removeItem(position: Int){
        spellList.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun removeAll(){
        spellList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return spellList.size
    }

    fun wordsUpdateList(_spellList: ArrayList<String>, _wrote: ArrayList<String>){
        this.spellList.addAll(_spellList)
        this.wrote = _wrote
    }

}