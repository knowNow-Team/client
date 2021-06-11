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
//        //TODO: 단어 들어가야 됨, 후순위
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
                centerText = "태그 비율"
                setEntryLabelTextSize(9f)
                setEntryLabelColor(Color.BLACK)
            }
            holder.pieChart!!.invalidate()
            true
        }
    }

    private fun parseDate(createdAt: String): String {
        val oldFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA) // 받은 데이터 형식
        oldFormat.timeZone = TimeZone.getTimeZone("KST")
        val newFormat = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss", Locale.KOREA) // 바꿀 데이터 형식

        val oldDate: Date = oldFormat.parse(createdAt)
        return newFormat.format(oldDate)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun setChart(position: Int):PieDataSet {
        var entries = ArrayList<PieEntry>()
        for(filter in items[position].filters){
            Log.d(Constants.TAG,filter.toString())
            if(filter.filter =="doNotKnow"){
                entries.add(PieEntry(filter.count.toFloat(),"몰라요"))
            }else if(filter.filter == "memorized"){
                entries.add(PieEntry(filter.count.toFloat(),"알아요"))
            }else{
                entries.add(PieEntry(filter.count.toFloat(),"헷갈려요"))
            }
        }

        val colorsItems = ArrayList<Int>()
        colorsItems.add(view.context.getColor(R.color.colorAccent))
        colorsItems.add(view.context.getColor(R.color.colorMain))
        colorsItems.add(view.context.getColor(R.color.bw_g2))

        val pieDataSet = PieDataSet(entries,"태그비율")
        pieDataSet.apply {
            //슬라이스 간격
            sliceSpace = 2f
            selectionShift = 5f

            //슬라이스 색, 미리 정의하거나 resource로 가져온 색 리스트를 줘도 좋다
            colors = colorsItems


            //value 위치, 크기 지정
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
        Log.d("노티파이",wordBookItem.toString())
        this.items.addAll(wordBookItem)
    }
}