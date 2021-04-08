package com.example.konwnow.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Folder


class GroupsAdapter : RecyclerView.Adapter<GroupsAdapter.Holder>(){

    private lateinit var view: View
    private lateinit var context : Context
    private var items = ArrayList<Folder>()
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
        val wordBook = items[position].name
        holder.groupName.text = wordBook
        holder.wordsCount.text = "${items[position].wordsCount}개의 단어"
        var i = 0
        holder.itemView.setOnClickListener {
            i = 1 - i
            if (i == 1 ){ // 선택됐을
                holder.groupImage.setImageResource(R.drawable.ic_selected_group)
                if(!selectedBook.contains(wordBook)){
                    selectedBook.add(wordBook)
                }
                Log.d("${position}값이 추가되었다!",selectedBook.toString())
            }else{ // 선택안됐을
                holder.groupImage.setImageResource(R.drawable.ic_group)
                if(selectedBook.contains(wordBook)){
                    selectedBook.removeAt(selectedBook.indexOf(wordBook))
                }
                Log.d("${position}값이 추가되었다!",selectedBook.toString())
            }
        }



//        var args = Bundle()
//        val activity = view.context as AppCompatActivity
//        val manager: FragmentManager = activity.supportFragmentManager
//        val homeFragment= HomeFragment()
//        if(position == 0 or 1 or 2){
//            args.putString("wordBook", wordBook)
//            homeFragment.arguments = args
//            manager.beginTransaction()
//                .replace(R.id.fl_container, homeFragment)
//                .addToBackStack(null)
//                .commit()
//            Log.d("전환",wordBook)
//        }else{
//            args.putString("wordBook", wordBook)
//            homeFragment.arguments = args
//            manager.beginTransaction()
//                .replace(R.id.fl_container, homeFragment)
//                .addToBackStack(null)
//                .commit()
//            Log.d("전환",wordBook)
//        }
    }

    fun groupsUpdateList(groupsItem: ArrayList<Folder>){
        this.items.addAll(groupsItem)
    }


   fun makeClicked(name: String) {
        Log.d("maked Clicked",name)
        items.add(items.count(),Folder(name,0))
        notifyItemInserted(items.count())
    }
}