package com.sportproger.mpt.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.sportproger.domain.module.PurchaseStatusForLevelData
import com.sportproger.domain.repository.UserRepository

class StartViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    fun setNumberOfCoins(numberOfCoins: Int) {
        userRepository.setNumberOfCoins(numberOfCoins)
    }

    fun setCurrentTheme(currentTheme: String) {
        userRepository.setCurrentTheme(currentTheme)
    }

    fun setLevel(level: String) {
        userRepository.setLevel(level)
    }
}