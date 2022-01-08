package com.tigro.lajoie.adapters

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: KnowledgeCategoryViewHolder, position: Int) {
        val subtitle = dataSet[position].text
        var rendered = Html.fromHtml(subtitle, Html.FROM_HTML_MODE_COMPACT)


        if (rendered.length > 80) {
            rendered = rendered.take(80) as Spanned?
        }

        holder.title.text = dataSet[position].name
        holder.subtitle.text = rendered

        holder.card.setOnClickListener {
            it.findNavController().navigate(
                KnowledgeCategoryFragmentDirections.actionKnowledgeCategoryToKnowledgeDetailFragment(
                    position
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}