package com.example.konwnow.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.FolderDTO

class FolderAdapter(private val folderList: ArrayList<FolderDTO>) :
        RecyclerView.Adapter<FolderAdapter.Holder>() {

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val cbFolder = itemView?.findViewById<CheckBox>(R.id.cb_folder_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_folder, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.cbFolder!!.text = folderList[position].name
    }

    override fun getItemCount(): Int {
        return folderList.size
    }
}