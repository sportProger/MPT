package com.sportproger.mpt.presentation.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sportproger.mpt.R
import com.sportproger.mpt.databinding.FractionItemBinding
import com.sportproger.mpt.databinding.SimpleExampleItemBinding
import com.sportproger.mpt.presentation.recycler.model.Fraction
import com.sportproger.mpt.presentation.recycler.model.Integer

class FractionAdapter(): RecyclerView.Adapter<FractionAdapter.FractionHolder>() {
    private val fractionList = ArrayList<Fraction>()

    class FractionHolder(private val item: View): RecyclerView.ViewHolder(item) {
        val binding = FractionItemBinding.bind(item)
        val view = item
        var flag = false

        fun bind(fraction: Fraction) = with(binding) {
            numerator1.text = fraction.numerator1.toString()
            denominator1.text = fraction.denominator1.toString()
            signF.text = fraction.sign
            numerator2.text = fraction.numerator2.toString()
            denominator2.text = fraction.denominator2.toString()
            resF.text = fraction.userAnswer.toString()

            fun replaceColor(color: String) {
                numerator1.setTextColor(Color.parseColor(color))
                numerator2.setTextColor(Color.parseColor(color))
                denominator1.setTextColor(Color.parseColor(color))
                denominator2.setTextColor(Color.parseColor(color))
                signF.setTextColor(Color.parseColor(color))
                equalsF.setTextColor(Color.parseColor(color))
                resF.setTextColor(Color.parseColor(color))

                if (color == "#85DA97") {
                    lineForFraction1.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_green_horizontal_line))
                    lineForFraction2.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_green_horizontal_line))
                }
                if (color == "#E09F9F") {
                    lineForFraction1.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_red_horizontal_line))
                    lineForFraction2.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_red_horizontal_line))
                }
            }

            if (fraction.result != fraction.userAnswer) replaceColor("#E09F9F")

            item.setOnClickListener {
                if (fraction.result != fraction.userAnswer) {
                    if (!flag) {
                        replaceColor("#85DA97")
                        resF.text = fraction.result.toString()
                        flag = true
                    }
                    else {
                        replaceColor("#E09F9F")
                        resF.text = fraction.userAnswer.toString()
                        flag = false
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FractionHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fraction_item, parent, false)
        return FractionHolder(view)
    }

    override fun onBindViewHolder(holder: FractionHolder, position: Int) {
        holder.bind(fractionList[position])
    }

    override fun getItemCount(): Int {
        return fractionList.size
    }

    fun addExample(example: Fraction) {
        fractionList.add(example)
        notifyDataSetChanged()
    }

    fun clear() {
        fractionList.clear()
    }
}