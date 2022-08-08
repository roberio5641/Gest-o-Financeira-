package com.example.gestaofinaceira.repository

import androidx.room.*
import com.example.gestaofinaceira.entity.User

@Dao
interface UserDAO {

    @Insert
    fun incert(user: User)

    @Update
    fun update(user:User)

    @Delete
    fun delete(user: User)

    @Query("SElECT * FROM users WHERE id = :id")
    fun findById(id:Int):User

    @Query ("SElECT * FROM users WHERE email = :email")
    fun findByEmail(email:String):User

    @Query("SElECT * FROM users")
    fun findAll():List<User>


}