package com.example.konwnow.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Quiz(
    @SerializedName("answer")
    val answer: String,
    @SerializedName("isCorrect")
    val isCorrect: Boolean,
    @SerializedName("wordId")
    val wordId: String) {
    override fun toString(): String {
        return "Quiz(answer='$answer', isCorrect=$isCorrect, wordId='$wordId')"
    }
}