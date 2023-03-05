package com.sportproger.mpt.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.sportproger.domain.module.NotificationTime
import com.sportproger.domain.repository.UserRepository

class TimeViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    fun setNotificationTime(notificationTime: NotificationTime) { userRepository.setNotificationTime(notificationTime) }
}