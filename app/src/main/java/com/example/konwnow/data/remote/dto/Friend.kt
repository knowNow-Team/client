package com.example.konwnow.data.remote.dto

import com.google.gson.annotations.SerializedName

class Friend{
    data class GETFriendResponse(
        @SerializedName("data")
        val `data`: List<FriendData>,
        @SerializedName("error")
        val error: Any,
        @SerializedName("statusCode")
        val statusCode: Int
    )

    data class GETRankResponse(
        @SerializedName("data")
        val `data`: GETRankResponseData,
        @SerializedName("error")
        val error: Any,
        @SerializedName("statusCode")
        val statusCode: Int
    )

    data class GETRankResponseData(
        @SerializedName("correctRank")
        val correctRank: List<FriendData>,
        @SerializedName("examRank")
        val examRank: List<FriendData>,
        @SerializedName("wordRank")
        val wordRank: List<FriendData>
    )

    data class FriendData(
        @SerializedName("correctPercentage")
        val correctPercentage: Double,
        @SerializedName("examCount")
        val examCount: Int,
        @SerializedName("friendShipId")
        val friendShipId: Int,
        @SerializedName("nickName")
        val nickName: String,
        @SerializedName("profileMessage")
        val profileMessage: Any,
        @SerializedName("state")
        val state: String,
        @SerializedName("userId")
        val userId: Int,
        @SerializedName("wordCount")
        val wordCount: Int,
        @SerializedName("level")
        val level: Int
    )
}