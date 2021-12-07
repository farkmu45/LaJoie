package com.tigro.lajoie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.tigro.lajoie.R
import com.tigro.lajoie.models.History

class HistoryAdapter(private val dataSet: List<History>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container: ConstraintLayout = view.findViewById(R.id.history_container)
        val title: TextView = view.findViewById(R.id.history_title)
        val status: TextView = view.findViewById(R.id.history_status)
        val detail: TextView = view.findViewById(R.id.history_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val historyLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(historyLayout)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.apply {
            title.text = dataSet[position].title
            detail.text = dataSet[position].detail
            status.text = dataSet[position].status
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}