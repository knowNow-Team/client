package com.example.konwnow.ui.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.ui.view.home.HomeFragment


class GroupsAdapter : RecyclerView.Adapter<GroupsAdapter.Holder>(){

    private lateinit var view: View
    private lateinit var context : Context
    private var items = ArrayList<WordBook>()
    private val selectedBook = ArrayList<String>()

    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        val groupName = itemView!!.findViewById<TextView>(R.id.tv_groups_name)
        val wordsCount = itemView!!.findViewById<TextView>(R.id.tv_words_count)
        val groupImage = itemView!!.findViewById<ImageView>(R.id.iv_groups)
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
//        val wordBook = items[position].name
//        holder.groupName.text = wordBook
//        holder.wordsCount.text = "${items[position].wordsCount}개의 단어"
//        var i = 0
//        holder.itemView.setOnClickListener {
//            i = 1 - i
//            when(i){
//                1 -> { // 선택하기
//                    if(selectedBook.contains(items[0].name) ){ // 휴지통이 선택되어 있는 경우
//                        Toast.makeText(context,"휴지통은 중복 선택이 불가능합니다.",Toast.LENGTH_SHORT).show()
//                        i = 0
//                    }else if((position == 0) and selectedBook.isNotEmpty()){ // 다른게 이미 선택되어있는데 전체,휴지통을 선택하는 경우
//                        Toast.makeText(context,"${items[position].name}은 중복 선택이 불가능합니다.!!!!!",Toast.LENGTH_SHORT).show()
//                        i = 0
//                    } else{
//                        holder.groupImage.setImageResource(R.drawable.ic_selected_group)
//                        selectedBook.add(wordBook)
//                        Log.d("${position}값이 추가되었다!",selectedBook.toString())
//                    }
//                }else -> { // 선택 해제하기
//                    holder.groupImage.setImageResource(R.drawable.ic_group)
//                    selectedBook.removeAt(selectedBook.indexOf(wordBook))
//                    Log.d("${position}값이 삭되었다!",selectedBook.toString())
//                }
//            }
//        }
    }

    fun applySelectedGroups(){
        val bundle = Bundle()
        bundle.putStringArrayList("wordBook",selectedBook)
        val homeFragment = HomeFragment()
        homeFragment.arguments = bundle
        Toast.makeText(context,"홈으로 전환 " ,Toast.LENGTH_SHORT).show()
    }

    fun groupsUpdateList(groupsItem: ArrayList<WordBook>){
        this.items.addAll(groupsItem)
    }

   fun makeClicked(name: String) {
        Log.d("maked Clicked",name)
        //items.add(items.count(), WordBook(name,0))
        notifyItemInserted(items.count())
    }
}