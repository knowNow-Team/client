package com.example.konwnow.data.remote.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
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
        @Expose
        val createdAt: String,
        @SerializedName("_id")
        @Expose
        val id: String,
        @SerializedName("meanings")
        @Expose
        val meanings: List<String>,
        @SerializedName("phonics")
        @Expose
        val phonics: String,
        @SerializedName("pronounceVoicePath")
        @Expose
        val pronounceVoicePath: String,
        @SerializedName("updatedAt")
        @Expose
        val updatedAt: String,
        @SerializedName("__v")
        @Expose
        val v: Int,
        @SerializedName("word")
        @Expose
        val word: String,
        @SerializedName("wordClasses")
        @Expose
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
        @Expose
        val data : List<Word>,
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
}