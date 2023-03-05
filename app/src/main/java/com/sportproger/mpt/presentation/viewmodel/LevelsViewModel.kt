package com.sportproger.mpt.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sportproger.domain.module.PurchaseStatusForLevelData
import com.sportproger.domain.repository.UserRepository

class LevelsViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    private val numberOfCoins = MutableLiveData<Int>()
    private val getPurchaseStatusForLevel = MutableLiveData<Boolean>()

    fun numberOfCoinsLive(): LiveData<Int> {
        return numberOfCoins
    }

    fun purchaseStatusForLevelLive(): LiveData<Boolean> {
        return getPurchaseStatusForLevel
    }


    fun setNumberOfCoins(numberOfCoins: Int) {
        userRepository.setNumberOfCoins(numberOfCoins)
    }

    fun getNumberOfCoins() {
        numberOfCoins.value = userRepository.getNumberOfCoins()
    }

    fun setPurchaseStatusForLevel(purchaseStatusForLevel: PurchaseStatusForLevelData) {
        userRepository.setPurchaseStatusForLevel(purchaseStatusForLevel)
    }

    fun setLevel(levelName: String) {
        userRepository.setLevel(levelName)
    }

    fun getPurchaseStatusForLevel(levelName: String) {
        getPurchaseStatusForLevel.value = userRepository.getPurchaseStatusForLevel(levelName)
    }

    fun getNumberOfCorrectAnswer(level: String): Int {
        return userRepository.getNumberOfCorrectAnswers(level)
    }

    fun getNumberOfWrongAnswer(level: String): Int {
        return userRepository.getNumberOfWrongAnswers(level)
    }
}