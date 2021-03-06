package com.example.konwnow.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Manual

class ManualAdapter() :  RecyclerView.Adapter<ManualAdapter.Holder>() {
    lateinit var myContext: Context
    lateinit var mHloder:Holder
    private var manualList = ArrayList<Manual>()


    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvManualMention = itemView?.findViewById<TextView>(R.id.tv_manual_mention)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_manual, parent, false)
        myContext = parent.context
        mHloder = Holder(view)
        return mHloder
    }


    override fun onBindViewHolder(holder: Holder, position: Int){

        when(position){

            0 -> {
                holder.tvManualMention!!.text = manualList[position].mention
            }
            1 -> {
                holder.tvManualMention!!.text = manualList[position].mention
            }
            2 -> {
                holder.tvManualMention!!.text = manualList[position].mention
            }
            3 -> {
                holder.tvManualMention!!.text = manualList[position].mention
            }
            4 -> {
                holder.tvManualMention!!.text = manualList[position].mention
            }
            5 -> {
                holder.tvManualMention!!.text = manualList[position].mention
            }
        }

    }

    override fun getItemCount(): Int {
        return manualList.size
    }

    fun manualUpdateList(manualItem: ArrayList<Manual>){
        this.manualList.clear()
        this.manualList.addAll(manualItem)
        notifyDataSetChanged()
    }




}