package com.example.konwnow.data.remote.retrofit.api

import com.example.konwnow.data.remote.dto.Users
import com.example.konwnow.utils.API
import com.example.konwnow.utils.LOGIN
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginAPi {

    @POST(LOGIN.CREATE_USERS)
    fun postSignUp(
        @Header("google-id-token") token: String,
        @Body userRequestBody : Users.SignUpBody
    ): Call<Users.SingUpResponseBody>

    @POST(LOGIN.LOGIN)
    fun postGoogleLogin(
        @Header("google-id-token") token: String
    ): Call<Users.LoginResponseBody>

    @POST(LOGIN.RE_LOGIN)
    fun requestNewRefreshToken(
        @Body userRequestBody : Users.ReLoginBody
    ): Call<Users.LoginResponseBody>

}