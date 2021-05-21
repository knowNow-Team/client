package com.example.konwnow.data.remote.dto

import com.google.gson.annotations.SerializedName


class WordBook {

    data class WordBooks(
        val title : String,
        val allCount : Int
    )

    //단어장 생성
    data class CreatedWordBookBody(
        val title : String,
        val owner : Int
    )

    data class PostWordBookResponse(
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

    //단어장 조회
    data class GetWordBookResponse(
        @SerializedName("data")
        val data : List<WordBookData>,
        @SerializedName("message")
        val message: String
    )

    data class WordBookData(
        @SerializedName("allCount")
        val allCount: Int,
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("filters")
        val filters: List<Filter>,
        @SerializedName("_id")
        val id: String,
        @SerializedName("owner")
        val owner: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("updatedAt")
        val updatedAt: String
    )

    data class Filter(
        @SerializedName("count")
        val count: Int
    )


}
