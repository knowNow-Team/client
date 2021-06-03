package com.example.konwnow.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.konwnow.App
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Friend
import com.example.konwnow.ui.view.MainActivity

class FriendAdapter(private var items: ArrayList<Friend.FriendData>) : RecyclerView.Adapter<FriendAdapter.Holder>(){

    private lateinit var view: View
    private lateinit var context : Context


    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){
        val profile =itemView!!.findViewById<ImageView>(R.id.iv_friend_profile)
        val level = itemView!!.findViewById<TextView>(R.id.tv_friend_level)
        val nickname = itemView!!.findViewById<TextView>(R.id.tv_friend_nickname)
        val talk = itemView!!.findViewById<TextView>(R.id.tv_friend_talk)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendAdapter.Holder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false)
        context = parent.context
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FriendAdapter.Holder, position: Int) {
        var imgResource = R.drawable.ic_bronze
        when(items[position].level){
            0 -> imgResource = R.drawable.level_0
            1 -> imgResource = R.drawable.level_1
            2 -> imgResource = R.drawable.level_2
            3 -> imgResource = R.drawable.level_3
            4 -> imgResource = R.drawable.level_4
            5 -> imgResource = R.drawable.level_5
        }

        Glide.with(App.instance)
            .load(imgResource)
            .circleCrop()
            .into(holder.profile)


        holder.level.text = "Level${items[position].level}"
        holder.nickname.text = items[position].nickName
        holder.talk.text = items[position].profileMessage?:"상태메세지가 없습니다."

    }

    fun freindUpdateList(freindsItem: ArrayList<Friend.FriendData>){
        this.items.addAll(freindsItem)
    }
}