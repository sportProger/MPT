package com.sportproger.data.storage.module

data class RootExampleSaveDataStorage(
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