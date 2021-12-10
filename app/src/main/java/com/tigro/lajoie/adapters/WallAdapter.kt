package com.tigro.lajoie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.tigro.lajoie.R
import com.tigro.lajoie.models.Wall
import com.tigro.lajoie.screens.wall.WallFragmentDirections

class WallAdapter(private val dataSet: List<Wall>) :
    RecyclerView.Adapter<WallAdapter.WallViewHolder>() {
    class WallViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val detail: TextView = view.findViewById(R.id.detail)
        val username: TextView = view.findViewById(R.id.username)
        val time: TextView = view.findViewById(R.id.time)
        val card: MaterialCardView = view.findViewById(R.id.card_wall)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallViewHolder {
        val wallLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.wall_item, parent, false)
        return WallViewHolder(wallLayout)
    }

    override fun onBindViewHolder(holder: WallViewHolder, position: Int) {
        val item = dataSet[position]

        var text = dataSet[position].detail

        if (text.length > 80) {
            text = dataSet[position].detail.take(80) + "..."
        }

        holder.apply {
            title.text = item.title
            detail.text = text
            time.text = item.createdAt
            username.text = item.username

            card.setOnClickListener {
                val direction =
                    WallFragmentDirections.actionWallFragmentToWallDetailFragment(position)
                it.findNavController().navigate(direction)
            }
        }

    }

    override fun getItemCount(): Int = dataSet.size

}