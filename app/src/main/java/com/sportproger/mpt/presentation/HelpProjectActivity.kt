package com.sportproger.mpt.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.sportproger.mpt.databinding.ActivityHelpProjectBinding
import com.sportproger.mpt.general.Base
import com.sportproger.mpt.presentation.viewmodel.HelpProjectViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HelpProjectActivity: Base() {
    lateinit var binding: ActivityHelpProjectBinding
    private val vm by viewModel<HelpProjectViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() = with(binding) {
        super.onStart()
        back(); share(share)

        vm.getNumberOfCoins()
        vm.numberOfCoinsLive().observe(this@HelpProjectActivity, {
            tvCoinCount.text = it.toString()
        })

        textView18.setOnClickListener {
            startActivity( Intent(Intent.ACTION_VIEW, Uri.parse("https://www.patreon.com/sportproger")) )
        }
        textView20.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", textView20.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this@HelpProjectActivity, "Номер счета скопирован", Toast.LENGTH_SHORT).show()
        }
    }
}