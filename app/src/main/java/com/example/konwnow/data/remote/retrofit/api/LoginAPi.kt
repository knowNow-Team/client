package com.example.konwnow.data.remote.retrofit.api

import com.example.konwnow.data.remote.dto.Users
import com.example.konwnow.utils.API
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginAPi {

    @POST(API.CREATE_USERS)
    fun postSignUp(
        @Header("google-id-token") token: String,
        @Body userRequestBody : Users.SignUpBody
    ): Call<Users.SignUpBody>

}