package com.example.konwnow.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.konwnow.data.remote.dto.Friend
import com.example.konwnow.data.remote.retrofit.RetrofitClient
import android.util.Log
import com.example.konwnow.data.remote.retrofit.api.FriendAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendViewModel : ViewModel() {

    var getCodeResponse : MutableLiveData<Friend.FriendCodeResponse> = MutableLiveData()
    var postfriendResponse : MutableLiveData<Friend.PostFriendResponse> = MutableLiveData()
    var friendList : MutableLiveData<Friend.GETFriendResponse> = MutableLiveData()
    var rankList : MutableLiveData<Friend.GETRankResponse> = MutableLiveData()
    var deleteFriendResponse : MutableLiveData<Friend.DeleteFriendBody> = MutableLiveData()

    fun getFriendListObserver() : MutableLiveData<Friend.GETFriendResponse>{
        return friendList
    }

    fun getRankListObserver() : MutableLiveData<Friend.GETRankResponse>{
        return rankList
    }

    fun getCodeObserver() : MutableLiveData<Friend.FriendCodeResponse>{
        return  getCodeResponse
    }

    fun postFriendObserver() : MutableLiveData<Friend.PostFriendResponse>{
        return postfriendResponse
    }

    fun deleteFriendObserver() : MutableLiveData<Friend.DeleteFriendBody>{
        return deleteFriendResponse
    }

    fun getCode(token: String){
        val instance = RetrofitClient.getUserClient()?.create(FriendAPI::class.java)
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
        val instance = RetrofitClient.getUserClient()?.create(FriendAPI::class.java)
        val call = instance?.postFriend(token,Friend.PostFriendBody(friendToken))

        call?.enqueue(object  : Callback<Friend.PostFriendResponse>{
            override fun onResponse(
                call: Call<Friend.PostFriendResponse>,
                response: Response<Friend.PostFriendResponse>
            ) {
                postfriendResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Friend.PostFriendResponse>, t: Throwable) {

            }
        })
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

    fun deleteFriend(token: String, friendId : Int){
        val instance = RetrofitClient.getUserClient()?.create(FriendAPI::class.java)
        val call = instance?.deleteFriend(token,friendId)

        call?.enqueue(object  : Callback<Friend.DeleteFriendBody>{
            override fun onResponse(
                call: Call<Friend.DeleteFriendBody>,
                response: Response<Friend.DeleteFriendBody>
            ) {
                deleteFriendResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Friend.DeleteFriendBody>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}