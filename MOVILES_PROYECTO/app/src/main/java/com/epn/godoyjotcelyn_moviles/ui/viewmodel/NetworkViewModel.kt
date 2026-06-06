package com.epn.godoyjotcelyn.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epn.godoyjotcelyn.network.Post
import com.epn.godoyjotcelyn.network.NetworkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class NetworkUiState {
    object Idle : NetworkUiState()
    object Loading : NetworkUiState()
    data class Success(val post: Post) : NetworkUiState()
    data class Updated(val post: Post) : NetworkUiState()
    data class Error(val message: String) : NetworkUiState()
}

class NetworkViewModel : ViewModel() {

    private val repository = NetworkRepository()

    private val _uiState = MutableStateFlow<NetworkUiState>(NetworkUiState.Idle)
    val uiState: StateFlow<NetworkUiState> = _uiState

    fun getPost(id: Int) {
        viewModelScope.launch {
            _uiState.value = NetworkUiState.Loading
            try {
                val post = repository.getPost(id)
                _uiState.value = NetworkUiState.Success(post)
            } catch (e: Exception) {
                _uiState.value = NetworkUiState.Error("Error de Red: ${e.localizedMessage}")
            }
        }
    }

    fun updatePost(id: Int, title: String, body: String) {
        viewModelScope.launch {
            _uiState.value = NetworkUiState.Loading
            try {
                val updated = repository.updatePost(
                    id = id,
                    post = Post(id = id, userId = 1, title = title, body = body)
                )
                _uiState.value = NetworkUiState.Updated(updated)
            } catch (e: Exception) {
                _uiState.value = NetworkUiState.Error("Error al actualizar: ${e.localizedMessage}")
            }
        }
    }
}