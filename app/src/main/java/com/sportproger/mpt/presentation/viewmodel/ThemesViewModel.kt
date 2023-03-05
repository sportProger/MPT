package com.sportproger.mpt.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sportproger.domain.repository.UserRepository

class ThemesViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    private val numberOfCoins = MutableLiveData<Int>()
    private val purchaseStatusForTheme = MutableLiveData<Boolean>()

    fun numberOfCoinsLive(): LiveData<Int> {
        return numberOfCoins
    }

    fun purchaseStatusForThemeLive(): LiveData<Boolean> {
        return purchaseStatusForTheme
    }

    fun getNumberOfCoins() {
        numberOfCoins.value = userRepository.getNumberOfCoins()
    }

    fun setNumberOfCoins(numberOfCoins: Int) {
        userRepository.setNumberOfCoins(numberOfCoins)
    }

    fun setCurrentTheme(theme: String) {
        userRepository.setCurrentTheme(theme)
    }

    fun setPurchaseStatusForTheme(themeName: String) {
        userRepository.setPurchaseStatusForTheme(themeName)
    }

    fun getPurchaseStatusForTheme(themeName: String) {
        purchaseStatusForTheme.value = userRepository.getPurchaseStatusForLevel(themeName)
    }
}