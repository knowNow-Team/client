package com.example.konwnow.ui.view.group

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.App
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.ui.adapter.GroupsAdapter
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.ui.view.home.HomeFragment
import com.example.konwnow.utils.Constants
import com.example.konwnow.utils.HOMEWORD
import com.example.konwnow.utils.WORDBOOK
import com.example.konwnow.viewmodel.WordBookViewModel

class GroupActivity : AppCompatActivity(), MakeGroupInterface,ApplyGroupsInterface {
    var btnBack : ImageButton? = null
    var btnPlus : ImageButton? = null
    var btnApply : Button? = null
    private lateinit var rvGroups : RecyclerView
    private var groupsList = arrayListOf<WordBook.WordBooks>()
    private lateinit var groupsAdapter : GroupsAdapter

    private var selectedFilter = HashMap<String, Boolean>()
    private val selectedBook = ArrayList<WordBook.WordBooks>()
    private val wordBookID = ArrayList<String>()
    private lateinit var viewModel: WordBookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        viewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(WordBookViewModel::class.java)

        Log.d(Constants.TAG,MainActivity.getUserData().email)
        btnBack = findViewById(R.id.ib_back)
        btnBack!!.setOnClickListener{
            finish()
        }

        btnPlus = findViewById(R.id.ib_back)
        btnPlus!!.visibility = VISIBLE
        btnPlus!!.setImageResource(R.drawable.ic_plus_groups)
        btnPlus!!.setOnClickListener {
            val dlg = GroupDialog(this, this,0,0)
            dlg.start()
        }

        btnApply = findViewById(R.id.btn_apply_groups)
        btnApply!!.setOnClickListener {
           
            for(id in selectedBook){
                    wordBookID.add(id.wordBookID)
            }
            App.sharedPrefs.saveWordBookId(wordBookID.joinToString(","))
            App.sharedPrefs.saveCount(wordBookID.size)
            App.sharedPrefs.saveTitle(selectedBook[0].title)
            App.sharedPrefs.saveOrder(HOMEWORD.ORDER.NEWEST)
            selectedFilter.put(HOMEWORD.FILTER.doNotKnow,true)
            selectedFilter.put(HOMEWORD.FILTER.memorized,true)
            selectedFilter.put(HOMEWORD.FILTER.confused,true)
            App.sharedPrefs.savedFilter(selectedFilter)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        groupsList.clear()
        setRecycler()
    }

    private fun setRecycler() {
        groupsAdapter = GroupsAdapter(this,groupsList)
        rvGroups = findViewById(R.id.rv_groups)
        rvGroups.layoutManager = GridLayoutManager(this, 3)

        requestGroups()
    }

    private fun requestGroups() {
        viewModel.getDataReponse().observe(this, Observer {
            if (it != null) {
                groupsList.clear()
                groupsList.add(WordBook.WordBooks("?????????", null, WORDBOOK.TRASH_BOOK_ID))
                Log.d(Constants.TAG, "????????? ???????????? ??????!")
                Log.d(Constants.TAG, "response Body : ${it}")
                for (datas in it.data) {
                    groupsList.add(WordBook.WordBooks(datas.title, datas.allCount, datas.id))
                }
            } else {
                Log.d(Constants.TAG, "????????? get response null!")
            }
            rvGroups.adapter = groupsAdapter
            groupsAdapter.notifyDataSetChanged()
        })
        viewModel.getWordBook(MainActivity.getUserData().loginToken)
    }


    override fun makeWordBookClicked(name: String, type : Int, postion: Int) {
        // 0 : ??????, 1: ??????
        when(type){
            0 ->{
                viewModel.postDataResponse().observe(this, Observer {
                    if (it != null) {
                        Log.d(Constants.TAG, "????????? ????????? ??????!")
                        Log.d(Constants.TAG, "response Body : ${it}")
                        onResume()
                    } else {
                        Log.d(Constants.TAG, "????????? post response null!")
                    }
                })
                val Body = WordBook.CreatedWordBookBody(name, MainActivity.getUserData().userID)
                viewModel.postWordBook(MainActivity.getUserData().loginToken, Body)
            }
            1-> {
                viewModel.putWordBookTitleObserver().observe(this,{
                    if (it!= null){
                        Log.d(Constants.TAG, "????????? ????????? ?????? ??????!")
                        onResume()
                    }
                })
                viewModel.putWordBookTitle(MainActivity.getUserData().loginToken,groupsList[postion].wordBookID,name)
            }
        }

    }

    override fun applyGroupsCliked(selectedList: ArrayList<WordBook.WordBooks>) {
        selectedBook.clear()
        selectedBook.addAll(selectedList)
        Log.d(Constants.TAG, selectedBook.toString())
    }

    override fun deleteWordBookClicked(postion: Int) {
        viewModel.deleteWordBookobserver().observe(this,{
            if (it != null){
                Log.d(Constants.TAG, "????????? ?????? ??????!")
                onResume()
            }
        })
        val dlg: AlertDialog.Builder = AlertDialog.Builder(this,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
            dlg.setTitle("????????? ???????????? ?????????????????????????")
            dlg.setMessage("???????????? ???????????? ????????? ?????? ???????????????.")
            dlg.setNeutralButton("??????", DialogInterface.OnClickListener { dialog, which ->
                viewModel.deleteWordBook(MainActivity.getUserData().loginToken, groupsList[postion].wordBookID)
            })
            dlg.setNegativeButton("??????",DialogInterface.OnClickListener { dialog, which ->

            })
        dlg.show()
    }

    override fun editWordBookClicked(postion: Int) {
        val dlg = GroupDialog(this, this,1,postion)
        dlg.start()
    }

}

