package com.example.konwnow.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllUser() : List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntity : UserEntity)

    @Query("DELETE FROM user WHERE idToken = :idToken")
    fun delete(idToken : String)
}