package com.example.gestaofinaceira.repository

import androidx.room.*
import com.example.gestaofinaceira.entity.Receita
import com.example.gestaofinaceira.entity.UserWithReceitas


@Dao
interface ReceitaDAO {

    @Insert
    fun insert(receita: Receita)

    @Update
    fun update(receita: Receita)

    @Delete
    fun delete(receita: Receita)

    @Query("SELECT * FROM despesas WHERE id = :id")
    fun findById(id: Int): Receita

    @Query("SELECT * FROM despesas")
    fun findAll(): List<Receita>

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    fun findAllbyUserId(userId: Int): UserWithReceitas


}