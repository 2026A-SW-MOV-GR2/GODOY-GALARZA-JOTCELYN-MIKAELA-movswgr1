package com.epn.godoyjotcelyn_moviles.ui.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.epn.godoyjotcelyn_moviles.storage.StorageManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

enum class StorageType {
    SHARED_PREFERENCES,
    DATA_STORE,
    ENCRYPTED_SHARED_PREFERENCES
}

sealed class StorageUiState {
    object Idle : StorageUiState()
    data class Found(val value: String) : StorageUiState()
    object NotFound : StorageUiState()
    data class Saved(val mechanism: String) : StorageUiState()
}

class StorageViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    private val _uiState = MutableStateFlow<StorageUiState>(StorageUiState.Idle)
    val uiState: StateFlow<StorageUiState> = _uiState

    fun guardar(key: String, value: String, type: StorageType) {
        viewModelScope.launch {
            when (type) {
                StorageType.SHARED_PREFERENCES -> {
                    StorageManager.saveSharedPrefs(context, key, value)
                    _uiState.value = StorageUiState.Saved("SharedPreferences")
                }
                StorageType.DATA_STORE -> {
                    StorageManager.saveDataStore(context, key, value)
                    _uiState.value = StorageUiState.Saved("DataStore")
                }
                StorageType.ENCRYPTED_SHARED_PREFERENCES -> {
                    StorageManager.saveEncrypted(context, key, value)
                    _uiState.value = StorageUiState.Saved("EncryptedSharedPreferences")
                }
            }
        }
    }

    fun recuperar(key: String, type: StorageType) {
        viewModelScope.launch {
            val result = when (type) {
                StorageType.SHARED_PREFERENCES ->
                    StorageManager.getSharedPrefs(context, key)
                StorageType.DATA_STORE ->
                    StorageManager.getDataStore(context, key)
                StorageType.ENCRYPTED_SHARED_PREFERENCES ->
                    StorageManager.getEncrypted(context, key)
            }
            _uiState.value = if (result != null) {
                StorageUiState.Found(result)
            } else {
                StorageUiState.NotFound
            }
        }
    }
}