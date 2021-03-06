package com.example.konwnow.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.App
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.ui.view.home.HomeInterface
import com.example.konwnow.ui.view.home.WordDialog
import com.example.konwnow.utils.HOMEWORD


class WordsAdapter(private var items: ArrayList<WordBook.GetAllWordResponseData>, homeInterface: HomeInterface) : RecyclerView.Adapter<WordsAdapter.Holder>(){

    private lateinit var context : Context
    private var toggleStatus = true

    private var homeInterface : HomeInterface? = null

    init{
        this.homeInterface = homeInterface
    }


    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvEng = itemView?.findViewById<TextView>(R.id.tv_words_eng)
        val tvKor = itemView?.findViewById<TextView>(R.id.tv_words_kor)
        val level = itemView?.findViewById<TextView>(R.id.tv_level)
        val btnDelete = itemView?.findViewById<ImageButton>(R.id.ib_trash)
        val btnRecovery = itemView?.findViewById<ImageButton>(R.id.ib_recovery)
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
        if(App.sharedPrefs.getTitle() == "휴지통"){
            holder.btnRecovery!!.visibility = View.VISIBLE
        }else{
            holder.btnRecovery!!.visibility = View.INVISIBLE
        }
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

        if(items[position].words.isRemoved){
            levelText.visibility = View.INVISIBLE
        }
        when(items[position].words.filter){
            HOMEWORD.FILTER.doNotKnow-> {
                levelText.setTextColor(context.getColor(R.color.red))
                levelText.text = context.getString(R.string.not_know)
            }
            HOMEWORD.FILTER.confused -> {
                levelText.setTextColor(context.getColor(R.color.orange))
                levelText.text = context.getString(R.string.confuse)
            }
            HOMEWORD.FILTER.memorized -> {
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
            if(App.sharedPrefs.getTitle() =="휴지통"){
                this.homeInterface?.realDelete(position)
            }else{
                this.homeInterface?.trashClicked(position)
            }
        }
        holder.btnRecovery?.setOnClickListener {
            this.homeInterface?.recoveryWord(position)
        }
    }

    private fun showDetail(holder: WordsAdapter.Holder, position: Int) {
        holder.itemView.setOnClickListener {
            val dlg = WordDialog(context)
            dlg.start(items[position])
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    private fun changeLevel(levelText: TextView,position: Int) {
        levelText.setOnClickListener {
            var filter =""
            when(items[position].words.filter){
                HOMEWORD.FILTER.doNotKnow-> {
                    filter = HOMEWORD.FILTER.confused
                }
                HOMEWORD.FILTER.confused -> {
                    filter = HOMEWORD.FILTER.memorized
                }
                HOMEWORD.FILTER.memorized -> {
                    filter = HOMEWORD.FILTER.doNotKnow
                }
            }
            this.homeInterface?.changeLevelClicked(filter,position)
        }
    }

    private fun changeToggle(holder: WordsAdapter.Holder) {
        if(toggleStatus){
            holder.tvKor!!.visibility = VISIBLE
        }else{
            holder.tvKor!!.visibility = INVISIBLE
        }
    }

    fun toggleUpdate(status : Boolean){
        this.toggleStatus = status
    }
}