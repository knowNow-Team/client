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
    var moveWordTarshResponse : MutableLiveData<Words.MoveTrashResponse> = MutableLiveData()
    var delteWordResponse : MutableLiveData<Words.DeleteWordResponse> = MutableLiveData()
    var recoveryWordResponse : MutableLiveData<Words.DeleteWordResponse> = MutableLiveData()

    fun getWordDataResponse() : MutableLiveData<Words.GetWordInfoResponseBody>{
        return getAllWordResponse
    }

    fun putWordFilterObserver() : MutableLiveData<Words.PutFilterResponse>{
        return putWordFilterResponse
    }

    fun moveWordTrashObserver() : MutableLiveData<Words.MoveTrashResponse>{
        return moveWordTarshResponse
    }

    fun deleteWordObserver() : MutableLiveData<Words.DeleteWordResponse>{
        return delteWordResponse
    }

    fun recoveryWordObserver() : MutableLiveData<Words.DeleteWordResponse>{
        return recoveryWordResponse
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
        val call = instances?.putWordFilter(token, wordBookId, wordId, Words.PutFilterBody(filter))

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

    fun moveTrash(token : String, wordBookId : String, wordId : String){
        val instances =  RetrofitClient.getWordClient()?.create(WordAPI::class.java)
        val call = instances?.moveWordTrash(token, wordBookId, wordId)

        call?.enqueue(object : Callback<Words.MoveTrashResponse>{
            override fun onResponse(
                call: Call<Words.MoveTrashResponse>,
                response: Response<Words.MoveTrashResponse>
            ) {
                moveWordTarshResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Words.MoveTrashResponse>, t: Throwable) {
                Log.d(Constants.TAG, "move trash fail")
            }

        })

    }

    fun deleteWord(token: String, wordId: String){
        val instances =  RetrofitClient.getWordClient()?.create(WordAPI::class.java)
        val call = instances?.delteWord(token,wordId)

        call?.enqueue(object : Callback<Words.DeleteWordResponse>{
            override fun onResponse(
                call: Call<Words.DeleteWordResponse>,
                response: Response<Words.DeleteWordResponse>
            ) {
                delteWordResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Words.DeleteWordResponse>, t: Throwable) {
                Log.d(Constants.TAG, "delete word fail")
            }

        })
    }

    fun recoveryWord(token: String, wordId: String){
        val instances =  RetrofitClient.getWordClient()?.create(WordAPI::class.java)
        val call = instances?.recoveryWord(token,wordId)

        call?.enqueue(object  : Callback<Words.DeleteWordResponse>{
            override fun onResponse(
                call: Call<Words.DeleteWordResponse>,
                response: Response<Words.DeleteWordResponse>
            ) {
               recoveryWordResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Words.DeleteWordResponse>, t: Throwable) {
                Log.d(Constants.TAG, "recovery word fail")
            }

        })
    }
}