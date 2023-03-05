package com.sportproger.mpt.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sportproger.mpt.R
import com.sportproger.mpt.databinding.ActivityGameBinding
import com.sportproger.mpt.databinding.ActivityMainBinding
import android.webkit.WebResourceError

import android.webkit.WebResourceRequest

import android.webkit.WebView

import android.annotation.TargetApi

import android.widget.Toast

import android.webkit.WebViewClient

import android.app.Activity
import android.os.Build
import com.sportproger.mpt.general.Conf


class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)

        val webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        webView.settings.mediaPlaybackRequiresUserGesture = true

        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                Toast.makeText(this@GameActivity, description, Toast.LENGTH_SHORT).show()
            }

            @TargetApi(Build.VERSION_CODES.M)
            override fun onReceivedError(
                view: WebView,
                req: WebResourceRequest,
                rerr: WebResourceError
            ) {
                onReceivedError(
                    view,
                    rerr.errorCode,
                    rerr.description.toString(),
                    req.url.toString()
                )
            }
        }

        when(intent.getStringExtra("name")) {
            Conf.CONNECTION_GAME -> { webView.loadUrl(getString(R.string.connection_game_url)) }
            Conf.SEARCH_GAME     -> { webView.loadUrl(getString(R.string.search_game_url)) }
        }

        setContentView(webView)
    }
}