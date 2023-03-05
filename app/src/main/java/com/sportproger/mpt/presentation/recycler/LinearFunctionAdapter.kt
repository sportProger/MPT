package com.sportproger.mpt.presentation.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sportproger.mpt.R
import com.sportproger.mpt.databinding.FunctionsItemBinding
import com.sportproger.mpt.presentation.recycler.model.LinearFunction

class LinearFunctionAdapter(): RecyclerView.Adapter<LinearFunctionAdapter.LinearFunctionHolder>() {
    private val linearFunctionList = ArrayList<LinearFunction>()

    class LinearFunctionHolder(private val item: View): RecyclerView.ViewHolder(item) {
        val binding = FunctionsItemBinding.bind(item)
        var flag = false

        fun bind(linearFunction: LinearFunction) = with(binding) {
            nameFunSQRT.text = "F(${linearFunction.x})="
            axSQRT.text = "${linearFunction.a}x"
            firstSignForSQRTFun.text = linearFunction.sign
            bxSQRT.text = "${linearFunction.b}x"
            resForFunctions.text = linearFunction.userAnswer.toString()

            fun replaceColor(color: String) {
                nameFunSQRT.setTextColor(Color.parseColor(color))
                axSQRT.setTextColor(Color.parseColor(color))
                firstSignForSQRTFun.setTextColor(Color.parseColor(color))
                bxSQRT.setTextColor(Color.parseColor(color))
                textView21.setTextColor(Color.parseColor(color))
                resForFunctions.setTextColor(Color.parseColor(color))
            }

            if (linearFunction.result != linearFunction.userAnswer) replaceColor("#E09F9F")

            item.setOnClickListener {
                if (linearFunction.result != linearFunction.userAnswer) {
                    if (!flag) {
                        replaceColor("#85DA97")
                        resForFunctions.text = linearFunction.result.toString()
                        flag = true
                    }
                    else {
                        replaceColor("#E09F9F")
                        resForFunctions.text = linearFunction.userAnswer.toString()
                        flag = false
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinearFunctionHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.functions_item, parent, false)
        return LinearFunctionHolder(view)
    }

    override fun onBindViewHolder(holder: LinearFunctionHolder, position: Int) {
        holder.bind(linearFunctionList[position])
    }

    override fun getItemCount(): Int {
        return linearFunctionList.size
    }

    fun addExample(example: LinearFunction) {
        linearFunctionList.add(example)
        notifyDataSetChanged()
    }

    fun clear() {
        linearFunctionList.clear()
    }
}
