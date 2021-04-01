package com.example.konwnow.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.TestLog

class TestLogAdapter(private val mContext:Context, private val testLogList: ArrayList<TestLog>, val itemClick: (Int) -> Unit) :
        RecyclerView.Adapter<TestLogAdapter.Holder>() {

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvTestScore = itemView?.findViewById<TextView>(R.id.tv_test_score)
        val tvTestCount = itemView?.findViewById<TextView>(R.id.tv_test_count)
        val tvTestGroup = itemView?.findViewById<TextView>(R.id.tv_test_group)
        val tvTestDate = itemView?.findViewById<TextView>(R.id.tv_test_date)
        val ibRetest = itemView?.findViewById<ImageButton>(R.id.ib_reTest)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_test_logs, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvTestScore!!.text = testLogList[position].score.toString()
        when(testLogList[position].score){
            in 0..29 -> holder.tvTestScore!!.setTextColor(ContextCompat.getColor(mContext,R.color.red))
            in 30..69 -> holder.tvTestScore!!.setTextColor(ContextCompat.getColor(mContext,R.color.yellow))
            in 70..100 -> holder.tvTestScore!!.setTextColor(ContextCompat.getColor(mContext,R.color.green))
        }
        holder.tvTestCount!!.text = testLogList[position].count
        holder.tvTestGroup!!.text = testLogList[position].group
        holder.tvTestDate!!.text = testLogList[position].date
        holder.ibRetest!!.setOnClickListener {
            itemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return testLogList.size
    }
}