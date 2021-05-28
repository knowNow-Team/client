package com.example.konwnow.data.remote.retrofit.api

import com.example.konwnow.data.remote.dto.Words
import com.example.konwnow.utils.API
import com.example.konwnow.utils.LOGIN
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface WriteAPI {
    @Multipart
    @POST(API.WORD_FROM_IMAGE)
    fun getWordFromImage(
        @Part file: MultipartBody.Part
    ): Call<Words.getWordResponseBody>
}