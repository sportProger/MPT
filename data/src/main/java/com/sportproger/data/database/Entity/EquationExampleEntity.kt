package com.sportproger.data.database.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EquationExampleEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    val type: String,
    val a: Int,
    val b: Int,
    val c: Int,
    val sign1: String,
    val sign2: String,
    val linearResult: Float,
    val squareResult: String,
    val userLinearAnswer: Float,
    val userSquareAnswer: Int,
    val stateExample: Boolean
)
