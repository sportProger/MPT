package com.sportproger.mpt.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sportproger.domain.module.NotificationTime
import com.sportproger.domain.module.TypeNumbersData
import com.sportproger.domain.repository.UserRepository

class SettingsViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    private val numberOfCoins = MutableLiveData<Int>()
    private val notificationTime = MutableLiveData<NotificationTime>()
    private val sounds = MutableLiveData<Boolean>()
    private val notificationState = MutableLiveData<Boolean>()

    fun numberOfCoinsLive(): LiveData<Int> { return numberOfCoins }

    fun notificationTimeLive(): LiveData<NotificationTime> { return notificationTime }

    fun soundsLive(): LiveData<Boolean> { return sounds }

    fun notificationStateLive(): LiveData<Boolean> { return notificationState }

    fun getNumberOfCoins() { numberOfCoins.value = userRepository.getNumberOfCoins() }

    fun getNotificationTime() { notificationTime.value = userRepository.getNotificationTime() }

    fun getSounds() { sounds.value = userRepository.getSounds() }

    fun setTypeNumbers(typeNumbers: TypeNumbersData) { userRepository.setTypeNumbers(typeNumbersData = typeNumbers) }

    fun setSounds(sounds: Boolean) { userRepository.setSounds(sounds = sounds) }

    fun setNotificationTime(notificationTime: NotificationTime) { userRepository.setNotificationTime(notificationTime) }

    fun setNotificationSate(flag: Boolean) {
        userRepository.setNotificationState(flag)
    }

    fun getNotificationState() {
        notificationState.value = userRepository.getNotificationState()
    }
}