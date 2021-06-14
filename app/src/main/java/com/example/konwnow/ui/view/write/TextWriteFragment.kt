package com.example.konwnow.ui.view.write

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Words
import com.example.konwnow.ui.adapter.WordListAdapter
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.utils.Constants
import com.example.konwnow.utils.LottieDialog
import com.example.konwnow.viewmodel.WordViewModel
import com.example.konwnow.viewmodel.WriteViewModel


class TextWriteFragment: Fragment() {
    private lateinit var v: View
    var wordList = arrayListOf<Words.Word>()
    var textList = arrayListOf<String>()
    private lateinit var wordListRv: RecyclerView
    private lateinit var wordAdapter: WordListAdapter
    private lateinit var searchBtn: Button
    private lateinit var sentenceEdt: EditText
    lateinit var dlg: LottieDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_text_write, container, false)
        setEditText()
        setWordList()
        setButton()
        return v
    }


    private fun setEditText() {
        sentenceEdt = v.findViewById(R.id.edt_sentence)
    }

    fun CloseKeyboard() {
        var view = activity!!.currentFocus

        if(view != null){
            val inputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    private fun setButton() {
        searchBtn = v.findViewById(R.id.btn_search)
        searchBtn.setOnClickListener {
            CloseKeyboard()
            WriteActivity.clearList()
            sentenceSeparate()
        }
    }

    private fun setWordList() {
        //폴더 리스트 데이터
        wordListRv = v.findViewById(R.id.rv_word_list) as RecyclerView
        wordListRv.setHasFixedSize(true)
        wordListRv.layoutManager = LinearLayoutManager(context)
        wordAdapter = WordListAdapter(wordList)
//        wordAdapter.wordUpdateList(wordList)
        wordListRv.adapter = wordAdapter
    }

    private fun requestWords() {
        var WordViewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(
            WordViewModel::class.java
        )
        WordViewModel.getWordDataResponse().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                wordList.clear()
                wordList.addAll(it)
                wordAdapter.notifyDataSetChanged()
            } else {
                Log.d(Constants.TAG, "data get response null!")
            }
            finishDialog()
        })
        WordViewModel.postScrapWord(MainActivity.getUserData().loginToken, textList.toList())
    }


    private fun sentenceSeparate() {
        var SetenceViewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(
            WriteViewModel::class.java
        )
        showDialog()
        SetenceViewModel.getSentenceWordsListObserver().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                textList.clear()
                for (item in it.data!!) {
                    textList.add(item)
                }
                requestWords()
            } else {
                Log.d(Constants.TAG, "data get response null!")
            }
        })
        var tempString = sentenceEdt.text.toString()
        if(tempString != ""){
            SetenceViewModel.getWordFromSentence(tempString)
        }else{
            toast("문장을 입력해주세요.")
        }
    }

    private fun showDialog() {
        dlg = LottieDialog(context!!)
        dlg.start(R.raw.loading_cat)
    }

    private fun finishDialog(){
        dlg.loadingComplete()
    }

    private fun toast(message: String){ Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }


}