package com.tigro.lajoie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tigro.lajoie.R
import com.tigro.lajoie.models.KnowledgeDetail
import com.tigro.lajoie.screens.knowledge.KnowledgeCategoryFragmentDirections

class KnowledgeCategoryAdapter(private val dataSet: List<KnowledgeDetail>) :
    RecyclerView.Adapter<KnowledgeCategoryAdapter.KnowledgeCategoryViewHolder>() {

    class KnowledgeCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.mtrl_list_item_text)
        val subtitle: TextView = view.findViewById(R.id.mtrl_list_item_secondary_text)
        val card: RelativeLayout = view.findViewById(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnowledgeCategoryViewHolder {
        val knowledgeCategoryLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.material_list_item_two_line, parent, false)
        return KnowledgeCategoryViewHolder((knowledgeCategoryLayout))
    }

    override fun onBindViewHolder(holder: KnowledgeCategoryViewHolder, position: Int) {
        var subtitle = dataSet[position].text

        if (subtitle.length > 80) {
            subtitle = dataSet[position].text.take(80) + "..."
        }


        holder.title.text = dataSet[position].name
        holder.subtitle.text = subtitle

        holder.card.setOnClickListener {
            it.findNavController().navigate(KnowledgeCategoryFragmentDirections.actionKnowledgeCategoryToKnowledgeDetailFragment(position))
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}