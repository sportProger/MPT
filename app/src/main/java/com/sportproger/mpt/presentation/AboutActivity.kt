package com.sportproger.mpt.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.sportproger.mpt.general.Base
import com.sportproger.mpt.databinding.ActivityAboutBinding
import com.sportproger.mpt.presentation.viewmodel.AboutViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutActivity : Base() {
    lateinit var binding: ActivityAboutBinding
    private val vm by viewModel<AboutViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() = with(binding) {
        super.onStart()
        back(); share(share)

        vm.getNumberOfCoins()
        vm.numberOfCoinsLive().observe(this@AboutActivity, {
            tvCoinCount.text = it.toString()
        })

        vk1.setOnClickListener { startActivity( Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/sp323")) ) }
        patreon.setOnClickListener { startActivity( Intent(Intent.ACTION_VIEW, Uri.parse("https://www.patreon.com/sportproger")) ) }
    }
}