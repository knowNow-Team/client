package com.example.konwnow.ui.adapter

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.App
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.ui.view.group.MakeGroupInterface
import com.example.konwnow.ui.view.home.ChangeLevelinterface
import com.example.konwnow.ui.view.home.HomeFragment
import com.example.konwnow.ui.view.home.WordDialog
import com.example.konwnow.utils.Constants
import com.example.konwnow.utils.HOMEWORD
import com.example.konwnow.viewmodel.WordBookViewModel
import com.example.konwnow.viewmodel.WordViewModel


class WordsAdapter(private var items: ArrayList<WordBook.GetAllWordResponseData>,changeLevelinterface: ChangeLevelinterface) : RecyclerView.Adapter<WordsAdapter.Holder>(){

    private lateinit var context : Context
    private var toggleStatus = true

    private var changeLevelinterface : ChangeLevelinterface? = null

    init{
        this.changeLevelinterface = changeLevelinterface
    }


    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvEng = itemView?.findViewById<TextView>(R.id.tv_words_eng)
        val tvKor = itemView?.findViewById<TextView>(R.id.tv_words_kor)
        val level = itemView?.findViewById<TextView>(R.id.tv_level)
        val btnDelete = itemView?.findViewById<ImageButton>(R.id.ib_trash)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_words, parent, false)
        context = parent.context
        return Holder(view)
    }

    override fun getItemCount(): Int {
        if(items.size == 0){
            return 0
        }
        return items.size
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        //바인딩
        val meaning = items[position].wordsDoc[0].meanings
        when(meaning.size){
            0 ->{
                holder.tvKor?.text = ""
            }else ->{
            holder.tvKor?.text = meaning[0]
            }
        }
        holder.tvEng?.text = items[position].wordsDoc[0].word

        var levelText = holder.level!!
        when(items[position].words.filter){
            HOMEWORD.doNotKnow-> {
                levelText.setTextColor(context.getColor(R.color.red))
                levelText.text = context.getString(R.string.not_know)
            }
            HOMEWORD.confused -> {
                levelText.setTextColor(context.getColor(R.color.orange))
                levelText.text = context.getString(R.string.confuse)
            }
            HOMEWORD.memorized -> {
                levelText.setTextColor(context.getColor(R.color.colorMain))
                levelText.text = context.getString(R.string.know)
            }
        }

        // 이벤트발생 시 호출되는 함수
        changeToggle(holder)
        changeLevel(levelText,position)
        showDetail(holder,position)
        deleteWord(holder,position)

    }

    private fun deleteWord(holder: WordsAdapter.Holder, position: Int) {
        holder.btnDelete?.setOnClickListener {
            Toast.makeText(context, "${position}번 아이템 삭제!", Toast.LENGTH_SHORT).show()
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }

    private fun showDetail(holder: WordsAdapter.Holder, position: Int) {
        holder.itemView.setOnClickListener {
            val dlg = WordDialog(context)
            dlg.start(items[position])
        }
    }
    //체크된 태그
    //0: 몰라요
    //1: 헷갈려요
    //2: 알아요


    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    private fun changeLevel(levelText: TextView,position: Int) {
        levelText.setOnClickListener {
            var filter =""
            when(items[position].words.filter){
                HOMEWORD.doNotKnow-> {
                    filter = HOMEWORD.confused
                }
                HOMEWORD.confused -> {
                    filter = HOMEWORD.memorized
                }
                HOMEWORD.memorized -> {
                    filter = HOMEWORD.doNotKnow
                }
            }
            this.changeLevelinterface?.changeLevelClicked(filter,position)
        }
    }

    private fun changeToggle(holder: WordsAdapter.Holder) {
        if(toggleStatus){
            //단어 보여주기 모드
            holder.tvKor!!.visibility = VISIBLE
        }else{
            holder.tvKor!!.visibility = INVISIBLE
        }
    }

    fun wordsUpdateList(wordItem: ArrayList<WordBook.GetAllWordResponseData>){
        this.items.addAll(wordItem)
    }

    fun toggleUpdate(status : Boolean){
        this.toggleStatus = status
    }
}