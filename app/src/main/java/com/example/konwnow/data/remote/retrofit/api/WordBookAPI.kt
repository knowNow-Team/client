package com.example.konwnow.data.remote.retrofit.api

import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.utils.API
import com.example.konwnow.utils.WORDBOOK
import retrofit2.Call
import retrofit2.http.*

interface WordBookAPI {

    @POST(WORDBOOK.WORDBOOK)
    fun postWordBook(
        @Header("Authorization") token : String,
        @Body responseBody: WordBook.CreatedWordBookBody
    ): Call<WordBook.PostWordBookResponse>

    @GET(WORDBOOK.WORDBOOK)
    fun getWordBook(
        @Header("Authorization") token : String,
    ): Call<WordBook.GetWordBookResponse>

    @GET(API.GET_WORDS)
    fun getAllWord(
        @Header("Authorization") token: String,
        @Query("wordbookIds") wordbook: String,
        ): Call<WordBook.GetAllWordResponse>

    @POST(API.POST_WORDS)
    fun postWord(
        @Header("Authorization") token : String,
        @Body request: WordBook.PostWordRequestBody
    ): Call<WordBook.PostWordResponse>
}