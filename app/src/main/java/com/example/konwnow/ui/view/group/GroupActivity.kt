package com.example.konwnow.ui.view.group

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.ui.adapter.GroupsAdapter
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.ui.view.home.HomeFragment

class GroupActivity : AppCompatActivity(), MakeGroupInterface  {
    var btnBack : ImageButton? = null
    var btnPlus : ImageButton? = null
    var btnApply : Button? = null
    private lateinit var rvGroups : RecyclerView
    private var groupsList = arrayListOf<WordBook>()
    private lateinit var groupsAdapter : GroupsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        requsetGroups()

        btnBack = findViewById(R.id.ib_back)
        btnBack!!.setOnClickListener{
            finish()
        }

        btnPlus = findViewById(R.id.ib_back)
        btnPlus!!.visibility = VISIBLE
        btnPlus!!.setImageResource(R.drawable.ic_plus_groups)
        btnPlus!!.setOnClickListener {
            val dlg = GroupDialog(this,this)
            dlg.start()
        }

        val homeFragment = HomeFragment()
        val mainActivity = MainActivity()
        btnApply = findViewById(R.id.btn_apply_groups)
        btnApply!!.setOnClickListener {
            groupsAdapter.applySelectedGroups()
            setResult(1)
            finish()
        }

    }

    private fun requsetGroups() {
        groupsList.clear()

        groupsAdapter = GroupsAdapter()
        rvGroups = findViewById(R.id.rv_groups)
        rvGroups.layoutManager = GridLayoutManager(this, 3)

//
//        groupsList.add(WordBook("휴지통",3))
//        groupsList.add(WordBook("토익 영단어",4))
//        groupsList.add(WordBook("영어2",5))

        groupsAdapter.groupsUpdateList(groupsList)
        rvGroups.adapter = groupsAdapter
        groupsAdapter.notifyDataSetChanged()
    }

    override fun makeClicked(name: String) {
        groupsAdapter.makeClicked(name)
    }
}