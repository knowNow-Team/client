package com.example.konwnow.data.remote.retrofit.api

import androidx.room.Delete
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.data.remote.dto.Words
import com.example.konwnow.utils.API
import com.example.konwnow.utils.WORDBOOK
import retrofit2.Call
import retrofit2.http.*

interface WordBookAPI {

    @POST(WORDBOOK.WORDBOOK)
    fun postWordBook(
        @Header("Authorization") token : String,
        @Body requestBody: WordBook.CreatedWordBookBody
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

    @PUT(API.PUT_WORDS)
    fun putWords(
        @Header("Authorization") token : String,
        @Path("wordbookId") wordbookId: String,
        @Body request: WordBook.PutWordRequestBody
    ): Call<WordBook.PutWordResponse>

    @GET(WORDBOOK.GET_TRASH_WORD)
    fun getTrashWord(
        @Header("Authorization") token : String,
    ): Call<WordBook.GetAllWordResponse>

    @GET(WORDBOOK.GET_DETAIL_WORD)
    fun getDetailSettingWord(
        @Header("Authorization") token : String,
        @Query("wordbookIds") wordbookId : String,
        @Query("filter") filter : String,
        @Query("order") order : String
    ):Call<WordBook.GetAllWordResponse>

    @DELETE(WORDBOOK.DELETE_WORDBOOK)
    fun deleteWordBook(
        @Header("Authorization") token : String,
        @Path("wordbookId") wordbookId: String,
    ):Call<Words.DeleteWordResponse>

    @PUT(WORDBOOK.DELETE_WORDBOOK)
    fun putWordBookTitle(
        @Header("Authorization") token : String,
        @Path("wordbookId") wordbookId: String,
        @Body request : WordBook.PutWordBookTitle
    ):Call<Words.DeleteWordResponse>
}