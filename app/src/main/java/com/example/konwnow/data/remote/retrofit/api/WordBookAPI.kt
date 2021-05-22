package com.example.konwnow.data.remote.retrofit.api

import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.utils.WORDBOOK
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

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

}