package com.example.konwnow.data.local

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface UserDao {

    @Insert(onConflict = REPLACE)
    fun insert(user : UserEntity)

    @Query("SELECT * FROM user")
    fun getAllUser() : List<UserEntity>

    @Delete
    fun delete(user : UserEntity)
}