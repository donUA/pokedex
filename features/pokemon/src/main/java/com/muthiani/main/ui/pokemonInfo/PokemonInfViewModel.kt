package com.muthiani.main.ui.pokemonInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muthiani.api.domain.PokemonRepository
import com.muthiani.api.domain.local.models.PokemonInfo
import com.muthiani.api.domain.mappers.toPokemonInfo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonInfoViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _uiState.value = PokeMonInfoUiState.Error(
            message = throwable.toString().getErrorMessage()
        )
    }

    private val _uiState: MutableLiveData<PokeMonInfoUiState> =
        MutableLiveData<PokeMonInfoUiState>()

    val uiState: LiveData<PokeMonInfoUiState> = _uiState


    fun getPokemonInfo(name: String) {
        viewModelScope.launch(exceptionHandler) {
            _uiState.value = PokeMonInfoUiState.Loading(loading = true)
            val pokemonInfoDto = withContext(Dispatchers.IO) {
                repository.getPokemonInfo(name)
            }
            _uiState.value = PokeMonInfoUiState.Content(
                pokemonInfo = pokemonInfoDto.toPokemonInfo()
            )
        }
    }

    sealed class PokeMonInfoUiState {
        data class Content(val pokemonInfo: PokemonInfo) : PokeMonInfoUiState()
        data class Loading(val loading: Boolean) : PokeMonInfoUiState()
        data class Error(val message: String) : PokeMonInfoUiState()

    }

    companion object {
        private fun String.getErrorMessage() = if (this.contains("Unable to resolve host")) {
            "Check internet connection and try again"
        } else this
    }
}