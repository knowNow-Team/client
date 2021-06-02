package com.example.konwnow.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Friend
import android.util.Log
import android.widget.ImageButton


class RankingAdapter() : RecyclerView.Adapter<RankingAdapter.Holder>() {
    lateinit var myContext: Context
    lateinit var mHloder:Holder
    private var userList= arrayListOf<Friend.FriendData>()


    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvRankingTitle = itemView?.findViewById<TextView>(R.id.tv_ranking_title)
        val tv1stLevel = itemView?.findViewById<TextView>(R.id.tv_1st_level)
        val tv1stNick = itemView?.findViewById<TextView>(R.id.tv_1st_nick)
        val iv1stThumb = itemView?.findViewById<ImageView>(R.id.iv_1st)
        val tv2ndLevel = itemView?.findViewById<TextView>(R.id.tv_2nd_level)
        val tv2ndNick = itemView?.findViewById<TextView>(R.id.tv_2nd_nick)
        val iv2ndThumb = itemView?.findViewById<ImageView>(R.id.iv_2nd)
        val tv3rdLevel = itemView?.findViewById<TextView>(R.id.tv_3rd_level)
        val tv3rdNick = itemView?.findViewById<TextView>(R.id.tv_3rd_nick)
        val iv3rdThumb = itemView?.findViewById<ImageView>(R.id.iv_3rd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ranking_section, parent, false)
        myContext = parent.context
        mHloder = Holder(view)
        return mHloder
    }


    override fun onBindViewHolder(holder: Holder, position: Int){
        if(position == 0){

            holder.itemView.setBackgroundColor(ContextCompat.getColor(myContext,R.color.colorDeepPrimaryDark))
            holder.tvRankingTitle!!.text = myContext.getString(R.string.ranking_highest_score)
        }else if(position == 2){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(myContext,R.color.colorPrimaryDark))
            holder.tvRankingTitle!!.text = myContext.getString(R.string.ranking_most_add)
        }else{
            holder.itemView.setBackgroundColor(ContextCompat.getColor(myContext,R.color.colorMain))
            holder.tvRankingTitle!!.text = myContext.getString(R.string.ranking_most_test)
        }

        //TODO: userID = level 임시로 쓰는 거
        holder.tv1stLevel!!.text = String.format(myContext.getString(R.string.user_level),userList[0].userId)
        holder.tv1stNick!!.text = String.format(myContext.getString(R.string.ranking_1st), userList[0].nickName)
        Glide.with(myContext).load(setLevelImage(position)).thumbnail(0.1f).into(holder.iv1stThumb!!)
        holder.tv2ndLevel!!.text = String.format(myContext.getString(R.string.user_level),userList[1].userId)
        holder.tv2ndNick!!.text = String.format(myContext.getString(R.string.ranking_2nd), userList[1].nickName)
        Glide.with(myContext).load(setLevelImage(position)).into(holder.iv2ndThumb!!)
        holder.tv3rdLevel!!.text = String.format(myContext.getString(R.string.user_level),userList[2].userId)
        holder.tv3rdNick!!.text = String.format(myContext.getString(R.string.ranking_3rd), userList[2].nickName)
        Glide.with(myContext).load(setLevelImage(position)).into(holder.iv3rdThumb!!)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setLevelImage(position: Int): Int{
        //TODO: userList[position].level 에 따라
        when(0){
            0 -> return R.drawable.level_0
            1 -> return R.drawable.level_1
            2 -> return R.drawable.level_2
            3 -> return R.drawable.level_3
            4 -> return R.drawable.level_4
            5 -> return R.drawable.level_5
        }
        return 0
    }


    fun rankingUpdateList(userItem: List<Friend.FriendData>?){
        this.userList.clear()
        if (userItem != null) {
            this.userList.addAll(userItem)
        }
        Log.d("리스트",userItem.toString())
        notifyDataSetChanged()
    }


}