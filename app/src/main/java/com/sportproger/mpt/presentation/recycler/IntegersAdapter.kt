package com.sportproger.mpt.presentation.recycler

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.sportproger.mpt.R
import com.sportproger.mpt.databinding.SimpleExampleItemBinding
import com.sportproger.mpt.presentation.recycler.model.Integer
import kotlin.coroutines.coroutineContext

class IntegersAdapter(): RecyclerView.Adapter<IntegersAdapter.IntegersHolder>() {
    private val integersList = ArrayList<Integer>()

    class IntegersHolder(private val item: View): RecyclerView.ViewHolder(item) {
        val binding = SimpleExampleItemBinding.bind(item)
        var flag = false

        fun bind(integer: Integer) = with(binding) {
            num1.text = integer.number1.toString()
            sign.text = integer.sign
            num2.text = integer.number2.toString()
            res.text = integer.userAnswer.toString()

            fun replaceColor(color: String) {
                num1.setTextColor(Color.parseColor(color))
                sign.setTextColor(Color.parseColor(color))
                num2.setTextColor(Color.parseColor(color))
                equals.setTextColor(Color.parseColor(color))
                res.setTextColor(Color.parseColor(color))
            }

            if (integer.result != integer.userAnswer) replaceColor("#E09F9F")

            item.setOnClickListener {
                if (integer.result != integer.userAnswer) {
                    if (!flag) {
                        replaceColor("#85DA97")
                        res.text = integer.result.toString()
                        flag = true
                    }
                    else {
                        replaceColor("#E09F9F")
                        res.text = integer.userAnswer.toString()
                        flag = false
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntegersHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.simple_example_item, parent, false)
        return IntegersHolder(view)
    }

    override fun onBindViewHolder(holder: IntegersHolder, position: Int) {
        holder.bind(integersList[position])
    }

    override fun getItemCount(): Int {
        return integersList.size
    }

    fun addExample(example: Integer) {
        integersList.add(example)
        notifyDataSetChanged()
    }

    fun clear() {
        integersList.clear()
    }
}