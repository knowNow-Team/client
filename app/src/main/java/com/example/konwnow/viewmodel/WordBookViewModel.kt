package com.example.konwnow.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.data.remote.dto.WordBook.ResponseBody
import com.example.konwnow.data.remote.retrofit.RetrofitClient
import com.example.konwnow.data.remote.retrofit.api.WordBookAPI
import com.example.konwnow.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WordBookViewModel : ViewModel() {

    var postRespons : MutableLiveData<WordBook.ResponseBody>

    init {
        postRespons= MutableLiveData()
    }

    fun getDataResponse() : MutableLiveData<ResponseBody> {
        return postRespons
    }

    fun postWordBook(token : String, Body : WordBook.CreatedWordBookBody){
        val instance = RetrofitClient.getWordClient()?.create(WordBookAPI::class.java)
        val call = instance?.postWordBook(token, Body)

        call?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                //업데이트 시켜주기.
                postRespons.postValue(response.body())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("viewmodel", "fail")
            }

        })
    }
}