package com.sportproger.data.database.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LogarithmExampleEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    val baseOfLogarithm: Int,
    val logarithmicNumber: Int,
    val result: Int,
    val userAnswer: Int,
    val stateExample: Boolean
)
