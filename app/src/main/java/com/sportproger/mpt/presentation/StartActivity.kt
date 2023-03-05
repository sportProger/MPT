package com.sportproger.mpt.presentation

import android.content.Intent
import android.os.Bundle
import com.sportproger.mpt.general.Base
import com.sportproger.mpt.databinding.ActivityStartBinding
import com.sportproger.mpt.general.Conf
import com.sportproger.mpt.presentation.viewmodel.StartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartActivity : Base() {
    lateinit var binding: ActivityStartBinding
    private val vm by viewModel<StartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        vm.setCurrentTheme(Conf.STANDART_THEM)
        vm.setNumberOfCoins(Conf.START_COIN)
        vm.setLevel(Conf.INTEGERS)

        binding.next.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            finish()
        }
    }
}