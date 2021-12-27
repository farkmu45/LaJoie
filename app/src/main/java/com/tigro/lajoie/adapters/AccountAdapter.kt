package com.tigro.lajoie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tigro.lajoie.R
import com.tigro.lajoie.models.AccountScreen

class AccountAdapter(private val dataSet: List<AccountScreen>, private val logout: () -> Unit) :
    RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {
    class AccountViewHolder(view: View, logout: () -> Unit) : RecyclerView.ViewHolder(view) {
        val card: RelativeLayout = view.findViewById(R.id.card)
        val title: TextView = view.findViewById(R.id.mtrl_list_item_text)
        val subtitle: TextView = view.findViewById(R.id.mtrl_list_item_secondary_text)
        val icon: ImageView = view.findViewById(R.id.mtrl_list_item_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val accountLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.material_list_item_two_line, parent, false)
        return AccountViewHolder(accountLayout, logout)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.apply {
            title.text = dataSet[position].title
            subtitle.text = dataSet[position].subtitle
            icon.setImageResource(dataSet[position].icon)

            card.setOnClickListener {

                when (dataSet[position].icon) {
                    R.drawable.ic_logout -> {
                        logout()
                    }
                    R.drawable.ic_knowledge -> {
                        Toast.makeText(it.context, "Info", Toast.LENGTH_SHORT).show()
                    }
                    R.drawable.ic_verified -> {
                        it.findNavController()
                            .navigate(R.id.action_accountFragment_to_submissionFragment)
                    }
                    R.drawable.ic_person -> {
                        Toast.makeText(it.context, "Personal info", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = dataSet.size
}
