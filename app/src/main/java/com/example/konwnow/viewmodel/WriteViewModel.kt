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

    var wordsList : MutableLiveData<Words.getWordResponseBody> = MutableLiveData()


    fun getWordDataObserver() : MutableLiveData<Words.getWordResponseBody>{
        return wordsList
    }


    fun getWordFromImage(image: MultipartBody.Part){
        val instance = RetrofitClient.getUserClient()?.create(WriteAPI::class.java)
        val call = instance?.getWordFromImage(image)
        call?.enqueue(object : Callback<Words.getWordResponseBody> {
            override fun onResponse(
                call: Call<Words.getWordResponseBody>,
                WordResponse: Response<Words.getWordResponseBody>
            ) {
                Log.d("viewmodel", "success")

                //업데이트 시켜주기.
                wordsList.postValue(WordResponse.body())
            }

            override fun onFailure(call: Call<Words.getWordResponseBody>, t: Throwable) {
                Log.d("viewmodel", t.message.toString())
            }

        })
    }


}