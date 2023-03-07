package com.sportproger.mpt.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sportproger.domain.module.PurchaseStatusForLevelData
import com.sportproger.mpt.R
import com.sportproger.mpt.general.Base
import com.sportproger.mpt.databinding.ActivityLevelsBinding
import com.sportproger.mpt.general.Conf
import com.sportproger.mpt.presentation.recycler.CardAdapter
import com.sportproger.mpt.presentation.recycler.model.Card
import com.sportproger.mpt.presentation.viewmodel.LevelsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LevelsActivity: Base() {
    lateinit var binding: ActivityLevelsBinding
    private val levels = ArrayList<Card>()
    private val cardAdapter = CardAdapter(this)
    private val vm by viewModel<LevelsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevelsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        back(); share(binding.share)

        vm.getNumberOfCoins()
        vm.numberOfCoinsLive().observe(this, { binding.tvCoinCount.text = it.toString() })

        cardAdapter.clear()
        levels.clear()

        val levelsArr = listOf(Conf.INTEGERS, Conf.FRACTION, Conf.EQUATION, Conf.DEGREE, Conf.LINEAR_FUNCTIONS, Conf.LOGARITHM)

        levelsArr.forEach { level ->
            val correct = vm.getNumberOfCorrectAnswer(level)
            val wrong = vm.getNumberOfWrongAnswer(level)
            when(level) {
                Conf.INTEGERS -> levels.add(Card(Conf.PRICE_FOR_INTEGERS.toString(), correct.toString(), wrong.toString(), Conf.INTEGERS_RU))
//                Conf.MODULES  -> levels.add(Card(Conf.PRICE_FOR_MODULES.toString(), correct.toString(), wrong.toString(), Conf.MODULES_RU))
                Conf.FRACTION -> levels.add(Card(Conf.PRICE_FOR_FRACTION.toString(), correct.toString(), wrong.toString(), Conf.FRACTION_RU))
                Conf.EQUATION -> levels.add(Card(Conf.PRICE_FOR_EQUATION.toString(), correct.toString(), wrong.toString(), Conf.EQUATION_RU))
                Conf.DEGREE   -> levels.add(Card(Conf.PRICE_FOR_DEGREE.toString(), correct.toString(), wrong.toString(), Conf.DEGREE_RU))
                Conf.LINEAR_FUNCTIONS -> levels.add(Card(Conf.PRICE_FOR_LINEAR_FUNCTIONS.toString(), correct.toString(), wrong.toString(), Conf.LINEAR_FUNCTIONS_RU))
                Conf.LOGARITHM -> levels.add(Card(Conf.PRICE_FOR_LOGARITHM.toString(), correct.toString(), wrong.toString(), Conf.LOGARITHM_RU))
            }
        }

        init()
    }

    // True - куплен
    // False - не куплен
    fun initGeneral(cardName: String, price: Int, nameLevel: String) {
        vm.getPurchaseStatusForLevel(nameLevel)
        vm.purchaseStatusForLevelLive().observe(this@LevelsActivity, { statePurchase ->
            if (!statePurchase) {
                val numberOfCoins = binding.tvCoinCount.text.toString().toInt()
                if (numberOfCoins > price) {
                    vm.setNumberOfCoins(numberOfCoins - price)
                    vm.setPurchaseStatusForLevel(PurchaseStatusForLevelData(nameLevel = nameLevel, status = true))
                    Log.d(Conf.MY_LOG, "Purchase status $nameLevel true")
                    vm.setLevel(nameLevel)
                    Toast.makeText(this@LevelsActivity,  getString(R.string.level_acquired), Toast.LENGTH_SHORT).show()
                    startLevelActivity(cardName)
                }
                else Toast.makeText(this@LevelsActivity,  getString(R.string.not_enough_money_level), Toast.LENGTH_SHORT).show()
            }
            else {
                vm.setLevel(nameLevel)
                startLevelActivity(cardName)
            }
        })
    }

    private fun init() = with(binding) {
        rvLevels.layoutManager = LinearLayoutManager(this@LevelsActivity)
        rvLevels.adapter = cardAdapter
        cardAdapter.setOnItemClickListener(object: CardAdapter.onItemClickListener {
            override fun onItemClick(position: Int, cardName: String) {
                when(cardName) {
                    Conf.INTEGERS_RU -> {
                        vm.setLevel(Conf.INTEGERS)
                        startLevelActivity(cardName)
                    }
//                    Conf.MODULES_RU  -> initGeneral(cardName, Conf.PRICE_FOR_MODULES, Conf.MODULES)
                    Conf.FRACTION_RU -> initGeneral(cardName, Conf.PRICE_FOR_FRACTION, Conf.FRACTION)
                    Conf.EQUATION_RU -> initGeneral(cardName, Conf.PRICE_FOR_EQUATION, Conf.EQUATION)
                    Conf.DEGREE_RU   -> initGeneral(cardName, Conf.PRICE_FOR_DEGREE, Conf.DEGREE)
                    Conf.LINEAR_FUNCTIONS_RU -> initGeneral(cardName, Conf.PRICE_FOR_LINEAR_FUNCTIONS, Conf.LINEAR_FUNCTIONS)
                    Conf.LOGARITHM_RU -> initGeneral(cardName, Conf.PRICE_FOR_LOGARITHM, Conf.LOGARITHM)
                }
            }

        })
        levels.forEach { card -> cardAdapter.addCard(card) }
    }

    private fun startLevelActivity(name: String) {
        startActivity(Intent(this, LevelActivity::class.java).apply {
            putExtra("name", name)
        })
    }
}