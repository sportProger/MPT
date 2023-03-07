package com.sportproger.data.storage.module

data class EquationExampleSaveDataStorage(
    val type: String,
    val a: Int,
    val b: Int,
    val c: Int,
    val sign1: String,
    val sign2: String,
    val linearResult: Float,
    val squareResult: ArrayList<Int>?,
    val userLinearAnswer: Float,
    val userSquareAnswer: Int,
    val stateExample: Boolean
)
