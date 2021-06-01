package com.example.konwnow.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val num : Int,
    val idToken : String,
    val loginToken : String,
    val refreshToken : String,
    val nickname : String,
    val userID : Int,
    val email : String,
    val level : Int,
    val message : String)
