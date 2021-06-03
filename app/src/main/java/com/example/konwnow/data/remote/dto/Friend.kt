package com.example.konwnow.data.remote.dto
import com.google.gson.annotations.SerializedName

class Friend{

    data class FriendCodeResponse(
        @SerializedName("data")
        val `data`: String,
        @SerializedName("error")
        val error: Any,
        @SerializedName("statusCode")
        val statusCode: Int
    )
}

