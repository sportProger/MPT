package com.sportproger.mpt.di

import com.sportproger.data.repository.UserRepositoryImpl
import com.sportproger.data.storage.ExampleStorage
import com.sportproger.data.storage.UserStorage
import com.sportproger.data.storage.UserStorageImpl
import com.sportproger.data.storage.ExampleStorageImpl
import com.sportproger.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {
    single<UserStorage> {
        UserStorageImpl(context = get())
    }

    single<ExampleStorage> {
        ExampleStorageImpl(context = get())
    }

    single<UserRepository> {
        UserRepositoryImpl(userStorage = get(), exampleStorage = get())
    }
}