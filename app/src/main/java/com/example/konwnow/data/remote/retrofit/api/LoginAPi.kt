package com.example.konwnow.data.remote.retrofit.api

import com.example.konwnow.data.remote.dto.Users
import com.example.konwnow.utils.LOGIN
import retrofit2.Call
import retrofit2.http.*

interface LoginAPi {

    @POST(LOGIN.CREATE_USERS)
    fun postSignUp(
        @Header("google-id-token") token: String,
        @Body userRequestBody : Users.SignUpBody
    ): Call<Users.UserResponseBody>

    @POST(LOGIN.LOGIN)
    fun postGoogleLogin(
        @Header("google-id-token") token: String
    ): Call<Users.UserResponseBody>

    @POST(LOGIN.RE_LOGIN)
    fun requestNewRefreshToken(
        @Body userRequestBody : Users.ReLoginBody
    ): Call<Users.UserResponseBody>

    @GET("v1/users/{userId}")
    fun getUser(
        @Header("jwt-access-token") loginToken : String,
        @Path("userId") userId : Int
    ): Call<Users.UserResponseBody>

    @PUT(LOGIN.PUT_USER_MESSAGE)
    fun putUserMessage(
        @Header("jwt-access-token") loginToken : String,
        @Path("userId") userId : Int,
        @Body putMessageBody : Users.PutMessageBody
    ):Call<Users.UserResponseBody>


}