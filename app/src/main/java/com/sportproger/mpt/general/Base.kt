package com.sportproger.mpt.general

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.sportproger.mpt.R

open class Base(): AppCompatActivity() {
    fun back() {
        findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
    }

    fun share(share: View) {
        share.setOnClickListener {
            val i = Intent()
            i.action = Intent.ACTION_SEND
            i.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_url)) // Заменить ссылку
            i.type = "text/plain"
            startActivity(Intent.createChooser(i, getString(R.string.share)))
        }
    }
}