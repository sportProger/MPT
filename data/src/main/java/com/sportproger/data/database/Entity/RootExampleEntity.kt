package com.sportproger.data.database.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RootExampleEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    val type: String,
    val exponent1: Int,
    val exponent2: Int,
    val baseRoot1: Int,
    val baseRoot2: Int,
    val sign: String,
    val result: Float,
    val userAnswer: Float,
    val stateExample: Boolean
)
