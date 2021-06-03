package com.example.konwnow.data.remote.dto

import com.google.gson.annotations.SerializedName

class Friend {

    data class FriendCodeResponse(
        @SerializedName("data")
        val `data`: String,
        @SerializedName("error")
        val error: Any,
        @SerializedName("statusCode")
        val statusCode: Int
    )

    data class PostFriendResponse(
        @SerializedName("data")
        val `data`: Data,
        @SerializedName("error")
        val error: Any,
        @SerializedName("statusCode")
        val statusCode: Int
    )

    data class Data(
        @SerializedName("correctPercentage")
        val correctPercentage: Double,
        @SerializedName("examCount")
        val examCount: Int,
        @SerializedName("friendShipId")
        val friendShipId: Int,
        @SerializedName("level")
        val level: Int,
        @SerializedName("nickName")
        val nickName: String,
        @SerializedName("profileMessage")
        val profileMessage: String,
        @SerializedName("state")
        val state: String,
        @SerializedName("userId")
        val userId: Int,
        @SerializedName("wordCount")
        val wordCount: Int
    )

    data class PostFriendBody(
        @SerializedName("friendAddTokens")
        val friendAddTokens: String
    )
}

