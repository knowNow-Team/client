package com.example.konwnow.data.remote.retrofit.api

import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.data.remote.dto.Words
import com.example.konwnow.utils.API
import com.example.konwnow.utils.HOMEWORD
import com.example.konwnow.utils.LOGIN
import com.example.konwnow.utils.WORDBOOK
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface WordAPI {
    @POST(API.WORD_SCRAP)
    fun postScrapWord(
        @Header("Authorization")authorization:String,
        @Body requestBody: Words.WordRequestBody,
    ): Call<Words.GetWordInfoResponseBody>

    @PUT(HOMEWORD.PUT_FILTER)
    fun putWordFilter(
        @Header("Authorization")authorization:String,
        @Path("wordbookId") wordbookId : String,
        @Path("wordId") wordId : String,
        @Body filter : String
    ): Call<Words.PutFilterResponse>
}