package com.sportproger.domain.module

data class DegreeExampleSaveData(
    val base1: Int,
    val exponent1: Int,
    val sign: String,
    val base2: Int,
    val exponent2: Int,
    val result: Int,
    val userAnswer: Int,
    val stateExample: Boolean
)
