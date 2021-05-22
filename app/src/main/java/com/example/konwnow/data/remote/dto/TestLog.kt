package com.example.konwnow.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TestLog{
    data class TestInfo(
        @SerializedName("data")
        val `data`: List<TestLog.TestLogData>,
        @SerializedName("message")
        val message: String
    ) {
        override fun toString(): String {
            return "TestInfo(`data`=$`data`, message='$message')"
        }
    }

    data class TestRequestBody(
        @SerializedName("correctAnswerCount")
        val correctAnswerCount: Int,
        @SerializedName("difficulty")
        val difficulty: String,
        @SerializedName("filter")
        val filter: List<String>,
        @SerializedName("score")
        val score: Int,
        @SerializedName("testerId")
        @Expose
        val testerId: Int?,
        @SerializedName("wordTotalCount")
        val wordTotalCount: Int,
        @SerializedName("wordbooks")
        val wordbooks: List<String>,
        @SerializedName("words")
        val words: List<Quiz>
    )

    data class TestCreateResponse(
        @SerializedName("message")
        val message: String
    )

    data class TestLogData(
        @SerializedName("correctAnswerCount")
        val correctAnswerCount: Int,
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("difficulty")
        val difficulty: String,
        @SerializedName("filter")
        val filter: List<String>,
        @SerializedName("_id")
        val id: String,
        @SerializedName("score")
        val score: Int,
        @SerializedName("testerId")
        val testerId: Int,
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("__v")
        val v: Int,
        @SerializedName("wordTotalCount")
        val wordTotalCount: Int,
        @SerializedName("wordbooks")
        val wordbooks: List<String>,
        @SerializedName("words")
        val words: List<UserAnswer>
    ) {
        override fun toString(): String {
            return "TestLogData(correctAnswerCount=$correctAnswerCount, createdAt='$createdAt', difficulty='$difficulty', filter=$filter, id='$id', score=$score, testerId=$testerId, updatedAt='$updatedAt', v=$v, wordTotalCount=$wordTotalCount, wordbooks=$wordbooks, words=$words)"
        }
    }

    data class UserAnswer(
        @SerializedName("answer")
        val answer: String,
        @SerializedName("isCorrect")
        val isCorrect: Boolean,
        @SerializedName("wordId")
        val wordId: String
    ) {
        override fun toString(): String {
            return "UserAnswer(answer='$answer', isCorrect=$isCorrect, wordId='$wordId')"
        }
    }
}

