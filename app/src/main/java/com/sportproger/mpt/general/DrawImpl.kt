package com.sportproger.mpt.general

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.os.Build
import androidx.core.content.ContextCompat.getColor
import com.sportproger.mpt.R

class DrawImpl(private val context: Context, private val canvas: Canvas): Draw {
    override fun drawRoot1(rootBase1: String, exponent1: String, strokeWidth: Float, textSize: Float, color: Int) {
        val paintRoot = Paint()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            paintRoot.color = getColor(context, color)
        }
        paintRoot.strokeWidth = strokeWidth

        val paintBaseRoot = Paint()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            paintBaseRoot.color = getColor(context, color)
        }
        paintBaseRoot.textSize = textSize

        val paintExponentRoot = Paint()
        paintExponentRoot.color = getColor(context, color)
        paintExponentRoot.textSize = (textSize / 1.8).toFloat()

        val ptsRoot = floatArrayOf(
            20f, (canvas.height / 1.6).toFloat(), 40f, (canvas.height / 1.6).toFloat(),
            40f, (canvas.height / 1.6).toFloat(), 60f, canvas.height.toFloat(),
            60f, canvas.height.toFloat(), 80f, (canvas.height / 4).toFloat(),
            80f, (canvas.height / 4).toFloat(), 120f + rootBase1.length * 40f, (canvas.height / 4).toFloat()
        )

        canvas.drawLines(ptsRoot, paintRoot)
        canvas.drawText(exponent1, 20f, (canvas.height / 2).toFloat(), paintExponentRoot)
        canvas.drawText(rootBase1, 100f, canvas.height.toFloat() - 20f, paintBaseRoot)
    }

    override fun drawSign(sign: String, textSize: Float, rootBase1: String, color: Int) {
        val paint = Paint()
        paint.textSize = textSize
        paint.color = getColor(context, color)

        canvas.drawText(sign, 170f + rootBase1.length * 40f, (canvas.height / 1.3).toFloat(), paint)
    }

    override fun drawRoot2(rootBase2: String, exponent2: String, strokeWidth: Float, textSize: Float, signPosition: Float, color: Int) {
        val paintRoot = Paint()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            paintRoot.color = getColor(context, color)
        }
        paintRoot.strokeWidth = strokeWidth

        val paintBaseRoot = Paint()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            paintBaseRoot.color = getColor(context, color)
        }
        paintBaseRoot.textSize = textSize

        val paintExponentRoot = Paint()
        paintExponentRoot.color = getColor(context, color)
        paintExponentRoot.textSize = (textSize / 1.8).toFloat()

        val ptsRoot = floatArrayOf(
            signPosition + 40f, (canvas.height / 1.6).toFloat(), signPosition + 60f, (canvas.height / 1.6).toFloat(),
            signPosition + 60f, (canvas.height / 1.6).toFloat(), signPosition + 80f, canvas.height.toFloat(),
            signPosition + 80f, canvas.height.toFloat(), signPosition + 100f, (canvas.height / 4).toFloat(),
            signPosition + 100f, (canvas.height / 4).toFloat(), signPosition + 120f + rootBase2.length * 40f, (canvas.height / 4).toFloat()
        )

        canvas.drawLines(ptsRoot, paintRoot)
        canvas.drawText(exponent2, signPosition + 40f, (canvas.height / 2).toFloat(), paintExponentRoot)
        canvas.drawText(rootBase2, 120f + signPosition, canvas.height.toFloat() - 20f, paintBaseRoot)

        canvas.save()
        canvas.restore()
    }
}