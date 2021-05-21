package com.example.konwnow.ui.view.group

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.local.UserDatabase
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.ui.adapter.GroupsAdapter
import com.example.konwnow.utils.Constants
import com.example.konwnow.viewmodel.WordBookViewModel

class GroupActivity : AppCompatActivity(), MakeGroupInterface  {
    var btnBack : ImageButton? = null
    var btnPlus : ImageButton? = null
    var btnApply : Button? = null
    private lateinit var rvGroups : RecyclerView
    private var groupsList = arrayListOf<WordBook.WordBooks>()
    private lateinit var groupsAdapter : GroupsAdapter
    private lateinit var db : UserDatabase

    var loginToken : String =""
    var userId : Int = 0

    private lateinit var viewModel: WordBookViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        viewModel = ViewModelProvider(this,defaultViewModelProviderFactory).get(WordBookViewModel::class.java)

        getUserInfo()

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

        btnApply = findViewById(R.id.btn_apply_groups)
        btnApply!!.setOnClickListener {
            groupsAdapter.applySelectedGroups()
            setResult(1)
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        setRecycler()
    }

    @SuppressLint("StaticFieldLeak")
    private fun getUserInfo() {
        db = UserDatabase.getInstance(this)!!
        val insertTask = object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                val users = db.userDao().getAll()
                userId = users.userID
                loginToken = users.loginToken
            }
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
            }
        }
        insertTask.execute()

    }

    private fun setRecycler() {
        groupsList.clear()
        groupsList.add(WordBook.WordBooks("휴지통",0))

        groupsAdapter = GroupsAdapter()
        rvGroups = findViewById(R.id.rv_groups)
        rvGroups.layoutManager = GridLayoutManager(this, 3)

        requestGroups()
    }

    private fun requestGroups() {
        viewModel.getDataReponse().observe(this, Observer {
            if (it != null){
                Log.d(Constants.TAG,"단어장 가져오 성공!")
                Log.d(Constants.TAG,"response Body : ${it}")
                for (datas in it.data){
                    groupsList.add(WordBook.WordBooks(datas.title,datas.allCount))
                }
            }else {
                Log.d(Constants.TAG,"단어장 get response null!")
            }
            groupsAdapter.groupsUpdateList(groupsList)
            rvGroups.adapter = groupsAdapter
            groupsAdapter.notifyDataSetChanged()
        })
        viewModel.getWordBook(loginToken)
    }

    override fun makeWordBookClicked(name: String) {
        viewModel.postDataResponse().observe(this, Observer {
            if(it != null){
                Log.d(Constants.TAG,"단어장 만들기 성공!")
                Log.d(Constants.TAG,"response Body : ${it}")
            }else {
                Log.d(Constants.TAG,"단어장 post response null!")
            }
        })
        val Body = WordBook.CreatedWordBookBody(name,userId)
        viewModel.postWordBook(loginToken,Body)
        groupsAdapter.notifyDataSetChanged()
    }
}