package com.example.konwnow.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Folder

class FolderAdapter(val itemClick: (Int, Boolean) -> Unit) :
        RecyclerView.Adapter<FolderAdapter.Holder>() {
    private var items = ArrayList<Folder>()
    var checkedFolder = mutableSetOf<Folder>()

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val cbFolder = itemView?.findViewById<CheckBox>(R.id.cb_folder_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_folder_checkbox, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.cbFolder!!.text = items[position].name
        holder.cbFolder!!.setOnClickListener {
            if(holder.cbFolder!!.isChecked){
                checkedFolder.add(items[position])
            }else{
                checkedFolder.remove(items[position])
            }
            itemClick(position, holder.cbFolder.isChecked)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItemChecked() {
        for(item in items){

        }
    }

    fun folderUpdateList(folderItem: ArrayList<Folder>){
        this.items.addAll(folderItem)
    }
}