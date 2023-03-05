package com.sportproger.mpt.presentation.recycler.model

data class Fraction(
    val numerator1: Int,
    val denominator1: Int,
    val sign: String,
    val numerator2: Int,
    val denominator2: Int,
    val result: Int,
    val userAnswer: Int,
    val stateExample: Boolean
)