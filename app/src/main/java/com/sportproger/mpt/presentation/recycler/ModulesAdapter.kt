package com.sportproger.mpt.presentation.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sportproger.mpt.R
import com.sportproger.mpt.databinding.ModulesItemBinding
import com.sportproger.mpt.presentation.recycler.model.Module

class ModulesAdapter(): RecyclerView.Adapter<ModulesAdapter.ModulesHolder>() {
    private val modulesList = ArrayList<Module>()

    class ModulesHolder(private val item: View): RecyclerView.ViewHolder(item) {
        val binding = ModulesItemBinding.bind(item)
        val view = item
        var flag = false

        fun bind(module: Module) = with(binding) {
            number1.text     = module.number1.toString()
            signModules.text = module.sign
            number2.text     = module.number2.toString()
            resModules.text  = module.userAnswer.toString()

            fun replaceColor(color: String, imageId: Int) {
                number1.setTextColor(Color.parseColor(color))
                signModules.setTextColor(Color.parseColor(color))
                number2.setTextColor(Color.parseColor(color))
                equalsForModules.setTextColor(Color.parseColor(color))
                resModules.setTextColor(Color.parseColor(color))

                lineForModules1.setImageDrawable( ContextCompat.getDrawable(view.context, imageId) )
                lineForModules2.setImageDrawable( ContextCompat.getDrawable(view.context, imageId) )
                lineForModules3.setImageDrawable( ContextCompat.getDrawable(view.context, imageId) )
                lineForModules4.setImageDrawable( ContextCompat.getDrawable(view.context, imageId) )
            }

            if (module.result != module.userAnswer) replaceColor("#E09F9F", R.drawable.ic_red_vertical_line)

            item.setOnClickListener {
                if (module.result != module.userAnswer) {
                    if (!flag) {
                        replaceColor("#85DA97", R.drawable.ic_green_line_vertical)
                        resModules.text = module.result.toString()
                        flag = true
                    }
                    else {
                        replaceColor("#E09F9F", R.drawable.ic_red_vertical_line)
                        resModules.text = module.userAnswer.toString()
                        flag = false
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModulesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.modules_item, parent, false)
        return ModulesHolder(view)
    }

    override fun onBindViewHolder(holder: ModulesHolder, position: Int) {
        holder.bind(modulesList[position])
    }

    override fun getItemCount(): Int {
        return modulesList.size
    }

    fun addExample(example: Module) {
        modulesList.add(example)
        notifyDataSetChanged()
    }

    fun clear() {
        modulesList.clear()
    }
}