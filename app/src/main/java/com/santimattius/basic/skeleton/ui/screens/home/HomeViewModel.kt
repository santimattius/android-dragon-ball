package com.santimattius.basic.skeleton.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.basic.skeleton.core.data.Character
import com.santimattius.basic.skeleton.core.data.CharacterRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val characters: List<Character> = emptyList(),
    val hasError: Boolean = false,
)

@KoinViewModel
class HomeViewModel(
    private val repository: CharacterRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.onStart {
        fetch()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _state.update { it.copy(hasError = true) }
    }

    private fun fetch() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch(exceptionHandler) {
            repository.getCharacters().onFailure { exception ->
                exception.printStackTrace()
                _state.update { it.copy(isLoading = false, hasError = true) }
            }.onSuccess { characters ->
                _state.update { it.copy(isLoading = false, characters = characters) }
            }
        }
    }
}