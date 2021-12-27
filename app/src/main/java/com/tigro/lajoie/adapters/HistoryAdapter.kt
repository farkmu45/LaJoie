package com.tigro.lajoie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tigro.lajoie.databinding.HistoryItemBinding
import com.tigro.lajoie.models.History

class HistoryAdapter : ListAdapter<History, HistoryAdapter.HistoryViewHolder>(DiffCallback) {

    class HistoryViewHolder(private var binding: HistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) {
            binding.history = history
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem.detail == newItem.detail
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            HistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}