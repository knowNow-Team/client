package com.example.konwnow.ui.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Words
import com.example.konwnow.ui.adapter.WordsAdapter
import com.example.konwnow.ui.view.group.GroupActivity

class HomeFragment : Fragment() {

    private lateinit var v: View
    private lateinit var switch: Switch
    private lateinit var groupButton: TextView
    private lateinit var rvWords: RecyclerView
    private lateinit var wordsAdapter: WordsAdapter
    private var wordsList = arrayListOf<Words>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)

        setSwitch()
        setButton()
        setRecycler()
        requestWords()

        return v
    }

    private fun setRecycler() {
        requestWords()

        wordsAdapter = WordsAdapter()
        wordsAdapter.wordsUpdateList(wordsList)

        rvWords = v.findViewById(R.id.rv_home_words) as RecyclerView
        val swipeHelperCallBack = SwipeHelperCallBack().apply {
            setClamp(200f)
        }
        val itemTouchHelper = ItemTouchHelper(swipeHelperCallBack)
        itemTouchHelper.attachToRecyclerView(rvWords)

        rvWords.layoutManager = LinearLayoutManager(context)
        rvWords.adapter = wordsAdapter
        rvWords.setOnTouchListener { _, _ ->
            swipeHelperCallBack.removePreviousClamp(rvWords)
            false
        }


    }

    private fun requestWords() {
        wordsList.clear()

        wordsList.add(Words("Complex", "복잡한"))
        wordsList.add(Words("movie", "영화관"))
        wordsList.add(Words("Fragment", "조각"))
        wordsList.add(Words("Complex", "복잡한"))
        wordsList.add(Words("movie", "영화관"))
        wordsList.add(Words("Fragment", "조각"))
        wordsList.add(Words("Complex", "복잡한"))
        wordsList.add(Words("movie", "영화관"))
        wordsList.add(Words("Fragment", "조각"))
    }

    private fun setButton() {
        groupButton = v.findViewById(R.id.tv_group_text)
        groupButton.setOnClickListener {
            activity?.let {
                val intent = Intent(context, GroupActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setSwitch() {
        switch = v.findViewById(R.id.switch_hide)
        switch.isChecked = true
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

            } else {

            }
        }
    }
}