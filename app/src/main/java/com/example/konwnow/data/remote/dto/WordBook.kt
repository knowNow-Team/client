package com.example.konwnow.data.remote.dto

import com.google.gson.annotations.SerializedName


class WordBook {

    data class WordBooks(
        val title : String,
        val allCount : Int,
        val wordBookID : String
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

    data class PostWordResponse(
        @SerializedName("data")
        val data: Data,
        @SerializedName("message")
        val message: String
    )


    data class PostWordRequestBody(
        @SerializedName("userId")
        val userId: Int,
        @SerializedName("wordNames")
        val wordNames: List<String>
    )

    data class GetAllWordResponse(
        @SerializedName("data")
        val data: List<GetAllWordResponseData>,
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

    data class GetAllWordResponseData(
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
        @SerializedName("words")
        val words: WordState,
        @SerializedName("words-doc")
        val wordsDoc: List<Words.Word>
    )

    data class WordState(
        @SerializedName("addedAt")
        val addedAt: String,
        @SerializedName("filter")
        val filter: String,
        @SerializedName("isRemoved")
        val isRemoved: Boolean,
        @SerializedName("wordId")
        val wordId: String
    )

    data class Filter(
        @SerializedName("count")
        val count: Int
    )


}
