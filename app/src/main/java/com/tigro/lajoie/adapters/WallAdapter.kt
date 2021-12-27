package com.tigro.lajoie.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tigro.lajoie.databinding.WallItemBinding
import com.tigro.lajoie.models.Wall
import com.tigro.lajoie.screens.wall.WallFragmentDirections

class WallAdapter() :
    ListAdapter<Wall, WallAdapter.WallViewHolder>(DiffCalback) {
    class WallViewHolder(var binding: WallItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(wall: Wall) {
            binding.wall = wall
            binding.executePendingBindings()
        }
    }

    companion object DiffCalback : DiffUtil.ItemCallback<Wall>() {
        override fun areItemsTheSame(oldItem: Wall, newItem: Wall): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Wall, newItem: Wall): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallViewHolder {
        return WallViewHolder(
            WallItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WallViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.binding.cardWall.setOnClickListener {
            val direction =
                WallFragmentDirections.actionWallFragmentToWallDetailFragment(position)
            Log.d("POSITION", position.toString())
            it.findNavController().navigate(direction)
        }
    }
}
