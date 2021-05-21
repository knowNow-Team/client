package com.example.konwnow.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.data.remote.dto.WordBook.PostWordBookResponse
import com.example.konwnow.data.remote.retrofit.RetrofitClient
import com.example.konwnow.data.remote.retrofit.api.WordBookAPI
import com.example.konwnow.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WordBookViewModel : ViewModel() {

    var postWordBookResponse : MutableLiveData<WordBook.PostWordBookResponse>
    var getWordBookResponse : MutableLiveData<WordBook.GetWordBookResponse>

    init {
        postWordBookResponse= MutableLiveData()
        getWordBookResponse = MutableLiveData()
    }

    fun postDataResponse() : MutableLiveData<PostWordBookResponse> {
        return postWordBookResponse
    }

    fun getDataReponse() : MutableLiveData<WordBook.GetWordBookResponse>{
        return getWordBookResponse
    }


    fun postWordBook(token : String, Body : WordBook.CreatedWordBookBody){
        val instance = RetrofitClient.getWordClient()?.create(WordBookAPI::class.java)
        val call = instance?.postWordBook(token, Body)

        call?.enqueue(object : Callback<PostWordBookResponse> {
            override fun onResponse(
                call: Call<PostWordBookResponse>,
                response: Response<PostWordBookResponse>
            ) {
                //업데이트 시켜주기.
                postWordBookResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<PostWordBookResponse>, t: Throwable) {
                Log.d(Constants.TAG, "post wordbook fail")
            }

        })
    }

    fun getWordBook(token : String){
        val instance = RetrofitClient.getWordClient()?.create(WordBookAPI::class.java)
        val call = instance?.getWordBook(token)

        call?.enqueue(object : Callback<WordBook.GetWordBookResponse>{
            override fun onResponse(
                call: Call<WordBook.GetWordBookResponse>,
                response: Response<WordBook.GetWordBookResponse>
            ) {
                getWordBookResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<WordBook.GetWordBookResponse>, t: Throwable) {
                Log.d(Constants.TAG, "get wordbook fail")
            }

        })
    }
}