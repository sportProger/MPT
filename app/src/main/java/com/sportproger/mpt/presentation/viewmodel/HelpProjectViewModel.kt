package com.sportproger.mpt.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sportproger.domain.repository.UserRepository

class HelpProjectViewModel(
    private val userRepository: UserRepository
): ViewModel() {
    private val numberOfCoins = MutableLiveData<Int>()

    fun numberOfCoinsLive(): LiveData<Int> {
        return numberOfCoins
    }

    fun getNumberOfCoins() {
        numberOfCoins.value = userRepository.getNumberOfCoins()
    }
}