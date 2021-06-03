package com.example.konwnow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.konwnow.data.remote.dto.Friend
import com.example.konwnow.data.remote.retrofit.RetrofitClient
import com.example.konwnow.data.remote.retrofit.api.FriendsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendViewModel : ViewModel() {

    var getCodeResponse : MutableLiveData<Friend.FriendCodeResponse> = MutableLiveData()
    var friendResponse : MutableLiveData<Friend.FriendResponse> = MutableLiveData()

    fun getCodeObserver() : MutableLiveData<Friend.FriendCodeResponse>{
        return  getCodeResponse
    }

    fun postFriendObserver() : MutableLiveData<Friend.FriendResponse>{
        return friendResponse
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

    fun postFriend(token: String, friendToken : String){
        val instance = RetrofitClient.getUserClient()?.create(FriendsAPI::class.java)
        val call = instance?.postFriend(token,Friend.PostFriendBody(friendToken))

        call?.enqueue(object  : Callback<Friend.FriendResponse>{
            override fun onResponse(
                call: Call<Friend.FriendResponse>,
                response: Response<Friend.FriendResponse>
            ) {
                friendResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Friend.FriendResponse>, t: Throwable) {

            }
        })
    }

    fun getFriend(token: String){
        val instance = RetrofitClient.getUserClient()?.create(FriendsAPI::class.java)
        val call = instance?.getAllFriend(token)

        call?.enqueue(object : Callback<Friend.FriendResponse>{
            override fun onResponse(
                call: Call<Friend.FriendResponse>,
                response: Response<Friend.FriendResponse>
            ) {
                friendResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Friend.FriendResponse>, t: Throwable) {
            }

        })
    }
}