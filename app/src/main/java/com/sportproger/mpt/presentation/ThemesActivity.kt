package com.sportproger.mpt.presentation

import android.os.Bundle
import android.widget.Toast
import com.sportproger.mpt.R
import com.sportproger.mpt.general.Base
import com.sportproger.mpt.databinding.ActivityThemsBinding
import com.sportproger.mpt.general.Conf
import com.sportproger.mpt.presentation.viewmodel.ThemesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ThemesActivity: Base() {
    lateinit var binding: ActivityThemsBinding
    private val vm by viewModel<ThemesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() = with(binding) {
        super.onStart()
        back(); share(share)

        priceForDarkTheme.text = Conf.PRICE_FOR_DARK_THEME.toString()

        vm.getNumberOfCoins()
        vm.numberOfCoinsLive().observe(this@ThemesActivity, {
            tvCoinCount.text = it.toString()
        })

        standartTheme.setOnClickListener { vm.setCurrentTheme(Conf.STANDART_THEM) }
        darkTheme.setOnClickListener {
            vm.getPurchaseStatusForTheme(Conf.DARK_THEM)
            vm.purchaseStatusForThemeLive().observe(this@ThemesActivity, { status ->
                if (!status) {
                    vm.getNumberOfCoins()
                    vm.numberOfCoinsLive().observe(this@ThemesActivity, {
                        if (it >= Conf.PRICE_FOR_DARK_THEME) {
                            vm.setNumberOfCoins(it - Conf.PRICE_FOR_DARK_THEME)
                            vm.setPurchaseStatusForTheme(Conf.DARK_THEM)
                            vm.setCurrentTheme(Conf.DARK_THEM)
                        }
                        else {
                            Toast.makeText(this@ThemesActivity, getString(R.string.not_enough_money_dark_theme), Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                else {
                    vm.setCurrentTheme(Conf.DARK_THEM)
                }
            })
        }
    }
}