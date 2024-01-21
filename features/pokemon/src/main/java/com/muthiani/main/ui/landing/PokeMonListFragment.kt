package com.muthiani.main.ui.landing

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.muthiani.api.domain.local.entities.Pokemon
import com.muthiani.main.extensions.viewBinding
import com.muthiani.main.extensions.observeEvent
import com.muthiani.pokedex.R
import com.muthiani.pokedex.databinding.FragmentPokemonListBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class PokeMonListFragment : Fragment(R.layout.fragment_pokemon_list) {

    private val binding by viewBinding(FragmentPokemonListBinding::bind)
    private val viewModel: PokeMonViewModel by viewModel()

    private val pokemonAdapter by lazy {
        PokemonAdapter(
            openInfo = {name ->
                viewModel.navigateToInfo(name)
            }
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpList()

        setObservers()

    }

    private fun setUpList() {
        val header = PokemonLoadStateAdapter { pokemonAdapter.retry() }
        binding.pokemonList.adapter = pokemonAdapter.withLoadStateHeaderAndFooter(
            header = header,
            footer = PokemonLoadStateAdapter { pokemonAdapter.retry() }
        )

        lifecycleScope.launch {
            pokemonAdapter.loadStateFlow.collect { loadState ->
                // Show a retry header if there was an error refreshing, and items were previously
                // cached OR default to the default prepend state
                header.loadState = loadState.mediator
                    ?.refresh
                    ?.takeIf { it is LoadState.Error && pokemonAdapter.itemCount > 0 }
                    ?: loadState.prepend

                val isListEmpty = loadState.refresh is LoadState.NotLoading && pokemonAdapter.itemCount == 0
                // show empty list
                binding.emptyList.isVisible = isListEmpty
                // Only show the list if refresh succeeds, either from the the local db or the remote.
                binding.pokemonList.isVisible =  loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                binding.progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                binding.retryButton.isVisible = loadState.mediator?.refresh is LoadState.Error && pokemonAdapter.itemCount == 0
                // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        requireContext(),
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) {state ->
            when(state) {
                is PokeMonViewModel.PokeMonUiState.Content -> {renderContent(state.pokemon)}
                else -> {}
            }
        }

        viewModel.actions.observeEvent(viewLifecycleOwner) { event ->
            when (event) {
                is PokeMonViewModel.PokemonActions.Navigate -> {
                    findNavController().navigate(directions = event.directions)
                }
            }
        }
    }

    private fun renderContent(pokemon: PagingData<Pokemon>) {
        lifecycleScope.launch {
            pokemonAdapter.submitData(pokemon)
        }
    }
}