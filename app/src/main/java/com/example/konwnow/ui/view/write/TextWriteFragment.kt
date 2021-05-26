package com.example.konwnow.ui.view.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Words
import com.example.konwnow.ui.adapter.WordListAdapter

class TextWriteFragment: Fragment() {
    private lateinit var v: View
    var wordList = arrayListOf<Words.Word>()
    private lateinit var wordListRv: RecyclerView
    private lateinit var testIv: ImageView
    private lateinit var wordAdapter: WordListAdapter
    private lateinit var searchBtn: Button
    private lateinit var sentenceEdt: EditText


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_text_write, container, false)
        setWordList()
        setButton()
        setEditText()
        return v
    }

    private fun setEditText() {
        sentenceEdt = v.findViewById(R.id.edt_sentence)
    }

    private fun setButton() {
//        testIv = v.findViewById(R.id.iv_test)
        searchBtn = v.findViewById(R.id.btn_search)
        searchBtn.setOnClickListener {
        requestWords()
        }
    }

    private fun setWordList() {
        //폴더 리스트 데이터
        requestWords()

        wordListRv = v.findViewById(R.id.rv_word_list) as RecyclerView
        wordListRv.setHasFixedSize(true)
        wordListRv.layoutManager = LinearLayoutManager(context)
        wordAdapter = WordListAdapter()
        wordAdapter.wordUpdateList(wordList)
        wordListRv.adapter = wordAdapter
    }

    private fun requestWords() {
        wordList.clear()

//        wordList.add(Words("Complex", "복잡한",0))
//        wordList.add(Words("movie", "영화관",1))
//        wordList.add(Words("Fragment", "조각",2))
//        wordList.add(Words("Complex", "복잡한",0))
//        wordList.add(Words("movie", "영화관",0))
//        wordList.add(Words("Fragment", "조각",1))
//        wordList.add(Words("Complex", "복잡한",2))
//        wordList.add(Words("movie", "영화관",0))
//        wordList.add(Words("Fragment", "조각",1))
    }


}