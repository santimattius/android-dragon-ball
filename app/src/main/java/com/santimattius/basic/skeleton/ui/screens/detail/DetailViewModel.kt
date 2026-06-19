package com.santimattius.basic.skeleton.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.basic.skeleton.core.data.Character
import com.santimattius.basic.skeleton.core.data.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class DetailViewModel(
    private val repository: CharacterRepository,
    @InjectedParam private val characterId: String
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state: StateFlow<DetailUiState> = _state.onStart {
        fetch()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DetailUiState(isLoading = true)
    )

    fun fetch() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                val character = repository.findCharacterById(characterId.toLong()).getOrThrow()
                _state.update { it.copy(isLoading = false, character = character) }
            } catch (ex: Throwable) {
                _state.update { it.copy(isLoading = false, hasError = true) }
            }
        }
    }
}

data class DetailUiState(
    val isLoading: Boolean = false,
    val character: Character? = null,
    val hasError: Boolean = false,
)
