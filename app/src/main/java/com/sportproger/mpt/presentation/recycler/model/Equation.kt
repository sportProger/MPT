package com.sportproger.mpt.presentation.recycler.model

data class Equation(
    val type: String,
    val a: Int,
    val b: Int,
    val c: Int,
    val sign1: String,
    val sign2: String,
    val linearResult: Float,
    val squareResult: ArrayList<Int>?,
    val userLinearAnswer: Float,
    val userSquareAnswer: ArrayList<Int>?,
    val stateExample: Boolean
)
