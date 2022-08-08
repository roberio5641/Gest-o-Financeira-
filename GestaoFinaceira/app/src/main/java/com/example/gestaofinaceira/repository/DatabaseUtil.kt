package com.example.gestaofinaceira.repository

import android.content.Context
import androidx.room.Room

object DatabaseUtil {

    private var instance: TaskDatabase? = null

    fun getInstance(context: Context):TaskDatabase{
        if (instance == null){
            instance = Room.databaseBuilder(
                context,
                TaskDatabase::class.java,
                "taskmaneger.db"
            )
               // .fallbackToDestructiveMigration()
                .build()
        }
        return instance!!
    }




}