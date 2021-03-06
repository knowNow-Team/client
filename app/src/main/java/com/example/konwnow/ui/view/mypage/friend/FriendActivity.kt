package com.example.konwnow.ui.view.mypage.friend

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Friend
import com.example.konwnow.ui.adapter.FriendAdapter
import com.example.konwnow.ui.adapter.WordsAdapter
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.ui.view.home.SwipeHelperCallBack
import com.example.konwnow.utils.Constants
import com.example.konwnow.viewmodel.FriendViewModel


class FriendActivity : AppCompatActivity(), MakeFriendInterface {

    private var friendsItem = arrayListOf<Friend.FriendData>()
    private lateinit var friendsAdapter: FriendAdapter
    private lateinit var friendViewModel: FriendViewModel
    private lateinit var rvFriend: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend)

        friendViewModel = ViewModelProvider(
            this,
            defaultViewModelProviderFactory
        ).get(FriendViewModel::class.java)

        setToolbar()
        setRecycler()
    }

    override fun onResume() {
        super.onResume()
        friendsItem.clear()
        setToolbar()
        setRecycler()
    }

    private fun setRecycler() {
        rvFriend = findViewById(R.id.rv_friend)
        requestFriend()

        friendsAdapter = FriendAdapter(friendsItem, this)
        rvFriend.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = friendsAdapter
        }
    }

    private fun requestFriend() {
        friendViewModel.getFriendListObserver().observe(this, {
            if (it != null) {
                Log.d(Constants.TAG, "?????? ???????????? ??????!")
                friendsItem.clear()
                friendsItem.addAll(it.data)
            }
            rvFriend.adapter?.notifyDataSetChanged()
        })
        friendViewModel.getFriend(MainActivity.getUserData().loginToken)
    }


    private fun setToolbar() {
        val title = findViewById<TextView>(R.id.tv_title)
        title.text = "????????????"

        val btnBack = findViewById<ImageButton>(R.id.ib_back)
        btnBack.setOnClickListener {
            setResult(1)
            finish()
        }

        val copyLink = findViewById<ImageButton>(R.id.ib_plus_friend)
        copyLink.setOnClickListener {
            val dlg = FriendMakeDialog(this, this)
            dlg.start()
        }
    }

    override fun makeFriendClicked(code: String) {
        friendViewModel.postFriendObserver().observe(this, {
            if (it != null) {
                val builder = AlertDialog.Builder(this)
                val dialogView = layoutInflater.inflate(R.layout.dialog_friend_finish, null)
                val tvMesaage = dialogView.findViewById<TextView>(R.id.tv_message)

                tvMesaage.text = "${it.data.nickName}?????? ????????? ???????????????!"
                builder.setView(dialogView)
                    .setPositiveButton("??????") { dialogInterface: DialogInterface, i: Int ->
                        onResume()
                    }
                    .show()
            } else {
                Toast.makeText(this, "?????? ??????????????? ???????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show()
            }
        })
        friendViewModel.postFriend(MainActivity.getUserData().loginToken, code)
    }

    override fun deleteFriendClicked(friendId: Int) {
        friendViewModel.deleteFriendObserver().observe(this, {
            if (it != null && it.statusCode == 200) {
                onResume()
            }
        })
        val dlg: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(
            this,
            android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth
        )
        dlg.setTitle("????????? ????????? ?????????????????????????")
        dlg.setNeutralButton("??????", DialogInterface.OnClickListener { dialog, which ->
            friendViewModel.deleteFriend(MainActivity.getUserData().loginToken, friendId)
        })
        dlg.setNegativeButton("??????", DialogInterface.OnClickListener { dialog, which ->

        })
        dlg.show()
    }
}