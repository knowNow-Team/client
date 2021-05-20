package com.example.konwnow.data.remote.dto

import com.google.gson.annotations.SerializedName

class Users{

    //회원가입
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
        val updated : String?,
        val userAuth : UserAuth
    )

    data class UserAuth(
        @SerializedName("@id") val  _id : Int,
        val id : Int,
        val user : Int,
        val loginToken : String,
        val refreshToken: String
    )

    //구글 로그인
    data class LoginResponseBody (
        val statusCode : Int,
        val data : LoginDataList?,
        val error : ErrorList?
    )

    data class LoginDataList(
        @SerializedName("@id") val  _id : Int,
        val id : Int,
        val user : UserList,
        val loginToken: String,
        val refreshToken: String
    )

    data class UserList(
        @SerializedName("@id") val  _id : Int,
        val id : Int,
        val user : Int,
        val userEmail: String,
        val nickName: String,
        val role: String,
        val created: String,
        val updated: String?,
        val userAuth : Int
    )

    data class ReLoginBody(
        val jwtToken : String,
        val refreshToken : String
    )
}