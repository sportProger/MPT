package com.sportproger.mpt.general

interface Draw {
    fun drawRoot1(rootBase1: String, exponent1: String, strokeWidth: Float, textSize: Float, color: Int)
    fun drawRoot2(rootBase2: String, exponent2: String, strokeWidth: Float, textSize: Float, signPosition: Float, color: Int)
    fun drawSign(sign: String, textSize: Float, rootBase1: String, color: Int)
}