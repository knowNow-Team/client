package com.example.konwnow.ui.view.mypage

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Friend
import com.example.konwnow.ui.adapter.FriendAdapter


class FriendActivity : AppCompatActivity() {

    private var friendsItem = arrayListOf<Friend>()
    private lateinit var friendsAdapter : FriendAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activiry_friend)

        setToolbar()
        search()
        setRecycler()
    }

    override fun onResume() {
        super.onResume()
        friendsItem.clear()
        setToolbar()
        search()
        setRecycler()
    }

    private fun setRecycler() {
        friendsAdapter = FriendAdapter()
        val rvFriend = findViewById<RecyclerView>(R.id.rv_friend)
        rvFriend.layoutManager = LinearLayoutManager(this)

        friendsAdapter.freindUpdateList(friendsItem)
        rvFriend.adapter = friendsAdapter
    }

    private fun search() {
        val searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.sv_friend)
    }

    private fun setToolbar() {
        val title  = findViewById<TextView>(R.id.tv_title)
        title.text = "친구관리"

        val btnBack = findViewById<ImageButton>(R.id.ib_back)
        btnBack.setOnClickListener {
            setResult(1)
            finish()
        }

        val copyLink = findViewById<TextView>(R.id.tv_copy_link)
        copyLink.setOnClickListener {
            Toast.makeText(this,"초대링크를 클립보드에 복사하였습니다!",Toast.LENGTH_SHORT).show()
        }
    }
}