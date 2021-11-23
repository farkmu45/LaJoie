package com.tigro.lajoie.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.tigro.lajoie.R
import com.tigro.lajoie.models.Knowledge

class KnowledgeAdapter(private val dataSet: List<Knowledge>) :
    RecyclerView.Adapter<KnowledgeAdapter.KnowledgeViewHolder>() {
    class KnowledgeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val card: MaterialCardView = view.findViewById(R.id.container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnowledgeViewHolder {
        val knowledgeLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.knowledge_item, parent, false)
        return KnowledgeViewHolder(knowledgeLayout)
    }

    override fun onBindViewHolder(holder: KnowledgeViewHolder, position: Int) {
        val data = dataSet[position]
        holder.title.text = data.name
//        holder.card.setOnClickListener {
//            TODO("NOT IMPLEMENTED YET")
//        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}