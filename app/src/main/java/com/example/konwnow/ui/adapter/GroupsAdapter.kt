package com.example.konwnow.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Folder
import com.example.konwnow.ui.view.group.GroupDialog
import com.example.konwnow.ui.view.group.MakeGroupInterface

class GroupsAdapter : RecyclerView.Adapter<GroupsAdapter.Holder>(), MakeGroupInterface {

    private lateinit var context : Context
    private var items = ArrayList<Folder>()
    private var lastIndex : Int? = null

    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        val groupName = itemView!!.findViewById<TextView>(R.id.tv_groups_name)
        val groupImage = itemView!!.findViewById<ImageView>(R.id.iv_groups)
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
        lastIndex = items.count()-1
        if(position == lastIndex){
            holder.groupImage.setImageResource(R.drawable.ic_plus_group)
            holder.groupImage.setOnClickListener {
                val dlg = GroupDialog(context,this)
                dlg.start()
            }
        }
    }

    fun groupsUpdateList(groupsItem: ArrayList<Folder>){
        this.items.addAll(groupsItem)
        items.add(Folder("",0))
    }


    override fun makeClicked(name: String) {
        Log.d("maked Clicked",name)
        items.add(lastIndex!!,Folder(name,0))
        notifyItemInserted(lastIndex!!)
    }
}