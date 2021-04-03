  package com.example.konwnow.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Words

class DictationAdapter(val itemClick: (ArrayList<String>) -> Unit) : RecyclerView.Adapter<DictationAdapter.Holder>() {
    lateinit var myContext: Context
    private var quizList = ArrayList<Words>()
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
        holder.tvDictationEng!!.text = quizList[position].eng
        holder.edtDictation!!.text = ""
        holder.edtDictation!!.isFocusableInTouchMode = true;
        holder.edtDictation!!.requestFocus()
        setSubmitBtn(holder)
    }


    override fun getItemCount(): Int {
        return quizList.size
    }

    private fun setSubmitBtn(holder: Holder){
        mHloder.btnSubmit!!.setOnClickListener{
            var answer = (holder.edtDictation!!.text).toString()
            wrote.add(answer)
            itemClick(wrote)
        }
    }

    fun wordsUpdateList(quizItem: ArrayList<Words>){
        this.quizList.addAll(quizItem)
    }


}