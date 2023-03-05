package com.sportproger.data.database.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LinearFunctionExampleEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    val x: Int,
    val a: Int,
    val b: Int,
    val sign: String,
    val result: Int,
    val userAnswer: Int,
    val stateExample: Boolean
)
