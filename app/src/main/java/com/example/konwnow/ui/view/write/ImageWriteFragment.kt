package com.example.konwnow.ui.view.write

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
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
import com.example.konwnow.utils.Constants.TAG
import com.example.konwnow.utils.LottieDialog
import com.example.konwnow.viewmodel.WordViewModel
import com.example.konwnow.viewmodel.WriteViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class ImageWriteFragment: Fragment() {
    private lateinit var v: View
    var textList = arrayListOf<String>()
    private var wordList = arrayListOf<Words.Word>()
    private lateinit var wordListRv: RecyclerView
    private lateinit var wordAdapter: WordListAdapter
    private lateinit var imageWriteIv: ImageView
    private lateinit var viewModel: WriteViewModel
    private lateinit var wordViewModel: WordViewModel
    var myUri=Uri.parse("")
    lateinit var dlg: LottieDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_image_write, container, false)
        viewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(WriteViewModel::class.java)
        wordViewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(WordViewModel::class.java)
        setWordList()
        setImageWrite()
        return v
    }

    override fun onResume() {
        super.onResume()
        if(myUri.toString() != ""){
            WriteActivity.clearList()
            showDialog()
        }
    }

    private fun showDialog() {
        dlg = LottieDialog(context!!)
        dlg.start(R.raw.loading_cat)
    }

    private fun finishDialog(){
        dlg.loadingComplete()
    }

    private fun getWordFromImage() {
        Log.d("실행","된다...")
        viewModel.getImageWordsListObserver().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                textList.clear()
                Log.d(Constants.TAG, "이미지로 단어 가져오기 성공!")
                for (item in it.data!!) {
                    textList.add(item.text)
                }
                Log.d("실행", textList.toString())
                requestWords()
            } else {
                Log.d(Constants.TAG, "data get response null!")
            }
        })
        var file = File(myUri.path)
        var filepart = MultipartBody.Part.createFormData("imageFile", file.getName(), RequestBody.create(
            "multipart/form-data/*".toMediaTypeOrNull(), file))
        viewModel.getWordFromImage(filepart)
    }

    private fun requestWords() {
        wordViewModel.getWordDataResponse().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                wordList.clear()
                wordList.addAll(it)
                wordAdapter.notifyDataSetChanged()
            } else {
                Log.d(Constants.TAG, "data get response null!")
            }
            finishDialog()
        })
        wordViewModel.postScrapWord(MainActivity.getUserData().loginToken,textList.toList())
    }


    private fun setWordList() {
        //폴더 리스트 데이터
        wordListRv = v.findViewById(R.id.rv_word_list) as RecyclerView
        wordListRv.setHasFixedSize(true)
        wordListRv.layoutManager = LinearLayoutManager(context)
        wordAdapter = WordListAdapter(wordList)
        wordListRv.adapter = wordAdapter
    }

    private fun setImageWrite(){
        imageWriteIv = v.findViewById<ImageView>(R.id.iv_image_write)
        val dialog = ImageWriteDialog()
        dialog.setSuccessListener { uri ->
            setImage(uri)
        }
        imageWriteIv.setOnClickListener {
            myUri ="".toUri()
            dialog.show(fragmentManager!!, dialog.tag)
        }

        imageWriteIv.setOnLongClickListener {
            if(!myUri.equals("")){
                val imageDialog = ImageDialog(context!!)
                imageDialog.start(myUri)
            }
            true
        }
    }


    private fun setImage(uri: Uri) {
        Log.d("Uri", uri.toString())
        myUri = uri
        if(myUri.toString() == ""){
            imageWriteIv.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.img_addimage,
                null
            )
        }else{
            getWordFromImage()
            imageWriteIv.setBackgroundColor(ContextCompat.getColor(context!!, R.color.black))
            imageWriteIv.setImageURI(uri)
        }
    }


    private fun toast(message: String){ Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }
}

