package com.example.konwnow.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.data.remote.dto.WordBook.PostWordBookResponse
import com.example.konwnow.data.remote.dto.Words
import com.example.konwnow.data.remote.retrofit.RetrofitClient
import com.example.konwnow.data.remote.retrofit.api.WordAPI
import com.example.konwnow.data.remote.retrofit.api.WordBookAPI
import com.example.konwnow.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WordViewModel : ViewModel() {

    var getAllWordResponse : MutableLiveData<Words.GetWordInfoResponseBody> = MutableLiveData()
    var putWordFilterResponse : MutableLiveData<Words.PutFilterResponse> = MutableLiveData()

    fun getWordDataResponse() : MutableLiveData<Words.GetWordInfoResponseBody>{
        return getAllWordResponse
    }

    fun putWordFilterObserver() : MutableLiveData<Words.PutFilterResponse>{
        return putWordFilterResponse
    }


    fun postScrapWord(token : String, content : List<String>){
        val instance = RetrofitClient.getWordClient()?.create(WordAPI::class.java)
        val call = instance?.postScrapWord(token, Words.WordRequestBody(content))

        call?.enqueue(object : Callback<Words.GetWordInfoResponseBody> {
            override fun onResponse(
                call: Call<Words.GetWordInfoResponseBody>,
                response: Response<Words.GetWordInfoResponseBody>
            ) {
                //업데이트 시켜주기.
                getAllWordResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Words.GetWordInfoResponseBody>, t: Throwable) {
                Log.d(Constants.TAG, "post scrap fail")
            }
        })
    }

    fun putFilter(token : String, wordBookId : String, wordId : String, filter : String){
        val instances =  RetrofitClient.getWordClient()?.create(WordAPI::class.java)
        val call = instances?.putWordFilter(token, wordBookId, wordId,Words.PutFilterBody(filter))

        call?.enqueue(object  : Callback<Words.PutFilterResponse>{
            override fun onResponse(
                call: Call<Words.PutFilterResponse>,
                response: Response<Words.PutFilterResponse>
            ) {
                putWordFilterResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Words.PutFilterResponse>, t: Throwable) {
                Log.d(Constants.TAG, "put filter fail")
            }

        })
    }
}