package com.example.konwnow.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(UserEntity::class), version=2)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao() : UserDao

    //싱글
    companion object{
        private var INSTANCE : UserDatabase? = null

        fun getInstance(context: Context) : UserDatabase?{
            if(INSTANCE == null ){
                synchronized(UserDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "local.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}