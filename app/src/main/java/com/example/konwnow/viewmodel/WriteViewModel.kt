package com.example.konwnow.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.konwnow.data.remote.dto.Users
import com.example.konwnow.data.remote.dto.Words
import com.example.konwnow.data.remote.retrofit.RetrofitClient
import com.example.konwnow.data.remote.retrofit.api.LoginAPi
import com.example.konwnow.data.remote.retrofit.api.WriteAPI
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WriteViewModel : ViewModel() {

    var imageWordsList : MutableLiveData<Words.GetWordFromImageResponseBody> = MutableLiveData()
    var sentenceWordsList : MutableLiveData<Words.GetWordFromSentenceResponseBody> = MutableLiveData()


    fun getImageWordsListObserver() : MutableLiveData<Words.GetWordFromImageResponseBody>{
        return imageWordsList
    }

    fun getSentenceWordsListObserver() : MutableLiveData<Words.GetWordFromSentenceResponseBody>{
        return sentenceWordsList
    }


    fun getWordFromImage(image: MultipartBody.Part){
        val instance = RetrofitClient.getUserClient()?.create(WriteAPI::class.java)
        val call = instance?.getWordFromImage(image)
        call?.enqueue(object : Callback<Words.GetWordFromImageResponseBody> {
            override fun onResponse(
                call: Call<Words.GetWordFromImageResponseBody>,
                WordResponse: Response<Words.GetWordFromImageResponseBody>
            ) {
                Log.d("viewmodel", "success")

                //업데이트 시켜주기.
                imageWordsList.postValue(WordResponse.body())
            }

            override fun onFailure(call: Call<Words.GetWordFromImageResponseBody>, t: Throwable) {
                Log.d("viewmodel", t.message.toString())
            }

        })
    }

    fun getWordFromSentence(sentence: String){
        val instance = RetrofitClient.getUserClient()?.create(WriteAPI::class.java)
        val call = instance?.getWordFromSentence(Words.SentenceRequestBody(sentence))
        call?.enqueue(object : Callback<Words.GetWordFromSentenceResponseBody> {
            override fun onResponse(
                call: Call<Words.GetWordFromSentenceResponseBody>,
                WordResponse: Response<Words.GetWordFromSentenceResponseBody>
            ) {
                Log.d("리스폰스", WordResponse.toString())

                //업데이트 시켜주기.
                sentenceWordsList.postValue(WordResponse.body())
            }

            override fun onFailure(call: Call<Words.GetWordFromSentenceResponseBody>, t: Throwable) {
                Log.d("viewmodel", t.message.toString())
            }

        })
    }


}