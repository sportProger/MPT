package com.sportproger.mpt.presentation.recycler

import android.annotation.SuppressLint
import android.graphics.Color
import android.provider.DocumentsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sportproger.mpt.R
import com.sportproger.mpt.databinding.RootItemBinding
import com.sportproger.mpt.general.Conf
import com.sportproger.mpt.presentation.recycler.model.Root

class RootAdapter: RecyclerView.Adapter<RootAdapter.RootHolder>() {
    private val rootList = ArrayList<Root>()

    class RootHolder(val item: View): RecyclerView.ViewHolder(item) {
        val binding = RootItemBinding.bind(item)
        var flag = false

        @SuppressLint("SetTextI18n")
        fun bind(root: Root) = with(binding) {
            fun replaceColor(color: String) {
                rootOne1.setTextColor(Color.parseColor(color))
                rootTwo1.setTextColor(Color.parseColor(color))
                rootTwoSign.setTextColor(Color.parseColor(color))
                rootTwo2.setTextColor(Color.parseColor(color))
                textView40.setTextColor(Color.parseColor(color))
                rootRes.setTextColor(Color.parseColor(color))
            }

            if (root.type == Conf.ROOT_TYPES.ONE.name) {
                constraintRootTwo.visibility = View.GONE
                constraintRootOne.visibility = View.VISIBLE

                rootOne1.text = "sqrt(${root.baseRoot1}, ${root.exponent1})"
                binding.rootRes.text = root.userAnswer.toString()
            }
            if (root.type == Conf.ROOT_TYPES.TWO.name) {
                constraintRootOne.visibility = View.GONE
                constraintRootTwo.visibility = View.VISIBLE

                rootTwo1.text = "sqrt(${root.baseRoot1}, ${root.exponent1})"
                rootTwoSign.text = root.sign
                rootTwo2.text = "sqrt(${root.baseRoot2}, ${root.exponent2})"
                binding.rootRes.text = root.userAnswer.toString()
            }

            if (root.result != root.userAnswer) replaceColor("#E09F9F")

            item.setOnClickListener {
                if (root.result != root.userAnswer) {
                    if (!flag) {
                        replaceColor("#85DA97")
                        rootRes.text = root.result.toString()
                        flag = true
                    }
                    else {
                        replaceColor("#E09F9F")
                        rootRes.text = root.userAnswer.toString()
                        flag = false
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RootHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.root_item, parent, false)
        return RootHolder(view)
    }

    override fun onBindViewHolder(holder: RootHolder, position: Int) {
        holder.bind(rootList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun getItemCount(): Int {
        return rootList.size
    }

    fun addExample(example: Root) {
        rootList.add(example)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        rootList.clear()
        notifyDataSetChanged()
    }
}