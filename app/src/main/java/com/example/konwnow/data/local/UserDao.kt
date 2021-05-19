package com.example.konwnow.data.local

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.google.android.gms.auth.api.credentials.IdToken

@Dao
interface UserDao {

    @Insert(onConflict = REPLACE)
    fun insert(user : UserEntity)

    @Query("SELECT idToken FROM user")
    fun getIdToken() : String

    @Query("SELECT refreshToken FROM user")
    fun getRefreahToken() : String

    @Query("SELECT nickname FROM user")
    fun getNickname() : String

    @Query("SELECT email FROM user")
    fun getEmail() : String

    @Query("SELECT * FROM user")
    fun getAll() : UserEntity

    @Delete
    fun delete(user: UserEntity)
}