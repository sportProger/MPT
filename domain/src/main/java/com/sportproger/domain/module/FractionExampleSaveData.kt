package com.sportproger.domain.module

data class FractionExampleSaveData (
    val type: String,
    val numerator1: Int,
    val denominator1: Int,
    val sign: String,
    val numerator2: Int,
    val denominator2: Int,
    val number1: Float,
    val number2: Float,
    val floatResult: Float,
    val result: Int,
    val userAnswer: Int,
    val stateExample: Boolean
)