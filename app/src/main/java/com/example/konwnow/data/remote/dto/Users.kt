package com.example.konwnow.data.remote.dto

class Users{

    data class SignUpBody(val nickname : String)

    data class SingUpResponseBody(
        val statusCode : Int,
        val data : DataList,
        val error : ErrorList?
    )

    data class ErrorList (
        val errorCode: String,
        val userMessage: String
    )

    data class DataList(
        //val @id : Int,
        val id : Int,
        val userEmail : String,
        val nickname: String,
        val role:String,
        val created : String,
        val updated : String,
        val userAuth : UserAuth
    )

    data class UserAuth(
        //val @id : Int,
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
        val nickname : String,
        val role :String,
        val updated : String,
        val userEmail : String
    )

    data class ReLoginBody(
        val jwtToken : String,
        val refreshToken : String
    )
}