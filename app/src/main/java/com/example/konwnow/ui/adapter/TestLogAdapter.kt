package com.example.konwnow.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.TestLog
import com.example.konwnow.ui.view.test.TestLogDialog
import com.example.konwnow.viewmodel.TestLogViewModel

class TestLogAdapter(private val mContext:Context,private val lifecycleOwner: LifecycleOwner, private val testLogList: ArrayList<TestLog.TestLogData>, val itemClick: (Int) -> Unit) :
        RecyclerView.Adapter<TestLogAdapter.Holder>() {

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvTestScore = itemView?.findViewById<TextView>(R.id.tv_test_score)
        val tvTestCount = itemView?.findViewById<TextView>(R.id.tv_test_count)
        val tvTestGroup = itemView?.findViewById<TextView>(R.id.tv_test_group)
        val tvTestDate = itemView?.findViewById<TextView>(R.id.tv_test_date)
        lateinit var dlg: TestLogDialog
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
        holder.tvTestCount!!.text = testLogList[position].correctAnswerCount.toString() +" / "+ testLogList[position].wordTotalCount.toString()
        holder.tvTestGroup!!.text = testLogList[position].wordbooks.toString()
        holder.tvTestDate!!.text = testLogList[position].createdAt
        holder.itemView.setOnClickListener {
            holder.dlg = TestLogDialog(mContext,lifecycleOwner)
            holder.dlg.start(testLogList[position].id)
        }
    }

    override fun getItemCount(): Int {
        return testLogList.size
    }
}