package com.example.gestaofinaceira.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "despesas"
)
data class Despesa(
    @PrimaryKey
    val id:Int? = null,
    val name: String,
    val valor: String,
    @ColumnInfo(name="user_id")
    val userId:Int
)
