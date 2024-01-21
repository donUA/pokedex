package com.muthiani.main.ui.pokemonInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muthiani.api.domain.local.models.StatContent
import com.muthiani.pokedex.databinding.ItemStatsBinding

class StatsAdapter(
    private var total: Int? = null
) : ListAdapter<StatContent, StatsAdapter.StatContentViewHolder>(DIFF_UTIL) {
    inner class StatContentViewHolder(private val binding: ItemStatsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(stat: StatContent) {
            binding.apply {
                statName.text = stat.statName
                statValue.text = stat.statValue.toString()
                statsProgress.max = total ?: 0
                statsProgress.setProgress(stat.statValue ?: 0, true)
            }
        }
    }

    fun setTotal(total: Int?) {
        this.total = total
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<StatContent>() {
            override fun areItemsTheSame(oldItem: StatContent, newItem: StatContent): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StatContent, newItem: StatContent): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatContentViewHolder {
        return StatContentViewHolder(ItemStatsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StatContentViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(
                it
            )
        }
    }
}
