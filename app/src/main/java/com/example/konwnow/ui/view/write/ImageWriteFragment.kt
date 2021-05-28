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
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.R
import com.example.konwnow.ui.adapter.WordListAdapter
import com.example.konwnow.utils.Constants
import com.example.konwnow.utils.Constants.TAG
import com.example.konwnow.viewmodel.WriteViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class ImageWriteFragment: Fragment() {
    private lateinit var v: View
    var wordList = arrayListOf<String>()
    private lateinit var wordListRv: RecyclerView
    private lateinit var wordAdapter: WordListAdapter
    private lateinit var imageWriteIv: ImageView
    private lateinit var viewModel: WriteViewModel
    var myUri=Uri.parse("")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_image_write, container, false)
        setWordList()
        setImageWrite()
        return v
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onResume() {
        super.onResume()
        if(myUri.toString() != ""){
            Log.d(TAG, "실행")
            requestWords()
        }
        Log.d(TAG, "실행 안됨")

    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun requestWords() {
        viewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(WriteViewModel::class.java)
        viewModel.getWordDataObserver().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Log.d(Constants.TAG, "단어장 가져오기 성공!")
                wordList.clear()
                for (item in it.data!!) {
                    wordList.add(item.text)
                }
            } else {
                Log.d(Constants.TAG, "data get response null!")
            }
        })

        Log.d(TAG, "tlqkf:" + myUri)

        val file = File(myUri.path)
//        val requestFile = file.asRequestBody("file/*".toMediaTypeOrNull())
//        val requestFile = RequestBody.create("mulipart/form-data".toMediaTypeOrNull(),file)
        var filepart = MultipartBody.Part.createFormData("imageFile", file.getName(), RequestBody.create(
            "multipart/form-data/*".toMediaTypeOrNull(), file));
        viewModel.getWordFromImage(filepart)
    }

    private fun setWordList() {
        //폴더 리스트 데이터
        wordListRv = v.findViewById(R.id.rv_word_list) as RecyclerView
        wordListRv.setHasFixedSize(true)
        wordListRv.layoutManager = LinearLayoutManager(context)
        wordAdapter = WordListAdapter()
//        wordAdapter.wordUpdateList(wordList)
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
            imageWriteIv.setBackgroundColor(ContextCompat.getColor(context!!, R.color.black))
            imageWriteIv.setImageURI(uri)
        }
    }


    private fun toast(message: String){ Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }
}

