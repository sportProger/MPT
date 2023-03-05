package com.sportproger.data.database.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DegreeExampleEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    val base1: Int,
    val exponent1: Int,
    val sign: String,
    val base2: Int,
    val exponent2: Int,
    val result: Int,
    val userAnswer: Int,
    val stateExample: Boolean
)
