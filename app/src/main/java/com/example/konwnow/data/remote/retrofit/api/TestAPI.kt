package com.example.konwnow.data.remote.retrofit.api

import com.example.konwnow.data.remote.dto.TestLog
import com.example.konwnow.data.remote.dto.Users
import com.example.konwnow.utils.API
import com.example.konwnow.utils.LOGIN
import retrofit2.Call
import retrofit2.http.*

interface TestAPI {

    @GET(API.GET_TEST)
    fun getTestLog(@Header("Authorization") authorization:String): Call<TestLog.TestInfo>

    @POST(API.CREATE_TEST)
    fun postTestLog(@Header("Authorization") authorization:String, @Body testRequestBody : TestLog.TestRequestBody): Call<TestLog.TestCreateResponse>

}