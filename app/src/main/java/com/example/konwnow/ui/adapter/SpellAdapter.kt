package com.example.konwnow.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.TestLog

class SpellAdapter(private val mContext:Context, private val spellList: ArrayList<String>, val itemClick: (Int) -> Unit) :
        RecyclerView.Adapter<SpellAdapter.Holder>() {

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvSpell = itemView?.findViewById<TextView>(R.id.tv_spell)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_spell, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvSpell!!.text = spellList[position].toString()
        holder.tvSpell!!.setOnClickListener {
            itemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return spellList.size
    }
}