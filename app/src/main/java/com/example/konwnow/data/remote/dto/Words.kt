package com.example.konwnow.data.remote.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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

    data class getWordResponseBody(
        val statusCode : Int,
        val data : Users.DataList?,
        val error : Users.ErrorList?
    )
}