package com.sportproger.mpt.presentation.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sportproger.mpt.R
import com.sportproger.mpt.databinding.DegreeItemBinding
import com.sportproger.mpt.presentation.recycler.model.Degree

class DegreeAdapter(): RecyclerView.Adapter<DegreeAdapter.DegreeHolder>() {
    private val degreeList = ArrayList<Degree>()

    class DegreeHolder(private val item: View): RecyclerView.ViewHolder(item) {
        val binding = DegreeItemBinding.bind(item)
        var flag = false

        fun bind(degree: Degree) = with(binding) {
            baseOfDegree1.text  = degree.base1.toString()
            exponent1.text      = degree.exponent1.toString()
            signForDegree.text  = degree.sign
            baseOfDegree2.text  = degree.base2.toString()
            exponent2.text      = degree.exponent2.toString()
            resDegree.text      = degree.userAnswer.toString()

            fun replaceColor(color: String) {
                baseOfDegree1.setTextColor(Color.parseColor(color))
                exponent1.setTextColor(Color.parseColor(color))
                signForDegree.setTextColor(Color.parseColor(color))
                baseOfDegree2.setTextColor(Color.parseColor(color))
                exponent2.setTextColor(Color.parseColor(color))
                equalsForDegree.setTextColor(Color.parseColor(color))
                resDegree.setTextColor(Color.parseColor(color))
            }

            if (degree.result != degree.userAnswer) replaceColor("#E09F9F")

            item.setOnClickListener {
                if (degree.result != degree.userAnswer) {
                    if (!flag) {
                        replaceColor("#85DA97")
                        resDegree.text = degree.result.toString()
                        flag = true
                    }
                    else {
                        replaceColor("#E09F9F")
                        resDegree.text = degree.userAnswer.toString()
                        flag = false
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DegreeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.degree_item, parent, false)
        return DegreeHolder(view)
    }

    override fun onBindViewHolder(holder: DegreeHolder, position: Int) {
        holder.bind(degreeList[position])
    }

    override fun getItemCount(): Int {
        return degreeList.size
    }

    fun addExample(example: Degree) {
        degreeList.add(example)
        notifyDataSetChanged()
    }

    fun clear() {
        degreeList.clear()
    }
}