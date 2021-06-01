package com.example.konwnow.data.remote.dto

import com.google.gson.annotations.SerializedName

class Users{

    data class SignUpBody(val nickname : String)

    data class UserResponseBody(
        val statusCode : Int,
        val data : DataList?,
        val error : ErrorList?
    )

    data class ErrorList (
        val errorCode: String,
        val userMessage: String
    )

    data class DataList(
        @SerializedName("correctPercentage")
        val correctPercentage: Double,
        @SerializedName("examCount")
        val examCount: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("nickName")
        val nickName: String,
        @SerializedName("userAuth")
        val userAuth: UserAuth,
        @SerializedName("userEmail")
        val userEmail: String,
        @SerializedName("userLevel")
        val userLevel: Int,
        @SerializedName("wordCount")
        val wordCount: Int,
        @SerializedName("profileMessage")
        val profileMessage : String?
    )

    data class UserAuth(
        @SerializedName("loginToken")
        val loginToken: String,
        @SerializedName("refreshToken")
        val refreshToken: String
    )

    data class ReLoginBody(
        val jwtToken : String,
        val refreshToken : String
    )

    data class PutMessageBody(
        val message : String
    )

}
