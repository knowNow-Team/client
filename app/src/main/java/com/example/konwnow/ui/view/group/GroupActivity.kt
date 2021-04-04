package com.example.konwnow.ui.view.group

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Folder
import com.example.konwnow.data.model.dto.Words
import com.example.konwnow.ui.adapter.GroupsAdapter
import com.example.konwnow.ui.adapter.WordsAdapter
import com.example.konwnow.ui.view.MainActivity

class GroupActivity : AppCompatActivity() {
    var btnBack : ImageButton? = null
    private lateinit var rvGroups : RecyclerView
    private var groupsList = arrayListOf<Folder>()
    private lateinit var groupsAdapter : GroupsAdapter
    //rivate val defalutGroups =resources.getStringArray(R.array.groups_list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        requsetGroups()

        btnBack = findViewById(R.id.ib_back)
        btnBack!!.setOnClickListener{
            finish()
        }

    }

    private fun requsetGroups() {
        groupsList.clear()

        groupsAdapter = GroupsAdapter()
        rvGroups = findViewById(R.id.rv_groups)
        rvGroups.layoutManager = GridLayoutManager(this, 2)

        groupsList.add(Folder("전체",1))
        groupsList.add(Folder("틀린 문제 ",2))
        groupsList.add(Folder("휴지통",3))
        groupsList.add(Folder("토익 영단어",4))
        groupsList.add(Folder("영어2",5))
        groupsList.add(Folder("영어회화",6))
        groupsList.add(Folder("공무원시험",7))
        groupsList.add(Folder("토익 영단어",8))
        groupsList.add(Folder("영어2",9))
        groupsList.add(Folder("영어회화",10))
        groupsList.add(Folder("공무원시험",11))

        groupsAdapter.groupsUpdateList(groupsList)
        rvGroups.adapter = groupsAdapter
        groupsAdapter.notifyDataSetChanged()
    }


}