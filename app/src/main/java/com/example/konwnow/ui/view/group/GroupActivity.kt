package com.example.konwnow.ui.view.group

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.ui.adapter.GroupsAdapter
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.ui.view.home.HomeFragment
import com.example.konwnow.utils.Constants
import com.example.konwnow.utils.WORDBOOK
import com.example.konwnow.viewmodel.WordBookViewModel

class GroupActivity : AppCompatActivity(), MakeGroupInterface,ApplyGroupsInterface {
    var btnBack : ImageButton? = null
    var btnPlus : ImageButton? = null
    var btnApply : Button? = null
    private lateinit var rvGroups : RecyclerView
    private var groupsList = arrayListOf<WordBook.WordBooks>()
    private lateinit var groupsAdapter : GroupsAdapter

    private val selectedBook = ArrayList<WordBook.WordBooks>()
    private val wordBookID = ArrayList<String>()
    private lateinit var viewModel: WordBookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        viewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(WordBookViewModel::class.java)


        btnBack = findViewById(R.id.ib_back)
        btnBack!!.setOnClickListener{
            finish()
        }

        btnPlus = findViewById(R.id.ib_back)
        btnPlus!!.visibility = VISIBLE
        btnPlus!!.setImageResource(R.drawable.ic_plus_groups)
        btnPlus!!.setOnClickListener {
            val dlg = GroupDialog(this, this)
            dlg.start()
        }

        btnApply = findViewById(R.id.btn_apply_groups)
        btnApply!!.setOnClickListener {
            val intent = Intent()

            for(id in selectedBook){
                wordBookID.add(id.wordBookID)
            }
            intent.putStringArrayListExtra("selected",wordBookID)
            intent.putExtra("first",selectedBook[0].title)
            setResult(1004,intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        setRecycler()
    }

    private fun setRecycler() {
        groupsList.clear()
        groupsList.add(WordBook.WordBooks("휴지통", null, WORDBOOK.TRASH_BOOK_ID))

        groupsAdapter = GroupsAdapter(this)
        rvGroups = findViewById(R.id.rv_groups)
        rvGroups.layoutManager = GridLayoutManager(this, 3)

        requestGroups()
    }

    private fun requestGroups() {
        viewModel.getDataReponse().observe(this, Observer {
            if (it != null) {
                Log.d(Constants.TAG, "단어장 가져오기 성공!")
                Log.d(Constants.TAG, "response Body : ${it}")
                for (datas in it.data) {
                    groupsList.add(WordBook.WordBooks(datas.title, datas.allCount, datas.id))
                }
            } else {
                Log.d(Constants.TAG, "단어장 get response null!")
            }
            groupsAdapter.groupsUpdateList(groupsList)
            rvGroups.adapter = groupsAdapter
            groupsAdapter.notifyDataSetChanged()
        })
        viewModel.getWordBook(MainActivity.getUserData().loginToken)
    }


    override fun makeWordBookClicked(name: String) {
        viewModel.postDataResponse().observe(this, Observer {
            if (it != null) {
                Log.d(Constants.TAG, "단어장 만들기 성공!")
                Log.d(Constants.TAG, "response Body : ${it}")
                groupsAdapter.makeClicked(name)
            } else {
                Log.d(Constants.TAG, "단어장 post response null!")
            }
        })
        val Body = WordBook.CreatedWordBookBody(name, MainActivity.getUserData().userID)
        viewModel.postWordBook(MainActivity.getUserData().loginToken, Body)
    }

    override fun applyGroupsCliked(selectedList: ArrayList<WordBook.WordBooks>) {
        selectedBook.clear()
        selectedBook.addAll(selectedList)
        Log.d(Constants.TAG, selectedBook.toString())
    }
}