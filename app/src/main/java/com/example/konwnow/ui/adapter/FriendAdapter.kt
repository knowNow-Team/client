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

class FriendAdapter : RecyclerView.Adapter<FriendAdapter.Holder>(){

    private lateinit var view: View
    private lateinit var context : Context
    private var items = ArrayList<Friend.FriendData>()

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

        Glide.with(App.instance)
            .load("https://postfiles.pstatic.net/MjAyMTA0MDVfMjM0/MDAxNjE3NTYwNTU4ODYw.OWh-4Z2lulAcRkEwVdufyy49VGYOggNAeZFbeohAB_kg.UNRV7GsH828h_cs5AHffbxPEtA2h63bySic0EMA7_rEg.JPEG.rhdwn6580/IMG_7989.JPG?type=w966")
            .circleCrop()
            .into(holder.profile)

        //TODO: level 대신
        holder.level.text = "Level${items[position].userId}"
        holder.nickname.text = items[position].nickName
        holder.talk.text = items[position].state
    }

    fun freindUpdateList(freindsItem: ArrayList<Friend.FriendData>){
        this.items.addAll(freindsItem)
    }
}