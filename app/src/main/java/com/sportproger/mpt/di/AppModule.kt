package com.sportproger.mpt.di

import com.sportproger.mpt.presentation.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MainViewModel(
            userRepository = get()
        )
    }

    viewModel {
        StartViewModel(
            userRepository = get()
        )
    }

    viewModel {
        SettingsViewModel(
            userRepository = get()
        )
    }

    viewModel {
        LevelsViewModel(
            userRepository = get()
        )
    }

    viewModel {
        LevelViewModel(
            userRepository = get()
        )
    }

    viewModel {
        GamesViewModel (
            userRepository = get()
        )
    }

    viewModel {
        GameViewModel(
            userRepository = get()
        )
    }

    viewModel {
        ThemesViewModel(
            userRepository = get()
        )
    }

    viewModel {
        HelpProjectViewModel(
            userRepository = get()
        )
    }

    viewModel {
        AboutViewModel(
            userRepository = get()
        )
    }

    viewModel {
        TimeViewModel(
            userRepository = get()
        )
    }
}