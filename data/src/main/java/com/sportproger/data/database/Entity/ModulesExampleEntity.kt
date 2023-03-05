package com.sportproger.data.database.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ModulesExampleEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    val number1: Int,
    val sign: String,
    val number2: Int,
    val result: Int,
    val userAnswer: Int,
    val stateExample: Boolean
)
