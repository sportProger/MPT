package com.sportproger.data.storage

import android.content.Context
import com.sportproger.data.storage.module.NotificationTimeDataStorage
import com.sportproger.data.storage.module.PurchaseStatusForLevelDataStorage
import com.sportproger.data.storage.module.TypeNumbersDataStorage

interface UserStorage {
    fun isFirstStart(): Boolean

    fun setNumberOfCoins(numberOfCoins: Int)
    fun getNumberOfCoins(): Int

    fun setCurrentTheme(currentTheme: String)
    fun getCurrentTheme(): String

    fun setSounds(sounds: Boolean)
    fun getSounds(): Boolean

    fun setPurchaseStatusForTheme(themeName: String)
    fun getPurchaseStatusForTheme(themeName: String): Boolean

    fun setNotificationTime(notificationTime: NotificationTimeDataStorage)
    fun getNotificationTime(): NotificationTimeDataStorage

    fun setNotificationState(flag: Boolean)
    fun getNotificationState(): Boolean

    fun setLevel(levelName: String)
    fun getLevel(): String

    fun setTypeNumbers(typeNumbersData: TypeNumbersDataStorage)
    fun getTypeNumbers(): TypeNumbersDataStorage

    fun setPurchaseStatusForLevel(purchaseStatusForLevel: PurchaseStatusForLevelDataStorage)
    fun getPurchaseStatusForLevel(levelName: String): Boolean
}