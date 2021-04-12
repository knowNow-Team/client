package com.example.konwnow.ui.view.write

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.data.model.dto.Words
import com.example.konwnow.ui.adapter.WordListAdapter


class ImageWriteFragment: Fragment() {
    private lateinit var v: View
    var wordList = arrayListOf<Words>()
    private lateinit var wordListRv: RecyclerView
    private lateinit var wordAdapter: WordListAdapter
    private lateinit var imageWriteIv: ImageView
    var myUri = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_image_write, container, false)
        setWordList()
        setImageWrite()
        return v
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

    private fun setImageWrite(){
        imageWriteIv = v.findViewById<ImageView>(R.id.iv_image_write)
        val dialog = ImageWriteDialog()
        dialog.setSuccessListener { uri ->
            setImage(uri)
        }
        imageWriteIv.setOnClickListener {
            dialog.show(fragmentManager!!, dialog.tag)
        }

        imageWriteIv.setOnLongClickListener {
            if(!myUri.equals("")){
                val imageDialog = ImageDialog(context!!)
                imageDialog.start(myUri.toUri())
            }
            true
        }
    }

    private fun requestWords() {
        wordList.clear()

        wordList.add(Words("Complex", "복잡한", 0))
        wordList.add(Words("movie", "영화관", 1))
        wordList.add(Words("Fragment", "조각", 2))
        wordList.add(Words("Complex", "복잡한", 0))
        wordList.add(Words("movie", "영화관", 0))
        wordList.add(Words("Fragment", "조각", 1))
        wordList.add(Words("Complex", "복잡한", 2))
        wordList.add(Words("movie", "영화관", 0))
        wordList.add(Words("Fragment", "조각", 1))
    }

    private fun setImage(uri: Uri) {
        Log.d("Uri", uri.toString())
        myUri = uri.toString()
        if(myUri == ""){
            imageWriteIv.background = ResourcesCompat.getDrawable(resources, R.drawable.img_addimage, null)
        }else{
            imageWriteIv.setBackgroundColor(ContextCompat.getColor(context!!, R.color.black))
            imageWriteIv.setImageURI(uri)
        }
    }


    private fun toast(message: String){ Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }
}

