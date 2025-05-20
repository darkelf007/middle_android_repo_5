package com.android.settings.middle_homework_5.settings.di

import com.android.settings.middle_homework_5.settings.data.data_store.SettingsRepositoryImpl
import com.android.settings.middle_homework_5.settings.ui.contract.SettingsRepository
import com.android.settings.middle_homework_5.settings.ui.screen.SettingsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {
    single<SettingsRepository> { SettingsRepositoryImpl(androidApplication()) }
    viewModelOf(::SettingsViewModel)
}
