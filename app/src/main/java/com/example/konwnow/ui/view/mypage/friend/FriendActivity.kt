package com.example.konwnow.ui.view.mypage.friend

import android.content.DialogInterface
import android.os.Bundle
import android.widget.AlphabetIndexer
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Friend
import com.example.konwnow.data.remote.retrofit.api.FriendsAPI
import com.example.konwnow.ui.adapter.FriendAdapter
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.ui.view.group.GroupDialog
import com.example.konwnow.utils.LottieDialog
import com.example.konwnow.viewmodel.FriendViewModel


class FriendActivity : AppCompatActivity(), MakeFriendInterface {

    private var friendsItem = arrayListOf<Friend>()
    private lateinit var friendsAdapter : FriendAdapter
    private lateinit var friendViewModel: FriendViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend)

        friendViewModel = ViewModelProvider(this,defaultViewModelProviderFactory).get(FriendViewModel::class.java)

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

        val copyLink = findViewById<ImageButton>(R.id.ib_plus_friend)
        copyLink.setOnClickListener {
            val dlg = FriendMakeDialog(this,this)
            dlg.start()
        }
    }

    override fun makeFriendClicked(code: String) {
        friendViewModel.postFriendObserver().observe(this, {
            if (it != null) {
                val builder = AlertDialog.Builder(this)
                val dialogView = layoutInflater.inflate(R.layout.dialog_friend_finish, null)
                val tvMesaage = dialogView.findViewById<TextView>(R.id.tv_message)

                tvMesaage.text = "${it.data.nickName}님과 친구가 되었습니다!"
                builder.setView(dialogView).setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    onResume()
                }
                    .show()
            } else {

            }
        })
        friendViewModel.postFriend(MainActivity.getUserData().loginToken,code)
    }
}