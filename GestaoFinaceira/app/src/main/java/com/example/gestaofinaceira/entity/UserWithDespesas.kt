package com.example.gestaofinaceira.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithDespesas(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "user_id"
    )
    val despesas: List<Despesa>
)
