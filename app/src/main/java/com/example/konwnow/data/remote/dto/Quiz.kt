package com.example.konwnow.data.remote.dto

class Quiz(val target: String, val kor:String, val userAnswer: String, var hit: Boolean){
    override fun toString(): String {
        return "타겟:$target\n뜻:$kor\n작성한 답:$userAnswer\n정답여부:$hit"
    }
}
