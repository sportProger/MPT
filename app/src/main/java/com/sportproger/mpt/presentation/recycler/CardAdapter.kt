package com.sportproger.mpt.presentation.recycler

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sportproger.mpt.*
import com.sportproger.mpt.databinding.CardItemBinding
import com.sportproger.mpt.general.Conf
import com.sportproger.mpt.presentation.recycler.model.Card

class CardAdapter(private val context: Context): RecyclerView.Adapter<CardAdapter.CardHolder>(){
    val cardList = ArrayList<Card>()
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int, cardName: String)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    fun getPurchaseStatusForLevel(levelName: String): Boolean {
        val NAME_SHARED_PREF = "SHARED_PREF"
        val sharedPreferences = context.getSharedPreferences(NAME_SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(levelName, false)
    }

    inner class CardHolder(val item: View, listener: onItemClickListener): RecyclerView.ViewHolder(item) {
        val binding = CardItemBinding.bind(item)

        init {
            item.setOnClickListener {
               listener.onItemClick(adapterPosition, binding.name.text.toString())
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(card: Card) {
            binding.price.text = card.price
            binding.howMany.text = "${card.how_mach_decided} из ${card.how_mach_decided.toInt() + card.how_mach_not_decided.toInt()}"
            binding.name.text = card.name

            var state = true
            when(card.name) {
                Conf.INTEGERS_RU -> state = true
                Conf.MODULES_RU  -> state = getPurchaseStatusForLevel(Conf.MODULES)
                Conf.FRACTION_RU -> state = getPurchaseStatusForLevel(Conf.FRACTION)
                Conf.EQUATION_RU -> state = getPurchaseStatusForLevel(Conf.EQUATION)
                Conf.DEGREE_RU   -> state = getPurchaseStatusForLevel(Conf.DEGREE)
                Conf.LINEAR_FUNCTIONS_RU -> state = getPurchaseStatusForLevel(Conf.LINEAR_FUNCTIONS)
                Conf.LOGARITHM_RU -> state = getPurchaseStatusForLevel(Conf.LOGARITHM)
            }

            if (state) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.card.backgroundTintList = context.getColorStateList(R.color.green)
                }
                Log.d(Conf.MY_LOG, "Purchase status for Logarithm - $state")
                binding.price.setTextColor(Color.parseColor("#FAFAFA"))
                binding.howMany.setTextColor(Color.parseColor("#FAFAFA"))
                binding.name.setTextColor(Color.parseColor("#FAFAFA"))
                binding.textView6.setTextColor(Color.parseColor("#FAFAFA"))
                binding.textView7.setTextColor(Color.parseColor("#FAFAFA"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return CardHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.bind(cardList[position])
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    fun clear() {
        cardList.clear()
    }

    fun addCard(card: Card) {
        cardList.add(card)
        notifyDataSetChanged()
    }
}