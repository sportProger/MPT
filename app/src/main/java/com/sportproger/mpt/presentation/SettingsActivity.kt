package com.sportproger.mpt.presentation

import android.app.*
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.sportproger.domain.module.TypeNumbersData
import com.sportproger.mpt.databinding.ActivitySettingsBinding
import com.sportproger.mpt.general.*
import com.sportproger.mpt.general.Notification
import com.sportproger.mpt.presentation.viewmodel.SettingsViewModel
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.common.MobileAds
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class SettingsActivity: Base() {
    lateinit var binding: ActivitySettingsBinding
    private val vm by viewModel<SettingsViewModel>()
    private var notificationFlag = false
    private val pendingIntent by lazy {
        val intent = Intent(this, Notification::class.java)
        PendingIntent.getBroadcast(this, 0, intent, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.timePicker.setIs24HourView(true)
//        createNotificationChannel()
    }

    override fun onStart() = with(binding) {
        super.onStart()
        back(); share(share)

        vm.getNumberOfCoins()
        vm.numberOfCoinsLive().observe(this@SettingsActivity, {
            tvCoinCount.text = it.toString()
        })

        vm.getSounds()
        vm.soundsLive().observe(this@SettingsActivity, {
            voiceSwitch.isChecked = it
        })

        vm.getNotificationState()
        vm.notificationStateLive().observe(this@SettingsActivity, {
            notificationSwitch.isChecked = it
        })

        save.setOnClickListener {
            val voiceIsChecked = voiceSwitch.isChecked
//            val notificationChecked = notificationSwitch.isChecked

            when(spinner.selectedItem.toString()) {
                Conf.UNAMBIGUOUS_RU -> vm.setTypeNumbers(TypeNumbersData(Conf.UNAMBIGUOUS, 0, 9))
                Conf.TWO_DIGIT_RU   -> vm.setTypeNumbers(TypeNumbersData(Conf.TWO_DIGIT, 10, 99))
                Conf.THREE_DIGIT_RU -> vm.setTypeNumbers(TypeNumbersData(Conf.THREE_DIGIT, 100, 999))
            }

            vm.setSounds(voiceIsChecked)
            vm.setNotificationSate(false)
//            vm.setNotificationSate(notificationChecked)
//            if (binding.notificationSwitch.isChecked) scheduleNotification()
//            if (norifiFlag) {
//                vm.setNotificationTime(NotificationTime(
//                    minute = binding.timePicker.minute,
//                    hour = binding.timePicker.hour,
//                    dayOfMonth = binding.datePicker.dayOfMonth,
//                    month = binding.datePicker.month,
//                    year = binding.datePicker.year
//                ))
//            }
            startActivity(Intent(this@SettingsActivity, MainActivity::class.java))
            finish()
        }

        notificationSwitch.setOnClickListener {
            if (notificationSwitch.isChecked) {
                constraintLayoutTime.visibility = View.VISIBLE
            }
        }

        saveButton.setOnClickListener {
            notificationFlag = true
            constraintLayoutTime.visibility = View.GONE
        }

        showAd()
    }

    private fun showAd() {
        MobileAds.initialize(this) { Log.d(Conf.AD_LOG, "SDK initialized") }
        binding.bannerAdView.setAdUnitId(Conf.AdUnitIdForBanner)
        binding.bannerAdView.setAdSize(AdSize.BANNER_320x100)

        val adRequest = AdRequest.Builder().build()
        val bannerAdListener = object: BannerAdEventListener {
            override fun onAdLoaded() { Log.d(Conf.AD_LOG, "Settings banner is loaded") }
            override fun onAdFailedToLoad(p0: AdRequestError) { Log.d(Conf.AD_LOG, "$p0 - Settings banner is error") }
            override fun onImpression(p0: ImpressionData?) { Log.d(Conf.AD_LOG, "$p0 - Settings banner ok") }
            override fun onAdClicked() {}
            override fun onLeftApplication() {}
            override fun onReturnedToApplication() {}
        }

        binding.bannerAdView.setBannerAdEventListener(bannerAdListener)
        binding.bannerAdView.loadAd(adRequest)
    }

    private fun scheduleNotification() {
        val intent = Intent(applicationContext, Notification::class.java)
        val title = "MPT"
        val message = "Пора решать примеры"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

//        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val time = getTime()
//        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            time,
//            AlarmManager.INTERVAL_DAY,
//            pendingIntent
//        )
    }

//    private fun getTime(): Long {
//        val calendar = Calendar.getInstance()
//        vm.getNotificationTime()
//        vm.notificationTimeLive().observe(this, {
//            calendar.set(it.year, it.month, Calendar.DAY_OF_MONTH, it.hour, it.minute)
//        })
//
//        return calendar.timeInMillis
//    }

//    private fun createNotificationChannel() {
//        val name = "Notif Channel"
//        val desc = "A Description of the Channel"
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val channel = NotificationChannel(channelID, name, importance)
//        channel.description = desc
//        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//    }
}