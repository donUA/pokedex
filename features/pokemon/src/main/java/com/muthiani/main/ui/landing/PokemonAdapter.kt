package com.muthiani.main.ui.landing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.muthiani.api.domain.local.entities.Pokemon
import com.muthiani.main.databinding.ItemPokemonViewBinding

class PokemonAdapter(
    val openInfo:  ((name: String) -> Unit)

): PagingDataAdapter<Pokemon, PokemonAdapter.PokemonViewHolder>(DIFF_UTIL) {


    inner class PokemonViewHolder(private val binding: ItemPokemonViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemon: Pokemon) {
            binding.apply {
                pokemonName.text = pokemon.name
                root.setOnClickListener {
                    openInfo.invoke(pokemon.name.orEmpty())
                }
            }
        }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {

       return PokemonViewHolder(ItemPokemonViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }
}