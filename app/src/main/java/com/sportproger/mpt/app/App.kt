package com.sportproger.mpt.app

import android.app.Application
import android.content.Context
import android.provider.Settings
import com.my.tracker.MyTracker
import com.sportproger.mpt.di.appModule
import com.sportproger.mpt.di.dataModule
import com.sportproger.mpt.di.domainModule
import com.sportproger.mpt.general.Conf
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.net.NetworkInterface
import java.util.*

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, domainModule, dataModule))
        }

        val trackerParams = MyTracker.getTrackerParams()


        trackerParams.setCustomParam("android_id", getAndroidId(applicationContext))
        trackerParams.setCustomParam("mac", getMac())

        val trackerConfig = MyTracker.getTrackerConfig()

        trackerConfig.isTrackingLaunchEnabled = true
        trackerConfig.isTrackingPreinstallEnabled = true

        MyTracker.initTracker(Conf.SDK_KEY, this)
    }

    fun getAndroidId(context: Context): String? {
        try {
            val cr = context.contentResolver
            if (cr != null) { return Settings.Secure.getString(cr, Settings.Secure.ANDROID_ID) }
        } catch (e: Throwable) {}
        return null
    }

    // Метод получения MAC
    fun getMac(): String? {
        try {
            val all: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (nif in all) {
                if (!nif.name.equals("wlan0", ignoreCase = true)) { continue }
                try {
                    val macBytes = nif.hardwareAddress ?: return null
                    val result = StringBuilder()
                    for (b in macBytes) { result.append(String.format("%02X:", b)) }
                    val length = result.length
                    if (length > 0) { result.deleteCharAt(length - 1) }
                    return result.toString()
                }
                catch (e: Throwable) {}
            }
        }
        catch (e: Throwable) {}

        return null
    }
}