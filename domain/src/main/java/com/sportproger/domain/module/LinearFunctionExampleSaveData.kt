package com.sportproger.domain.module

data class LinearFunctionExampleSaveData(
    val x: Int,
    val a: Int,
    val b: Int,
    val sign: String,
    val result: Int,
    val userAnswer: Int,
    val stateExample: Boolean
)
