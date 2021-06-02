package com.example.konwnow.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.konwnow.data.remote.dto.Friend
import com.example.konwnow.data.remote.dto.Users
import com.example.konwnow.data.remote.dto.Words
import com.example.konwnow.data.remote.retrofit.RetrofitClient
import com.example.konwnow.data.remote.retrofit.api.FriendAPI
import com.example.konwnow.data.remote.retrofit.api.LoginAPi
import com.example.konwnow.data.remote.retrofit.api.WriteAPI
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FriendViewModel : ViewModel() {

    var friendList : MutableLiveData<Friend.GETFriendResponse> = MutableLiveData()
    var rankList : MutableLiveData<Friend.GETRankResponse> = MutableLiveData()


    fun getFriendListObserver() : MutableLiveData<Friend.GETFriendResponse>{
        return friendList
    }

    fun getRankListObserver() : MutableLiveData<Friend.GETRankResponse>{
        return rankList
    }


    fun getFriend(token : String){
        val instance = RetrofitClient.getUserClient()?.create(FriendAPI::class.java)
        val call = instance?.getFriends(token)
        call?.enqueue(object : Callback<Friend.GETFriendResponse> {
            override fun onResponse(
                call: Call<Friend.GETFriendResponse>,
                WordResponse: Response<Friend.GETFriendResponse>
            ) {
                Log.d("친구", "success")

                //업데이트 시켜주기.
                friendList.postValue(WordResponse.body())
            }

            override fun onFailure(call: Call<Friend.GETFriendResponse>, t: Throwable) {
                Log.d("viewmodel", t.message.toString())
            }

        })
    }

    fun getRank(token : String){
        val instance = RetrofitClient.getUserClient()?.create(FriendAPI::class.java)
        val call = instance?.getRank(token)
        call?.enqueue(object : Callback<Friend.GETRankResponse> {
            override fun onResponse(
                call: Call<Friend.GETRankResponse>,
                rankResponse: Response<Friend.GETRankResponse>
            ) {
                Log.d("랭크", rankResponse.body().toString())

                //업데이트 시켜주기.
                rankList.postValue(rankResponse.body())
            }

            override fun onFailure(call: Call<Friend.GETRankResponse>, t: Throwable) {
                Log.d("viewmodel", t.message.toString())
            }

        })
    }


}