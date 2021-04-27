package com.example.konwnow.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserEntity(
    @PrimaryKey val idToken : String,
    val nickname : String)
