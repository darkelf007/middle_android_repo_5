package com.android.settings.middle_homework_5.settings.ui.contract

import com.android.settings.middle_homework_5.settings.data.data_store.SettingContainer
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {
    val settingData: StateFlow<SettingContainer>
    suspend fun saveSetting(periodic: Long, delayed: Long)
    suspend fun readSetting()
}