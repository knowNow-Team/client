package com.example.konwnow.ui.view.write

import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
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
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileDescriptor
import java.io.IOException


class ImageWriteFragment: Fragment() {
    private lateinit var v: View
    var wordList = arrayListOf<String>()
    private lateinit var wordListRv: RecyclerView
    private lateinit var wordAdapter: WordListAdapter
    private lateinit var imageWriteIv: ImageView
    private lateinit var viewModel: WriteViewModel
    var myUri = ""

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


    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        var parcelFileDescriptor: ParcelFileDescriptor? = null
        return try {
            parcelFileDescriptor = activity!!.contentResolver.openFileDescriptor(uri, "r")
            val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor.close()
            image
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load image.", e)
            null
        } finally {
            try {
                parcelFileDescriptor?.close()
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e(TAG, "Error closing ParcelFile Descriptor")
            }
        }
    }

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

        val bitmap = getBitmapFromUri(myUri.toUri())

        val cursor: Cursor = context!!.getContentResolver().query(
            Uri.parse(myUri.toString()),
            null,
            null,
            null,
            null
        )!!
        cursor.moveToFirst()
        var mediaPath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))
        //커서 사용해서 경로 확인

        Log.d("이미지",mediaPath)

        val file = File(mediaPath)

        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        viewModel.getWordFromImage(body)
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
            requestWords()
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


    private fun setImage(uri: Uri) {
        Log.d("Uri", uri.toString())
        //TODO: OCR요청
        myUri = uri.toString()
        if(myUri == ""){
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

