package com.muthiani.main.ui.pokemonInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.muthiani.api.domain.local.models.PokemonInfo
import com.muthiani.main.R
import com.muthiani.main.databinding.FragmentPokemonInfoBinding


import com.muthiani.main.extensions.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class PokemonInfoFragment : Fragment(R.layout.fragment_pokemon_info) {

    private val binding by viewBinding(FragmentPokemonInfoBinding::bind)
    private val viewModel: PokemonInfoViewModel by viewModel()
    private val args: PokemonInfoFragmentArgs by navArgs()

    private var materialDialog: MaterialAlertDialogBuilder? = null

    private val statsAdapter by lazy {
        StatsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPokemonInfo(args.pokemonName)

        setUpList()

        setUpObservers()

        setOnClickListeners()

    }

    private fun setOnClickListeners() {
        binding.closePage.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpList() {
        binding.statsList.apply {
            adapter = statsAdapter
        }
    }

    private fun setUpObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PokemonInfoViewModel.PokeMonInfoUiState.Loading -> {
                    renderLoading(state.loading)
                }

                is PokemonInfoViewModel.PokeMonInfoUiState.Content -> {
                    renderContent(state.pokemonInfo)
                }

                is PokemonInfoViewModel.PokeMonInfoUiState.Error -> {
                    renderError(state.message)
                }
            }
        }
    }

    private fun renderLoading(loading: Boolean) {
        binding.apply {
            progressBar.isVisible = loading
        }
    }

    private fun renderError(message: String) {
        renderLoading(loading = false)

        materialDialog = MaterialAlertDialogBuilder(
            requireContext()
        )
        materialDialog?.apply {
            setTitle("Error")
            setMessage(message)
            setPositiveButton("Okay") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
        }
        materialDialog?.show()
    }

    private fun renderContent(pokemonInfo: PokemonInfo) {
        statsAdapter.apply {
            Glide
                .with(requireContext())
                .load(pokemonInfo.imageUrl)
                .fitCenter()
                .into(binding.pokemonImage)
            binding.statsTitle.isVisible = pokemonInfo.statList.isNotEmpty()
            binding.pokemonName.text = pokemonInfo.pokemonName
            renderLoading(loading = false)
            binding.statsTotal.text = getString(R.string.stats_total, pokemonInfo.totalStats.toString())
            setTotal(pokemonInfo.totalStats)
            submitList(pokemonInfo.statList)
        }
    }
}