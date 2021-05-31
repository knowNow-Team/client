package com.example.konwnow.data.remote.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import org.json.JSONArray
import org.json.JSONObject

//@Parcelize
//class Words(var eng: String, var kor: String,var levelStatus: Int) : Parcelable
class Words {
    @Parcelize
    data class Word(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("meanings")
        val meanings: List<String>,
        @SerializedName("phonics")
        val phonics: String,
        @SerializedName("pronounceVoicePath")
        val pronounceVoicePath: String,
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("__v")
        val v: Int,
        @SerializedName("word")
        val word: String,
        @SerializedName("wordClasses")
        val wordClasses: List<String>
    ): Parcelable

    data class GetWordFromImageResponseBody(
        @SerializedName("statusCode")
        val statusCode : Int,
        @SerializedName("data")
        val data : List<GetWordFromImageResponseData>?,
        @SerializedName("error")
        val error : ErrorList?
    )

    data class GetWordFromSentenceResponseBody(
        @SerializedName("statusCode")
        val statusCode : Int,
        @SerializedName("data")
        val data : List<String>?,
        @SerializedName("error")
        val error : ErrorList?
    )

    data class GetWordInfoResponseBody(
        @SerializedName("message")
        val message : String,
        @SerializedName("data")
        val data : List<Word>?,
    )

    data class SentenceRequestBody(
        @SerializedName("sentence")
        val sentence: String
    )

    data class WordRequestBody(
        @SerializedName("wordNames")
        val wordNames: List<String>
    )


    data class GetWordFromImageResponseData(
        @SerializedName("position")
        val position: List<JSONObject>,
        @SerializedName("text")
        val text: String
    )

    data class GetWordFromSentenceResponseData(
        @SerializedName("text")
        val text: String
    )

    data class ErrorList (
        val errorCode: String,
        val userMessage: String
    )


    // 필터 put & 휴지통 이동 응답
    data class PutFilterBody(
        @SerializedName("filter")
        val filter:  String
    )
    data class PutFilterResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String
    )

    data class MoveTrashResponse(
        @SerializedName("data")
        val `data`: Data,
        @SerializedName("message")
        val message: String
    )

    data class DeleteWordResponse(
        @SerializedName("data")
        val `data`: Data,
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
        val words: List<Word>
    )

    data class Words(
        @SerializedName("addedAt")
        val addedAt: String,
        @SerializedName("filter")
        val filter: String,
        @SerializedName("isRemoved")
        val isRemoved: Boolean,
        @SerializedName("wordId")
        val wordId: String
    )



}