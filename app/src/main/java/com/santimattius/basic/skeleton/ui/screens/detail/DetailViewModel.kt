package com.santimattius.basic.skeleton.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.santimattius.basic.skeleton.core.data.Character
import com.santimattius.basic.skeleton.core.data.CharacterRepository
import com.santimattius.basic.skeleton.ui.navigation.Detail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DetailViewModel(
    private val repository: CharacterRepository,
    private val characterId: String
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

    fun toggleFavorite() {

    }

    class Factory(
        private val key: Detail,
    ) : ViewModelProvider.Factory, KoinComponent {
        private val repository: CharacterRepository by inject()

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailViewModel(repository, key.id) as T
        }
    }
}

data class DetailUiState(
    val isLoading: Boolean = false,
    val character: Character? = null,
    val hasError: Boolean = false,
)
