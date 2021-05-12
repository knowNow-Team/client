package com.example.konwnow.data.remote.retrofit.api

import com.example.konwnow.data.remote.dto.Users
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginAPi {

    @POST("v1/login")
    fun postLoginInfo(
        @Header("X-AUTH-TOKEN") token: String,
        @Body nickname : String
    ): Call<Users>

}