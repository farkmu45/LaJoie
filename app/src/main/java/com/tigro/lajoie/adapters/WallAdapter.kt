package com.tigro.lajoie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tigro.lajoie.R
import com.tigro.lajoie.models.Wall

class WallAdapter(private val dataset: List<Wall>?) :
    RecyclerView.Adapter<WallAdapter.WallViewHolder>() {
    class WallViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val detail: TextView = view.findViewById(R.id.detail)
        val username: TextView = view.findViewById(R.id.username)
        val time: TextView = view.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallViewHolder {
        val wallLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.wall_item, parent, false)
        return WallViewHolder(wallLayout)
    }

    override fun onBindViewHolder(holder: WallViewHolder, position: Int) {
        val item = dataset?.get(position)

        holder.apply {
            title.text = item?.title
            detail.text = item?.detail
            time.text = item?.createdAt
            username.text = item?.username
        }

    }

    override fun getItemCount(): Int = dataset?.size!!

}