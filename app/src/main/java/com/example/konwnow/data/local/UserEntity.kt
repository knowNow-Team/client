package com.example.konwnow.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val idToken : String,
    val refreshToken : String,
    val nickname : String,
    val email : String)
