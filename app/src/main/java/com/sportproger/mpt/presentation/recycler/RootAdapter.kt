package com.sportproger.mpt.presentation.recycler

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.provider.DocumentsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sportproger.mpt.R
import com.sportproger.mpt.databinding.RootItemBinding
import com.sportproger.mpt.general.Conf
import com.sportproger.mpt.general.DrawImpl
import com.sportproger.mpt.presentation.recycler.model.Root

class RootAdapter(private val context: Context): RecyclerView.Adapter<RootAdapter.RootHolder>() {
    private val rootList = ArrayList<Root>()

    inner class RootHolder(val item: View): RecyclerView.ViewHolder(item) {
        val binding = RootItemBinding.bind(item)
        var flag = false

        @SuppressLint("SetTextI18n")
        fun bind(root: Root) {
            val rect = Rect()
            rect[100, 100, 300] = 300

            val canvas = Canvas()
            lateinit var bitmap: Bitmap
            val textSize = 45f

            fun replaceColor(color: Int) {
                if (root.type == Conf.ROOT_TYPES.ONE.name) {
                    val draw = DrawImpl(context, canvas)
                    draw.drawRoot1(root.baseRoot1.toString(), root.exponent1.toString(), 3f, textSize, color)

                    binding.rootRes.text = root.userAnswer.toString()
                }
                if (root.type == Conf.ROOT_TYPES.TWO.name) {
                    val draw = DrawImpl(context, canvas)
                    draw.drawRoot1(root.baseRoot1.toString(), root.exponent1.toString(), 3f, textSize, color)
                    draw.drawSign(root.sign, 55f, root.baseRoot1.toString(), color)
                    draw.drawRoot2(root.baseRoot2.toString(), root.exponent2.toString(),3f, textSize, 200f + root.baseRoot1.toString().length * 40f, color)

                    binding.rootRes.text = root.userAnswer.toString()
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.textView40.setTextColor(context.getColor(color))
                    binding.rootRes.setTextColor(context.getColor(color))
                }
            }

            if (root.type == Conf.ROOT_TYPES.ONE.name) {
                bitmap = Bitmap.createBitmap(( root.baseRoot1.toString().length * 40f + 120f ).toInt() + 20, 90, Bitmap.Config.ARGB_8888)
                canvas.setBitmap(bitmap)

                val draw = DrawImpl(context, canvas)
                draw.drawRoot1(root.baseRoot1.toString(), root.exponent1.toString(), 3f, textSize, R.color.green)

                binding.rootRes.text = root.userAnswer.toString()
            }
            if (root.type == Conf.ROOT_TYPES.TWO.name) {
                bitmap = Bitmap.createBitmap(root.baseRoot1.toString().length * 60 + 50 + 150 + 60 + root.baseRoot2.toString().length * 50, 90, Bitmap.Config.ARGB_8888)
                canvas.setBitmap(bitmap)

                val draw = DrawImpl(context, canvas)
                draw.drawRoot1(root.baseRoot1.toString(), root.exponent1.toString(), 3f, textSize, R.color.green)
                draw.drawSign(root.sign, textSize, root.baseRoot1.toString(), R.color.green)
                draw.drawRoot2(root.baseRoot2.toString(), root.exponent2.toString(),3f, textSize, 200f + root.baseRoot1.toString().length * 40f, R.color.green)

                binding.rootRes.text = root.userAnswer.toString()
            }

            val imageView = ImageView(context)
            imageView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            imageView.setBackgroundColor(Color.TRANSPARENT)
            imageView.scaleType = ImageView.ScaleType.CENTER
            imageView.setImageBitmap(bitmap)
            binding.constraintRootTwo.removeAllViews()
            binding.constraintRootTwo.addView(imageView)

            if (root.result != root.userAnswer) replaceColor(R.color.red)
            item.setOnClickListener {
                if (root.result != root.userAnswer) {
                    if (!flag) {
                        replaceColor(R.color.green)
                        binding.rootRes.text = root.result.toString()
                        flag = true
                    }
                    else {
                        replaceColor(R.color.red)
                        binding.rootRes.text = root.userAnswer.toString()
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