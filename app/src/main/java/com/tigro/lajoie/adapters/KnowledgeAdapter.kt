package com.tigro.lajoie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tigro.lajoie.databinding.KnowledgeItemBinding
import com.tigro.lajoie.models.Knowledge
import com.tigro.lajoie.screens.knowledge.KnowledgeFragmentDirections

class KnowledgeAdapter :
    ListAdapter<Knowledge, KnowledgeAdapter.KnowledgeViewHolder>(DiffCallback) {

    class KnowledgeViewHolder(var binding: KnowledgeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(knowledge: Knowledge) {
            binding.knowledge = knowledge
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Knowledge>() {
        override fun areItemsTheSame(oldItem: Knowledge, newItem: Knowledge): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Knowledge, newItem: Knowledge): Boolean {
            return oldItem.name == oldItem.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnowledgeViewHolder {
        return KnowledgeViewHolder(
            KnowledgeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: KnowledgeViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.binding.container.setOnClickListener {
            val direction =
                KnowledgeFragmentDirections.actionKnowledgeFragmentToKnowledgeCategory(
                    getItem(position).id, getItem(position).name
                )
            it.findNavController().navigate(direction)
        }
    }
}