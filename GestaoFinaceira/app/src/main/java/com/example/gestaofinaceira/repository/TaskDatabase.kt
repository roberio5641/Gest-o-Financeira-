package com.example.gestaofinaceira.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gestaofinaceira.entity.Despesa
import com.example.gestaofinaceira.entity.Receita
import com.example.gestaofinaceira.entity.User

@Database(entities = [User::class, Despesa::class, Receita::class], version = 2)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDAO
    abstract fun getDespesaDAO(): DespesaDAO
    abstract fun getReceitaDAO(): ReceitaDAO

}