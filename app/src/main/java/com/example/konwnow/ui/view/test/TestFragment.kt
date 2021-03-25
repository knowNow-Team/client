package com.example.konwnow.ui.view.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.FolderDTO
import com.example.konwnow.ui.adapter.FolderAdapter

class TestFragment: Fragment() {
    var folderList = arrayListOf<FolderDTO>()
    private val folderRcv: RecyclerView by lazy {
        view?.findViewById(R.id.rcv_word_folder) as RecyclerView
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        folderList.add(FolderDTO("name1"))
        folderList.add(FolderDTO("name2"))
        folderList.add(FolderDTO("name3"))
        folderList.add(FolderDTO("name4"))
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        folderRcv.setHasFixedSize(true)
        folderRcv.layoutManager = LinearLayoutManager(context)
        folderRcv.adapter = FolderAdapter(folderList)
    }


}