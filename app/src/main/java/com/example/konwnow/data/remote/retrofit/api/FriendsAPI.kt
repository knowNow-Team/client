package com.example.konwnow.data.remote.retrofit.api

import com.example.konwnow.data.remote.dto.Friend
import com.example.konwnow.utils.FRIEND
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface FriendsAPI {

    @GET(FRIEND.GET_CODE)
    fun getFriendCode(
        @Header("jwt-access-token") token : String
    ):Call<Friend.FriendCodeResponse>
}