package com.sportproger.mpt.presentation.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sportproger.mpt.R
import com.sportproger.mpt.databinding.GameItemBinding
import com.sportproger.mpt.presentation.recycler.model.Game

class GameAdapter(private val context: Context): RecyclerView.Adapter<GameAdapter.GameHolder>() {
    private val gameList = ArrayList<Game>()
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int, gameName: String)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    inner class GameHolder(val item: View, listener: onItemClickListener): RecyclerView.ViewHolder(item) {
        val binding = GameItemBinding.bind(item)

        init {
            item.setOnClickListener {
                listener.onItemClick(adapterPosition, binding.gameName.text.toString())
            }
        }

        fun bind(game: Game) {
            binding.gameName.text = game.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return GameHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        holder.bind(gameList[position])
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    fun addGame(game: Game) {
        gameList.add(game)
        notifyDataSetChanged()
    }

    fun clear() {
        gameList.clear()
        notifyDataSetChanged()
    }
}