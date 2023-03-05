package com.sportproger.mpt.presentation.recycler.model

data class LinearFunction(
    val x: Int,
    val a: Int,
    val b: Int,
    val sign: String,
    val result: Int,
    val userAnswer: Int,
    val stateExample: Boolean
)
