package com.example.konwnow.data.remote.retrofit.api

import com.example.konwnow.data.remote.dto.Friend
import com.example.konwnow.utils.FRIEND
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FriendsAPI {

    @GET(FRIEND.GET_CODE)
    fun getFriendCode(
        @Header("jwt-access-token") token : String
    ):Call<Friend.FriendCodeResponse>

    @POST(FRIEND.POST_FRIEND)
    fun postFriend(
        @Header("jwt-access-token") token : String,
        @Body friendAddTokens : Friend.PostFriendBody
    ):Call<Friend.PostFriendResponse>
}