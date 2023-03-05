package com.sportproger.data.storage.module

data class DegreeExampleSaveDataStorage(
    val base1: Int,
    val exponent1: Int,
    val sign: String,
    val base2: Int,
    val exponent2: Int,
    val result: Int,
    val userAnswer: Int,
    val stateExample: Boolean
)