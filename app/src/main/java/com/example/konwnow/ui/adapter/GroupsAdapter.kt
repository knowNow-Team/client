package com.example.konwnow.ui.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.ui.view.group.ApplyGroupsInterface
import com.example.konwnow.ui.view.group.MakeGroupInterface
import com.example.konwnow.ui.view.home.HomeFragment


class GroupsAdapter(applyGroupsInterface: ApplyGroupsInterface,var items: ArrayList<WordBook.WordBooks>) : RecyclerView.Adapter<GroupsAdapter.Holder>(){

    private lateinit var view: View
    private lateinit var context : Context
//    private var items = ArrayList<WordBook.WordBooks>()
    private val selectedBook = ArrayList<WordBook.WordBooks>()

    private var applyGroupsInterface : ApplyGroupsInterface? = null

    init{
        this.applyGroupsInterface = applyGroupsInterface
    }

    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        val groupName = itemView!!.findViewById<TextView>(R.id.tv_groups_name)
        val wordsCount = itemView!!.findViewById<TextView>(R.id.tv_words_count)
        val groupImage = itemView!!.findViewById<ImageView>(R.id.iv_groups)
        val optionBox = itemView!!.findViewById<LinearLayout>(R.id.ll_longButton)
        val btnDelete = itemView!!.findViewById<TextView>(R.id.tv_delete)
        val btnEdit = itemView!!.findViewById<TextView>(R.id.tv_edit)
        val llBack = itemView!!.findViewById<LinearLayout>(R.id.ll_backgound)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsAdapter.Holder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.item_groups, parent, false)
        context = parent.context
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: GroupsAdapter.Holder, position: Int) {
        val wordBook = items[position].title
        holder.groupName.text = wordBook
        if(items[position].allCount == null){ //??????
            holder.wordsCount.text = ""
        }else{
            holder.wordsCount.text = "${items[position].allCount}?????? ??????"
        }

        var i = 0
        holder.groupImage.setOnClickListener {
            i = 1 - i
            when(i){
                1 -> { // ????????????
                    if(selectedBook.contains(items[0]) ){ // ???????????? ???????????? ?????? ??????
                        Toast.makeText(context,"???????????? ?????? ????????? ??????????????????.",Toast.LENGTH_SHORT).show()
                        i = 0
                    }else if((position == 0) and selectedBook.isNotEmpty()){ // ????????? ?????? ????????????????????? ???????????? ???????????? ??????
                        Toast.makeText(context,"${items[position].title}??? ?????? ????????? ??????????????????.",Toast.LENGTH_SHORT).show()
                        i = 0
                    } else{
                        holder.groupImage.setImageResource(R.drawable.img_folder_selected)
                        selectedBook.add(items[position])
                        Log.d("${position}?????? ???????????????!",selectedBook.toString())
                    }
                }else -> { // ?????? ????????????
                    holder.groupImage.setImageResource(R.drawable.img_folder)
                    selectedBook.removeAt(selectedBook.indexOf(items[position]))
                    Log.d("${position}?????? ????????????!",selectedBook.toString())
                }
            }
            this.applyGroupsInterface?.applyGroupsCliked(selectedBook)
        }

        longCligkedEvent(holder, position)
    }

    private fun longCligkedEvent(holder : Holder, position: Int) {
        var i = 0
        holder.groupImage.setOnLongClickListener{
            holder.optionBox.visibility = View.VISIBLE
            holder.btnDelete.setOnClickListener {
                this.applyGroupsInterface?.deleteWordBookClicked(position)
                holder.optionBox.visibility = View.GONE
            }
            holder.btnEdit.setOnClickListener {
                this.applyGroupsInterface?.editWordBookClicked(position)
                holder.optionBox.visibility = View.GONE
            }
            true
        }
    }


}