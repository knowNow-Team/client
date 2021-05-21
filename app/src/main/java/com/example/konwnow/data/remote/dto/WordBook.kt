package com.example.konwnow.data.remote.dto

import com.google.gson.annotations.SerializedName


class WordBook {

    data class CreatedWordBookBody(
        val title : String,
        val owner : Int
    )

    data class ResponseBody(
        @SerializedName("data")
        val data: Data,
        @SerializedName("message")
        val message: String
    )
    
    data class Data(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("owner")
        val owner: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("__v")
        val v: Int,
        @SerializedName("words")
        val words: List<Any>
    )


}
