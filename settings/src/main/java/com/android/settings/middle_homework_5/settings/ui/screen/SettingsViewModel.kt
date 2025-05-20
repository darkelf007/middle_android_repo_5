package com.android.settings.middle_homework_5.settings.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.settings.middle_homework_5.settings.data.data_store.SettingContainer
import com.android.settings.middle_homework_5.settings.ui.contract.SettingsRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val dataStoreService: SettingsRepository
) : ViewModel() {

    fun saveSetting(periodic: Long, delayed: Long) {
        viewModelScope.launch {
            dataStoreService.saveSetting(periodic = periodic, delayed = delayed)
        }
    }

    fun getCurrentSetting(): SettingContainer {
        return dataStoreService.settingData.value
    }
}