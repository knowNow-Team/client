package com.example.konwnow.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.konwnow.data.remote.dto.TestLog
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

//    var getAllWordResponse : MutableLiveData<Words.GetWordInfoResponseBody> = MutableLiveData()
    var getValidWordResponse : MutableLiveData<ArrayList<Words.Word>> = MutableLiveData()
    var validWords: ArrayList<Words.Word> = ArrayList()


    fun getWordDataResponse() : MutableLiveData<ArrayList<Words.Word>>{
        return getValidWordResponse
    }


    fun postScrapWord(token : String, content : List<String>){
        val instance = RetrofitClient.getWordClient()?.create(WordAPI::class.java)
        val call = instance?.postScrapWord(token, Words.WordRequestBody(content))

        call?.enqueue(object : Callback<Words.GetWordInfoResponseBody> {
            override fun onResponse(
                call: Call<Words.GetWordInfoResponseBody>,
                response: Response<Words.GetWordInfoResponseBody>
            ) {
                for(item in response.body()!!.data){
                    if(item == null){
                        Log.d("아이템","널널")
                    }else{
                        validWords.add(item)
                    }
                }
                getValidWordResponse.postValue(validWords)
            }

            override fun onFailure(call: Call<Words.GetWordInfoResponseBody>, t: Throwable) {
                Log.d(Constants.TAG, "post scrap fail")
            }
        })
    }
}