package com.example.konwnow.data.remote.retrofit.api

import com.example.konwnow.data.remote.dto.Friend
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.data.remote.dto.Words
import com.example.konwnow.utils.API
import com.example.konwnow.utils.FRIEND
import com.example.konwnow.utils.LOGIN
import com.example.konwnow.utils.WORDBOOK
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface FriendAPI {

    @GET(FRIEND.GET_CODE)
    fun getFriendCode(
        @Header("jwt-access-token") token : String
    ):Call<Friend.FriendCodeResponse>

    @POST(FRIEND.POST_FRIEND)
    fun postFriend(
        @Header("jwt-access-token") token : String,
        @Body friendAddTokens : Friend.PostFriendBody
    ):Call<Friend.PostFriendResponse>

    @GET(FRIEND.GET_FRIEND)
    fun getFriends(
        @Header("jwt-access-token") token : String,
        ): Call<Friend.GETFriendResponse>

    @GET(FRIEND.GET_RANK)
    fun getRank(
        @Header("jwt-access-token") token : String,
    ): Call<Friend.GETRankResponse>

    @DELETE(FRIEND.DELETE_FRIEND)
    fun deleteFriend(
        @Header("jwt-access-token") token : String,
        @Path("friendShipId") userId : Int
    ): Call<Friend.DeleteFriendBody>
}