package com.sportproger.mpt.presentation.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sportproger.mpt.R
import com.sportproger.mpt.databinding.EquationItemBinding
import com.sportproger.mpt.presentation.recycler.model.Equation

class EquationAdapter(): RecyclerView.Adapter<EquationAdapter.EquationHolder>() {
    private val equationList = ArrayList<Equation>()

    class EquationHolder(private val item: View): RecyclerView.ViewHolder(item) {
        val binding = EquationItemBinding.bind(item)
        private var flag = false

        fun bind(equation: Equation) = with(binding) {
            fun replaceColor(color: String) {
                equationSquareA.setTextColor(Color.parseColor(color))
                equationSquareB.setTextColor(Color.parseColor(color))
                equationSquareC.setTextColor(Color.parseColor(color))
                equationSquareSign1.setTextColor(Color.parseColor(color))
                equationSquareSign2.setTextColor(Color.parseColor(color))
                equationRes.setTextColor(Color.parseColor(color))
                textView44.setTextColor(Color.parseColor(color))
                textView48.setTextColor(Color.parseColor(color))
                textView53.setTextColor(Color.parseColor(color))
            }

            if (equation.type == "linear") {
                constraintEquationSquare.visibility = View.GONE
                constraintEquationLinear.visibility = View.VISIBLE

                equationLinearA.text = equation.a.toString()
                equationLinearSign.text = equation.sign1
                equationLinearB.text = equation.b.toString()
                equationRes.text = equation.linearResult.toString()

                val userAnswer = equation.userLinearAnswer
                if (userAnswer != equation.linearResult) replaceColor("#E09F9F")

                item.setOnClickListener {
                    if (equation.linearResult != userAnswer) {
                        if (!flag) {
                            replaceColor("#85DA97")
                            equationRes.text = equation.linearResult.toString()
                            flag = true
                        }
                        else {
                            replaceColor("#E09F9F")
                            equationRes.text = userAnswer.toString()
                            flag = false
                        }
                    }
                }
            }
            else {
                constraintEquationLinear.visibility = View.GONE
                constraintEquationSquare.visibility = View.VISIBLE

                equationSquareA.text = equation.a.toString()
                equationSquareB.text = equation.b.toString()
                equationSquareC.text = equation.c.toString()
                equationSquareSign1.text = equation.sign1
                equationSquareSign2.text = equation.sign2
                equationRes.text = equation.squareResult.toString()

                val firstUserAnswer = equation.userSquareAnswer?.get(0)
                if (firstUserAnswer != equation.squareResult?.get(0) && firstUserAnswer != equation.squareResult?.get(1)) replaceColor("#E09F9F")

                item.setOnClickListener {
                    if (equation.squareResult?.get(0) != firstUserAnswer && equation.squareResult?.get(1) != firstUserAnswer) {
                        if (!flag) {
                            replaceColor("#85DA97")
                            equationRes.text = equation.linearResult.toString()
                            flag = true
                        }
                        else {
                            replaceColor("#E09F9F")
                            equationRes.text = firstUserAnswer.toString()
                            flag = false
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.equation_item, parent, false)
        return EquationHolder(view)
    }

    override fun onBindViewHolder(holder: EquationHolder, position: Int) {
        holder.bind(equationList[position])
    }

    override fun getItemCount(): Int {
        return equationList.size
    }

    fun addExample(example: Equation) {
        equationList.add(example)
        notifyDataSetChanged()
    }

    fun clear() {
        equationList.clear()
    }
}