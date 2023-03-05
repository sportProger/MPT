package com.sportproger.data.storage

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.sportproger.data.storage.module.NotificationTimeDataStorage
import com.sportproger.data.storage.module.PurchaseStatusForLevelDataStorage
import com.sportproger.data.storage.module.TypeNumbersDataStorage
import com.sportproger.domain.module.TypeNumbersData

class UserStorageImpl(context: Context): UserStorage {
    private val NAME_SHARED_PREF = "SHARED_PREF"
    private val IS_FIRST_START_KEY = "IS_FIRST_START_KEY"
    private val NUMBER_OF_COINS_KEY = "NUMBER_OF_COINS_KEY"
    private val CURRENT_THEM_KEY = "CURRENT_THEM_KEY"
    private val SOUNDS_KEY = "SOUNDS_KEY"
    private val CURRENT_LEVEL_KEY = "CURRENT_LEVEL_KEY"
    private val TYPE_NUMBERS_NAME_KEY = "TYPE_NUMBERS_NAME_KEY"
    private val TYPE_NUMBERS_FROM_KEY = "TYPE_NUMBERS_FROM_KEY"
    private val TYPE_NUMBERS_TO_KEY = "TYPE_NUMBERS_TO_KEY"
    private val NOTIFICATION_MINUTES_KEY = "NOTIFICATION_MINUTES_KEY"
    private val NOTIFICATION_HOUR_KEY = "NOTIFICATION_HOUR_KEY"
    private val NOTIFICATION_DAY_KEY = "NOTIFICATION_DAY_KEY"
    private val NOTIFICATION_MONTH_KEY = "NOTIFICATION_MONTH_KEY"
    private val NOTIFICATION_YEAR_KEY = "NOTIFICATION_YEAR_KEY"
    private val NOTIFICATION_STATE = "NOTIFICATION_STATE"
    private val MY_LOG = "MY_LOG"
    private val sharedPreferences = context.getSharedPreferences(NAME_SHARED_PREF, Context.MODE_PRIVATE)
    private val edit = sharedPreferences.edit()

    override fun isFirstStart(): Boolean {
        val result = sharedPreferences.getBoolean(IS_FIRST_START_KEY, true)
        if (result) edit.putBoolean(IS_FIRST_START_KEY, false).commit()

        Log.d(MY_LOG, result.toString())

        return result
    }

    override fun setNumberOfCoins(numberOfCoins: Int) {
        edit.putInt(NUMBER_OF_COINS_KEY, numberOfCoins).commit()
    }

    override fun getNumberOfCoins(): Int {
        return sharedPreferences.getInt(NUMBER_OF_COINS_KEY, -1)
    }

    override fun setCurrentTheme(currentTheme: String) {
        edit.putString(CURRENT_THEM_KEY, currentTheme).commit()
        when(currentTheme) {
            "STANDART_THEM" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "DARK_THEM" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    override fun getCurrentTheme(): String {
        return sharedPreferences.getString(CURRENT_THEM_KEY, "").toString()
    }

    override fun setSounds(sounds: Boolean) {
        edit.putBoolean(SOUNDS_KEY, sounds).commit()
    }

    override fun getSounds(): Boolean {
        return sharedPreferences.getBoolean(SOUNDS_KEY, false)
    }

    override fun setPurchaseStatusForTheme(themeName: String) {
        edit.putBoolean(themeName, true)
    }

    override fun getPurchaseStatusForTheme(themeName: String): Boolean {
        return sharedPreferences.getBoolean(themeName, false)
    }

    override fun setNotificationTime(notificationTime: NotificationTimeDataStorage) {
        edit.apply {
            putInt(NOTIFICATION_MINUTES_KEY, notificationTime.minute)
            putInt(NOTIFICATION_HOUR_KEY, notificationTime.hour)
            putInt(NOTIFICATION_DAY_KEY, notificationTime.dayOfMonth)
            putInt(NOTIFICATION_MONTH_KEY, notificationTime.month)
            putInt(NOTIFICATION_YEAR_KEY, notificationTime.year)
        }.commit()
    }

    override fun getNotificationTime(): NotificationTimeDataStorage {
        val minute = sharedPreferences.getInt(NOTIFICATION_MINUTES_KEY, 0)
        val hour = sharedPreferences.getInt(NOTIFICATION_HOUR_KEY, 0)
        val day = sharedPreferences.getInt(NOTIFICATION_DAY_KEY, 0)
        val month = sharedPreferences.getInt(NOTIFICATION_MONTH_KEY, 0)
        val year = sharedPreferences.getInt(NOTIFICATION_YEAR_KEY, 0)

        return NotificationTimeDataStorage(minute = minute, hour = hour, dayOfMonth = day, month = month, year = year)
    }

    override fun setNotificationState(flag: Boolean) {
        edit.putBoolean(NOTIFICATION_STATE, flag).commit()
    }

    override fun getNotificationState(): Boolean {
        return sharedPreferences.getBoolean(NOTIFICATION_STATE, false)
    }

    override fun setLevel(levelName: String) {
        edit.putString(CURRENT_LEVEL_KEY, levelName).commit()
    }

    override fun getLevel(): String {
        return sharedPreferences.getString(CURRENT_LEVEL_KEY, "").toString()
    }

    override fun setPurchaseStatusForLevel(purchaseStatusForLevel: PurchaseStatusForLevelDataStorage) {
        edit.apply {
            putBoolean(purchaseStatusForLevel.nameLevel, purchaseStatusForLevel.status)
        }.commit()
    }

    override fun getPurchaseStatusForLevel(levelName: String): Boolean {
       return sharedPreferences.getBoolean(levelName, false)
    }

    override fun setTypeNumbers(typeNumbersData: TypeNumbersDataStorage) {
        edit.apply {
            putString(TYPE_NUMBERS_NAME_KEY, typeNumbersData.type)
            putInt(TYPE_NUMBERS_FROM_KEY, typeNumbersData.from)
            putInt(TYPE_NUMBERS_TO_KEY, typeNumbersData.to)
        }.commit()
    }

    override fun getTypeNumbers(): TypeNumbersDataStorage {
        val type = sharedPreferences.getString(TYPE_NUMBERS_NAME_KEY, "").toString()
        val from = sharedPreferences.getInt(TYPE_NUMBERS_FROM_KEY, 0)
        val to = sharedPreferences.getInt(TYPE_NUMBERS_TO_KEY, 0)

        return TypeNumbersDataStorage(type = type, from = from, to = to)
    }
}