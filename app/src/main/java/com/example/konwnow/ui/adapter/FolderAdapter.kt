package com.example.konwnow.ui.adapter

import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.ui.view.test.TestFragment
import com.example.konwnow.utils.Constants
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.ramotion.foldingcell.FoldingCell
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class FolderAdapter(var folderList:ArrayList<WordBook.WordBookData>,val itemClick: (Int, Boolean) -> Unit) :
        RecyclerView.Adapter<FolderAdapter.Holder>() {
    private var items = folderList
    private lateinit var view:View

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val titleTv = itemView?.findViewById<TextView>(R.id.tv_groups_name)
        val countTv = itemView?.findViewById<TextView>(R.id.tv_count)
        val openTitleTv = itemView?.findViewById<TextView>(R.id.tv_title_opened)
        val openCountTv = itemView?.findViewById<TextView>(R.id.tv_count_opened)
        val openDateTv = itemView?.findViewById<TextView>(R.id.tv_date_opened)
        val foldingcell = itemView?.findViewById<FoldingCell>(R.id.folding_cell)
        val pieChart = itemView?.findViewById<PieChart>(R.id.pieChart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.item_folder_folding_cell, parent, false)
        return Holder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        var dataset = setChart(position)
        holder.foldingcell!!.setOnClickListener {
            if(holder.foldingcell.isSelected ){
                holder.foldingcell.isSelected = false
                itemClick(position,holder.foldingcell.isSelected)
            }else{
                TestFragment.selectedWordBook[items[position].id] = items[position].title
                holder.foldingcell.isSelected = true
                itemClick(position,holder.foldingcell.isSelected)
            }
        }
        holder.titleTv!!.text = items[position].title
        holder.countTv!!.text = String.format(view.context.getString(R.string.word_count),items[position].allCount)
        holder.openTitleTv!!.text = items[position].title
        holder.openCountTv!!.text = String.format(view.context.getString(R.string.word_count),items[position].allCount)
        holder.openDateTv!!.text = String.format(view.context.getString(R.string.createAt),parseDate(items[position].createdAt))
//        //TODO: ?????? ???????????? ???, ?????????
//        var tempWords: String ="0"
//        for(i in 1..2){
//            tempWords += ", $i"
//        }
//        tempWords+="..."

//        holder.openwordTv!!.text = String.format(view.context.getString(R.string.words),tempWords)
        holder.foldingcell!!.setOnLongClickListener {
            holder.foldingcell!!.toggle(false)
            holder.pieChart!!.setUsePercentValues(true)
            holder.pieChart!!.apply {
                data = PieData(dataset)
                description.isEnabled = false
                isRotationEnabled = false
                centerText = "?????? ??????"
                setEntryLabelTextSize(9f)
                setEntryLabelColor(Color.BLACK)
            }
            holder.pieChart!!.invalidate()
            true
        }
    }

    private fun parseDate(createdAt: String): String {
        val oldFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA) // ?????? ????????? ??????
        oldFormat.timeZone = TimeZone.getTimeZone("KST")
        val newFormat = SimpleDateFormat("yyyy??? MM??? dd??? HH:mm:ss", Locale.KOREA) // ?????? ????????? ??????

        val oldDate: Date = oldFormat.parse(createdAt)
        return newFormat.format(oldDate)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun setChart(position: Int):PieDataSet {
        var entries = ArrayList<PieEntry>()
        for(filter in items[position].filters){
            Log.d(Constants.TAG,filter.toString())
            if(filter.filter =="doNotKnow"){
                entries.add(PieEntry(filter.count.toFloat(),"?????????"))
            }else if(filter.filter == "memorized"){
                entries.add(PieEntry(filter.count.toFloat(),"?????????"))
            }else{
                entries.add(PieEntry(filter.count.toFloat(),"????????????"))
            }
        }

        val colorsItems = ArrayList<Int>()
        colorsItems.add(view.context.getColor(R.color.colorAccent))
        colorsItems.add(view.context.getColor(R.color.colorMain))
        colorsItems.add(view.context.getColor(R.color.bw_g2))

        val pieDataSet = PieDataSet(entries,"????????????")
        pieDataSet.apply {
            //???????????? ??????
            sliceSpace = 2f
            selectionShift = 5f

            //???????????? ???, ?????? ??????????????? resource??? ????????? ??? ???????????? ?????? ??????
            colors = colorsItems


            //value ??????, ?????? ??????
            yValuePosition=PieDataSet.ValuePosition.OUTSIDE_SLICE
            valueTextSize = 8f
            valueTextColor = Color.BLACK

        }
        return pieDataSet
    }

    override fun getItemCount(): Int {
        return items.size
    }



    fun folderUpdateList(wordBookItem: ArrayList<WordBook.WordBookData>){
//        this.items.clear()
        Log.d("????????????",wordBookItem.toString())
        this.items.addAll(wordBookItem)
    }
}