package com.example.gestaofinaceira.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithReceitas(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "user_id"
    )
    val receitas: List<Receita>
)
