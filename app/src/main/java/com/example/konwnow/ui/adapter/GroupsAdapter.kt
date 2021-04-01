package com.example.konwnow.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Folder
import com.example.konwnow.data.model.dto.Words

class GroupsAdapter : RecyclerView.Adapter<GroupsAdapter.Holder>() {

    private lateinit var context : Context
    private var items = ArrayList<Folder>()

    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        val groupName = itemView!!.findViewById<TextView>(R.id.tv_groups_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsAdapter.Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_groups, parent, false)
        context = parent.context
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: GroupsAdapter.Holder, position: Int) {
        holder.groupName.text = items[position].name
    }

    fun groupsUpdateList(groupsItem: ArrayList<Folder>){
        this.items.addAll(groupsItem)
    }
}