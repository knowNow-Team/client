package com.example.konwnow.data.remote.dto

import com.google.gson.annotations.SerializedName

class Users{

    data class SignUpBody(val nickname : String)

    data class SingUpResponseBody(
        val statusCode : Int,
        val data : DataList?,
        val error : ErrorList?
    )

    data class ErrorList (
        val errorCode: String,
        val userMessage: String
    )

    data class DataList(
        @SerializedName("@id") val  _id : Int,
        val id : Int,
        val userEmail : String,
        val nickName: String,
        val role:String,
        val created : String,
        val updated : String,
        val userAuth : UserAuth
    )

    data class UserAuth(
        @SerializedName("@id") val  _id : Int,
        val id : Int,
        val user : Int,
        val loginToken : String,
        val refreshToken: String
    )

    data class LoginResponseBody (
        val id : Int,
        val loginToken:String,
        val refreshToken: String,
        val user : LoginUserList
    )

    data class LoginUserList(
        val created : String,
        val id : Int,
        val nickName : String,
        val role :String,
        val updated : String,
        val userEmail : String
    )

    data class ReLoginBody(
        val jwtToken : String,
        val refreshToken : String
    )
}