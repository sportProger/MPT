package com.sportproger.mpt.presentation

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sportproger.mpt.databinding.ActivityGamesBinding
import com.sportproger.mpt.general.Base
import com.sportproger.mpt.general.Conf
import com.sportproger.mpt.presentation.recycler.GameAdapter
import com.sportproger.mpt.presentation.recycler.model.Game
import com.sportproger.mpt.presentation.viewmodel.GamesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GamesActivity : Base() {
    private lateinit var binding: ActivityGamesBinding
    private val games = ArrayList<Game>()
    private val gameAdapter = GameAdapter(this)
    private val vm by viewModel<GamesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        back()
        share(binding.share)

        vm.getNumberOfCoins()
        vm.numberOfCoinsLive().observe(this, {
            binding.tvCoinCount.text = it.toString()
        })

        games.clear()
        gameAdapter.clear()

        val gamesList = listOf(Conf.CONNECTION_GAME, Conf.SEARCH_GAME)
        gamesList.forEach { gameName ->
            games.add(Game(name = gameName))
        }

        init()
    }

    private fun init() = with(binding) {
        rvGames.layoutManager = LinearLayoutManager(this@GamesActivity)
        rvGames.adapter = gameAdapter
        gameAdapter.setOnItemClickListener(object: GameAdapter.onItemClickListener {
            override fun onItemClick(position: Int, gameName: String) {
                when(gameName) {
                    Conf.CONNECTION_GAME -> {
                        startActivity(Intent(this@GamesActivity, GameActivity::class.java).apply {
                            putExtra("name", gameName)
                        })
                    }
                    Conf.SEARCH_GAME -> {
                        startActivity(Intent(this@GamesActivity, GameActivity::class.java).apply {
                            putExtra("name", gameName)
                        })
                    }
                }
            }
        })

        games.forEach { game -> gameAdapter.addGame(game) }
    }
}