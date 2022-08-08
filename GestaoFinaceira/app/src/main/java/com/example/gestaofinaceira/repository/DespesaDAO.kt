package com.example.gestaofinaceira.repository

import androidx.room.*
import com.example.gestaofinaceira.entity.Despesa
import com.example.gestaofinaceira.entity.UserWithDespesas

@Dao
interface DespesaDAO {

    @Insert
    fun insert(despesa: Despesa)
    @Update
    fun update(despesa: Despesa)
    @Delete
    fun delete(despesa: Despesa)
    @Query("SELECT * FROM despesas WHERE id = :id")
    fun findById(id:Int): Despesa
    @Query("SELECT * FROM despesas")
    fun findAll():List<Despesa>

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    fun findAllbyUserId(userId: Int):UserWithDespesas




}