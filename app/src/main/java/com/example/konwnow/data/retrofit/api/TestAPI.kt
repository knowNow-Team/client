package com.example.konwnow.data.retrofit.api

import com.example.konwnow.data.model.dto.TestLog
import com.example.konwnow.utils.API
import retrofit2.Call
import retrofit2.http.*

interface TestAPI {

    @GET(API.GET_TEST)
    fun getTestLog(@Header("Authorization") authorization:String): Call<TestLog>

}