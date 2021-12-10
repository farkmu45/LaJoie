package com.tigro.lajoie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tigro.lajoie.R
import com.tigro.lajoie.models.Comment

class CommentAdapter(private val dataSet: List<Comment>) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val username: TextView = view.findViewById(R.id.comment_username)
        val comment: TextView = view.findViewById(R.id.comment_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val commentLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return CommentViewHolder(commentLayout)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.apply {
            username.text = dataSet[position].username
            comment.text = dataSet[position].comment
        }
    }

    override fun getItemCount(): Int = dataSet.size

}