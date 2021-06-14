package com.example.konwnow.ui.view.home

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.App
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.ui.adapter.WordsAdapter
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.ui.view.group.GroupActivity
import com.example.konwnow.utils.Constants
import com.example.konwnow.utils.HOMEWORD
import com.example.konwnow.viewmodel.WordBookViewModel
import com.example.konwnow.viewmodel.WordViewModel


class HomeFragment : Fragment(), HomeInterface {

    private lateinit var v: View
    private lateinit var switch: Switch
    private lateinit var groupButton: TextView
    private lateinit var detailButton : ImageButton
    private lateinit var rvWords: RecyclerView
    private lateinit var wordsAdapter: WordsAdapter
    var wordsList = arrayListOf<WordBook.GetAllWordResponseData>()
    private lateinit var workBookViewModel: WordBookViewModel
    private lateinit var wordViewModel: WordViewModel

    private var wordBookID =""
    private var firstTitle =""
    private var size =0
    private var order =""
    private var filter =""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)

        detailButton = v.findViewById(R.id.ib_detail_setting)
        groupButton = v.findViewById(R.id.tv_group_text)
        switch = v.findViewById(R.id.switch_hide)
        rvWords = v.findViewById(R.id.rv_home_words) as RecyclerView

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        workBookViewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(WordBookViewModel::class.java)
        wordViewModel = ViewModelProvider(this,defaultViewModelProviderFactory).get(WordViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        wordsList.clear()
        getWordBookData()
        resetView()
    }

    private fun resetView() {
        setRecycler()
        setSwitch()
        setButton()
        switch.isChecked = true
    }


    private fun getWordBookData() {
        if(App.sharedPrefs.checkValid()!!){
            firstTitle = App.sharedPrefs.getTitle()!!
            wordBookID= App.sharedPrefs.getWordBookId()!!
            size = App.sharedPrefs.getCount()!!
        }else{
            firstTitle="단어장을 선택해주세요"
        }
        filter = App.sharedPrefs.selectedFilter()
        order = App.sharedPrefs.getOrder()!!
        if(order == HOMEWORD.ORDER.RANDOM){order = ""}

        Log.d(Constants.TAG,"저장된 단어장 데이터 : ${firstTitle} , ${wordBookID}, ${size}")
        Log.d(Constants.TAG,"저장된 세부설정 데이터 : ${filter}, order: ${order}")
    }

    private fun setRecycler() {
        if(firstTitle == "휴지통"){
            groupButton.text = "${firstTitle} ▼"
            requestTrashWord()
            detailButton.visibility = View.INVISIBLE
        }else{
            detailButton.visibility = View.VISIBLE
            if(size == 1 || size == 0){
                groupButton.text = "${firstTitle} ▼"
            }else{
                groupButton.text = "${firstTitle} 외 ${(size)?.minus(1)} ▼"
            }
            requsetSettingWord()
        }

        val swipeHelperCallBack = SwipeHelperCallBack().apply {
            setClamp(200f)
        }
        val itemTouchHelper = ItemTouchHelper(swipeHelperCallBack)
        itemTouchHelper.attachToRecyclerView(rvWords)

        wordsAdapter = WordsAdapter(wordsList, this)
        rvWords.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = wordsAdapter
            setOnTouchListener { _, _ ->
                swipeHelperCallBack.removePreviousClamp(rvWords)
                false
            }
        }
    }

    private fun requsetSettingWord(){
        workBookViewModel.getDetailSettingObserver().observe(viewLifecycleOwner,{
            if(it != null){
                Log.d(Constants.TAG, "단어 가져오기 성공!")
                wordsList.clear()
                for(datas in it.data){
                    if(datas.wordsDoc.isNotEmpty() && !datas.words.isRemoved){
                        this.wordsList.add(datas)}
                }
            }else{
                Log.d(Constants.TAG, "해당 단어 없음")
            }
            rvWords.adapter?.notifyDataSetChanged()
        })
        workBookViewModel.getDetailSettingWord(MainActivity.getUserData().loginToken,wordBookID,filter,order)
    }

    private fun requestTrashWord() {
        workBookViewModel.getWordDataResponse().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Log.d(Constants.TAG, "휴지통 가져오기 성공!")
                wordsList.clear()
                for(datas in it.data){
                    this.wordsList.add(datas)
                }
            } else {
                Log.d(Constants.TAG, "휴지통 get response null!")
            }
            wordsAdapter.notifyDataSetChanged()
        })
        workBookViewModel.getTrashWord(MainActivity.getUserData().loginToken)
    }


    private fun setSwitch() {
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                wordsAdapter.toggleUpdate(true)
                wordsAdapter.notifyDataSetChanged()
            } else {
                wordsAdapter.toggleUpdate(false)
                wordsAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setButton() {
        groupButton.setOnClickListener {
            activity?.let {
                val intent = Intent(context, GroupActivity::class.java)
                startActivity(intent)
            }
        }

        detailButton.setOnClickListener {
            activity?.let {
                val intent = Intent(context, DetailSettingActivity::class.java)
                startActivity(intent)
            }
        }

    }


    override fun changeLevelClicked(filter: String, position: Int) {
        wordViewModel.putWordFilterObserver().observe(viewLifecycleOwner,{
            if(it != null){
                Log.d(Constants.TAG, "필터 업데이트 성공!")
                onResume()
            }else {
                Log.d(Constants.TAG, "필터 업데이트 실패!")
            }
        })
        wordViewModel.putFilter(MainActivity.getUserData().loginToken,wordsList[position].id,wordsList[position].words.wordId,filter)
    }

    override fun trashClicked(position: Int) {
        wordViewModel.moveWordTrashObserver().observe(viewLifecycleOwner,{
            if(it != null){
                val dlg: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context,
                    android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth
                )
                dlg.setTitle("휴지통으로 이동되었습니다.")
                dlg.setNeutralButton("확인", DialogInterface.OnClickListener { dialog, which ->
                    onResume()
                })
                dlg.show()

            }else{
                Log.d(Constants.TAG, "휴지통으로 이동 실패!")
            }
        })
        wordViewModel.moveTrash(MainActivity.getUserData().loginToken,wordsList[position].id,wordsList[position].words.wordId)
    }

    override fun realDelete(position: Int) {
        wordViewModel.deleteWordObserver().observe(viewLifecycleOwner, {
            if(it != null){
                Log.d(Constants.TAG, "단어 완전 삭제 성공!")
                onResume()
            }else{
                Log.d(Constants.TAG, "단어 완전 삭제 실!")
            }
        })
        val dlg: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context,
            android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth
        )
        dlg.setTitle("정말로 단어를 삭제하시겠습니까?")
        dlg.setMessage("단어가 영구적으로 삭제됩니다.")
        dlg.setNeutralButton("확인", DialogInterface.OnClickListener { dialog, which ->
            wordViewModel.deleteWord(MainActivity.getUserData().loginToken, wordsList[position].words.wordId)
        })
        dlg.setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->

        })
        dlg.show()
    }

    override fun recoveryWord(position: Int) {
        wordViewModel.recoveryWordObserver().observe(viewLifecycleOwner, {
                if(it != null){
                    Log.d(Constants.TAG, "단어 복구 성공!")
                    onResume()
                }else{
                    Log.d(Constants.TAG, "단어 완전 삭제 실!")
                }
            })
        wordViewModel.recoveryWord(MainActivity.getUserData().loginToken, wordsList[position].words.wordId)
    }
}