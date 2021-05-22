package com.example.konwnow.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Quiz
import com.example.konwnow.ui.view.home.WordDialog


class TestWordsAdapter : RecyclerView.Adapter<TestWordsAdapter.Holder>(){

    private lateinit var context : Context
    private var items = ArrayList<Quiz>()


    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvEng = itemView?.findViewById<TextView>(R.id.tv_words_eng)
        val tvKor = itemView?.findViewById<TextView>(R.id.tv_words_kor)
        val tvMissAnswer = itemView?.findViewById<TextView>(R.id.tv_miss_answer)
        val ivHit = itemView?.findViewById<ImageView>(R.id.iv_hit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_test_words, parent, false)
        context = parent.context
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvEng!!.text = items[position].wordId
        holder.tvKor!!.text = items[position].answer
        if(items[position].isCorrect){
            holder.ivHit!!.background = context.getDrawable(R.drawable.ic_hit)
        }else{
            holder.ivHit!!.background = context.getDrawable(R.drawable.ic_miss)
            holder.tvMissAnswer!!.visibility = View.VISIBLE
            val str = String.format(context.getString(R.string.miss), items[position].answer)
            var ssb = SpannableStringBuilder(str)
            ssb.setSpan(ForegroundColorSpan( ContextCompat.getColor(context, R.color.red)), 7, 8+items[position].answer.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            holder.tvMissAnswer!!.text = ssb
        }


        holder.itemView.setOnClickListener {
            val dlg = WordDialog(context)
//            dlg.start(items[position].target)
        }
    }

    fun wordsUpdateList(wordItem: ArrayList<Quiz>){
        this.items.addAll(wordItem)
    }

}