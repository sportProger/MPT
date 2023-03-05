package com.sportproger.mpt.presentation.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sportproger.mpt.R
import com.sportproger.mpt.databinding.LogarithmItemBinding
import com.sportproger.mpt.databinding.SimpleExampleItemBinding
import com.sportproger.mpt.presentation.recycler.model.Integer
import com.sportproger.mpt.presentation.recycler.model.Logarithm

class LogarithmAdapter(): RecyclerView.Adapter<LogarithmAdapter.LogarithmHolder>() {
    private val logarithmList = ArrayList<Logarithm>()

    class LogarithmHolder(private val item: View): RecyclerView.ViewHolder(item) {
        val binding = LogarithmItemBinding.bind(item)
        var flag = false

        fun bind(logarithm: Logarithm) = with(binding) {
            baseOfLogarighm1.text = logarithm.baseOfLogarithm.toString()
            logarighmicNumber1.text = logarithm.logarithmicNumber.toString()
            resLogarighm.text = logarithm.userAnswer.toString()

            fun replaceColor(color: String) {
                logarighmicNumber1.setTextColor(Color.parseColor(color))
                baseOfLogarighm1.setTextColor(Color.parseColor(color))
                equalsForLogarithm.setTextColor(Color.parseColor(color))
                resLogarighm.setTextColor(Color.parseColor(color))
                nameLogarighm1.setTextColor(Color.parseColor(color))
            }

            if (logarithm.result != logarithm.userAnswer) replaceColor("#E09F9F")

            item.setOnClickListener {
                if (logarithm.result != logarithm.userAnswer) {
                    if (!flag) {
                        replaceColor("#85DA97")
                        resLogarighm.text = logarithm.result.toString()
                        flag = true
                    }
                    else {
                        replaceColor("#E09F9F")
                        resLogarighm.text = logarithm.userAnswer.toString()
                        flag = false
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogarithmHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.logarithm_item, parent, false)
        return LogarithmHolder(view)
    }

    override fun onBindViewHolder(holder: LogarithmHolder, position: Int) {
        holder.bind(logarithmList[position])
    }

    override fun getItemCount(): Int {
        return logarithmList.size
    }

    fun addExample(example: Logarithm) {
        logarithmList.add(example)
        notifyDataSetChanged()
    }

    fun clear() {
        logarithmList.clear()
    }
}