  package com.example.konwnow.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Words

class DictationAdapter(var wordList:ArrayList<Words.Word>,val itemClick: (ArrayList<String>) -> Unit) : RecyclerView.Adapter<DictationAdapter.Holder>() {
    lateinit var myContext: Context
    private var quizList = wordList
    lateinit var mHloder:Holder
    var wrote = arrayListOf<String>()


    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvDictationEng = itemView?.findViewById<TextView>(R.id.tv_dictation_eng)
        val edtDictation = itemView?.findViewById<TextView>(R.id.edt_dictation)
        val btnSubmit = itemView?.findViewById<Button>(R.id.btn_submit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dictation, parent, false)
        myContext = parent.context
        mHloder = Holder(view)
        return mHloder
    }

    override fun onBindViewHolder(holder: Holder, position: Int){
        holder.tvDictationEng!!.text = quizList[position].meanings[0]
        holder.edtDictation!!.text = ""
        holder.edtDictation!!.isFocusableInTouchMode = true;
        holder.edtDictation!!.requestFocus()
        setSubmitBtn(holder, position)
    }


    override fun getItemCount(): Int {
        return quizList.size
    }

    private fun setSubmitBtn(holder: Holder, position:Int){
        if(position == quizList.size-1){
            holder.btnSubmit!!.text = myContext.getString(R.string.submit)
        }
        holder.btnSubmit!!.setOnClickListener{
            var answer = (holder.edtDictation!!.text).toString()
            wrote.add(answer)
            itemClick(wrote)
        }
    }

//    fun wordsUpdateList(quizItem: ArrayList<WordId>){
//        this.quizList.addAll(quizItem)
//    }


}