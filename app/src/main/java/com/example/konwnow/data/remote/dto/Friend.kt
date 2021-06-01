package com.example.konwnow.data.remote.dto
import com.google.gson.annotations.SerializedName


class Friend{

class FriendResponse : ArrayList<FriendResponseItem>()

data class FriendResponseItem(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: Error,
    @SerializedName("statusCode")
    val statusCode: Int
)

class Data(
)

data class Error(
    @SerializedName("errorCode")
    val errorCode: String,
    @SerializedName("userMessage")
    val userMessage: String
)
}