package com.example.konwnow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.konwnow.data.remote.dto.Friend
import com.example.konwnow.data.remote.retrofit.RetrofitClient
import com.example.konwnow.data.remote.retrofit.api.FriendsAPI
import retrofit2.Call
import retrofit2.Response

class FriendViewModel : ViewModel() {

    var getCodeResponse : MutableLiveData<Friend.FriendCodeResponse> = MutableLiveData()

    fun getCodeObserver() : MutableLiveData<Friend.FriendCodeResponse>{
        return  getCodeResponse
    }

    fun getCode(token: String){
        val instance = RetrofitClient.getUserClient()?.create(FriendsAPI::class.java)
        val call = instance?.getFriendCode(token)

        call?.enqueue(object : retrofit2.Callback<Friend.FriendCodeResponse>{
            override fun onResponse(
                call: Call<Friend.FriendCodeResponse>,
                response: Response<Friend.FriendCodeResponse>
            ) {
                getCodeResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Friend.FriendCodeResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}