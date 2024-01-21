package com.muthiani.main.ui.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.muthiani.api.domain.PokemonRepository
import com.muthiani.api.domain.local.entities.Pokemon
import com.muthiani.main.extensions.Event
import com.muthiani.main.extensions.asEvent
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class PokeMonViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    fun navigateToInfo(name: String) {
        val direction =
            PokeMonListFragmentDirections.toFragmentInfoFragment(name)
        _actions.value = PokemonActions.Navigate(direction).asEvent()
    }

    private val _uiState: MutableLiveData<PokeMonUiState> =
        MutableLiveData<PokeMonUiState>()
    val uiState: LiveData<PokeMonUiState> = _uiState

    private var _actions = MutableLiveData<Event<PokemonActions>>()
    val actions: LiveData<Event<PokemonActions>> = _actions

    init {
        viewModelScope.launch {
            repository.getPokeMonResultsStream().cachedIn(viewModelScope).distinctUntilChanged()
                .collect {
                    _uiState.value = PokeMonUiState.Content(it)
                }
        }
    }

    sealed class PokeMonUiState {
        data class Content(val pokemon: PagingData<Pokemon>) : PokeMonUiState()
    }

    sealed class PokemonActions {
        data class Navigate(val directions: NavDirections) : PokemonActions()
    }
}