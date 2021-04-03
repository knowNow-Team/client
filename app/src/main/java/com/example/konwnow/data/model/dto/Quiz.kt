package com.example.konwnow.data.model.dto

class Quiz(val target: String, val userAnswer: String, var hit: Boolean){
    override fun toString(): String {
        return "타겟:$target\n작성한 답:$userAnswer\n정답여부:$hit"
    }
}
