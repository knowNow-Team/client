package com.example.konwnow.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.data.remote.dto.WordBook.PostWordBookResponse
import com.example.konwnow.data.remote.dto.Words
import com.example.konwnow.data.remote.retrofit.RetrofitClient
import com.example.konwnow.data.remote.retrofit.api.WordBookAPI
import com.example.konwnow.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WordBookViewModel : ViewModel() {

    var postWordBookResponse : MutableLiveData<WordBook.PostWordBookResponse> = MutableLiveData()
    var putWordResponse : MutableLiveData<WordBook.PutWordResponse> = MutableLiveData()
    var getWordBookResponse : MutableLiveData<WordBook.GetWordBookResponse> = MutableLiveData()
    var getAllWordResponse : MutableLiveData<WordBook.GetAllWordResponse> = MutableLiveData()
    var getDetailSettingResponse : MutableLiveData<WordBook.GetAllWordResponse> = MutableLiveData()
    var deleteWordResponse : MutableLiveData<Words.DeleteWordResponse> = MutableLiveData()
    var putWordBookTitleResponse : MutableLiveData<Words.DeleteWordResponse> = MutableLiveData()

    fun postDataResponse() : MutableLiveData<PostWordBookResponse> {
        return postWordBookResponse
    }

    fun putWordsResponse() : MutableLiveData<WordBook.PutWordResponse> {
        return putWordResponse
    }

    fun getDataReponse() : MutableLiveData<WordBook.GetWordBookResponse>{
        return getWordBookResponse
    }

    fun getWordDataResponse() : MutableLiveData<WordBook.GetAllWordResponse>{
        return getAllWordResponse
    }

    fun getDetailSettingObserver() : MutableLiveData<WordBook.GetAllWordResponse>{
        return getDetailSettingResponse
    }

    fun deleteWordBookobserver() : MutableLiveData<Words.DeleteWordResponse>{
        return deleteWordResponse
    }

    fun putWordBookTitleObserver() : MutableLiveData<Words.DeleteWordResponse>{
        return putWordBookTitleResponse
    }


    fun postWordBook(token : String, Body : WordBook.CreatedWordBookBody){
        val instance = RetrofitClient.getWordClient()?.create(WordBookAPI::class.java)
        val call = instance?.postWordBook(token, Body)

        call?.enqueue(object : Callback<PostWordBookResponse> {
            override fun onResponse(
                call: Call<PostWordBookResponse>,
                response: Response<PostWordBookResponse>
            ) {
                //???????????? ????????????.
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

    fun getAllWord(token : String,wordBookIds: String){
        val instance = RetrofitClient.getWordClient()?.create(WordBookAPI::class.java)
        val call = instance?.getAllWord(token,wordBookIds)

        call?.enqueue(object : Callback<WordBook.GetAllWordResponse>{
            override fun onResponse(
                call: Call<WordBook.GetAllWordResponse>,
                response: Response<WordBook.GetAllWordResponse>
            ) {
                getAllWordResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<WordBook.GetAllWordResponse>, t: Throwable) {
                Log.d(Constants.TAG, "get wordbook fail")
            }
        })
    }

    fun getDetailSettingWord(token: String, wordBookIds: String, filter : String, order : String){
        val instance = RetrofitClient.getWordClient()?.create(WordBookAPI::class.java)
        val call = instance?.getDetailSettingWord(token,wordBookIds,filter,order)

        call?.enqueue(object : Callback<WordBook.GetAllWordResponse>{
            override fun onResponse(
                call: Call<WordBook.GetAllWordResponse>,
                response: Response<WordBook.GetAllWordResponse>
            ) {
                getDetailSettingResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<WordBook.GetAllWordResponse>, t: Throwable) {
                Log.d(Constants.TAG, "get detail wordbook fail")
            }
        })

    }

    fun putWords(token : String, wordBookIds: String,Body : WordBook.PutWordRequestBody){
        val instance = RetrofitClient.getWordClient()?.create(WordBookAPI::class.java)
        val call = instance?.putWords(token, wordBookIds, Body)

        call?.enqueue(object : Callback<WordBook.PutWordResponse> {
            override fun onResponse(
                call: Call<WordBook.PutWordResponse>,
                response: Response<WordBook.PutWordResponse>
            ) {
                //???????????? ????????????.
                putWordResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<WordBook.PutWordResponse>, t: Throwable) {
                Log.d(Constants.TAG, "post word fail")
            }

        })
    }

    fun getTrashWord(token : String){
        val instance = RetrofitClient.getWordClient()?.create(WordBookAPI::class.java)
        val call = instance?.getTrashWord(token)

        call?.enqueue(object : Callback<WordBook.GetAllWordResponse>{
            override fun onResponse(
                call: Call<WordBook.GetAllWordResponse>,
                response: Response<WordBook.GetAllWordResponse>
            ) {
                getAllWordResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<WordBook.GetAllWordResponse>, t: Throwable) {
                Log.d(Constants.TAG, "get wordbook fail")
            }
        })
    }

    fun deleteWordBook(token: String, wordBookIds: String){
        val instance = RetrofitClient.getWordClient()?.create(WordBookAPI::class.java)
        val call = instance?.deleteWordBook(token, wordBookIds)

        call?.enqueue(object  : Callback<Words.DeleteWordResponse>{
            override fun onResponse(
                call: Call<Words.DeleteWordResponse>,
                response: Response<Words.DeleteWordResponse>
            ) {
                deleteWordResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Words.DeleteWordResponse>, t: Throwable) {
            }

        })
    }

    fun putWordBookTitle(token: String, wordBookIds: String, title : String){
        val instance = RetrofitClient.getWordClient()?.create(WordBookAPI::class.java)
        val call = instance?.putWordBookTitle(token, wordBookIds, WordBook.PutWordBookTitle(title))

        call?.enqueue(object  : Callback<Words.DeleteWordResponse>{
            override fun onResponse(
                call: Call<Words.DeleteWordResponse>,
                response: Response<Words.DeleteWordResponse>
            ) {
                putWordBookTitleResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Words.DeleteWordResponse>, t: Throwable) {

            }

        })
    }
}