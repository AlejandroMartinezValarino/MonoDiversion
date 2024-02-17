package com.example.monodiversion.helper.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.monodiversion.R
import com.example.monodiversion.model.User

class UserSearchAdapter (private val context: Context, private var userList: List<User>) :
    RecyclerView.Adapter<UserSearchAdapter.ViewHolder>(), Filterable {
    private var filteredList: List<User> = listOf()
    init {
        filteredList = userList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.user_cursor_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = filteredList[position].name
        Log.d("+++", "onBindViewHolder: ${filteredList.toList()}")
    }

    override fun getItemCount(): Int = filteredList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvName = view.findViewById<TextView>(R.id.tvUserName)
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filteredList = if (charSearch.isEmpty()) {
                    userList
                } else {
                    val resultList = userList.filter { user ->
                        user.name.lowercase().contains(constraint.toString().lowercase())
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results?.values != null) {
                    filteredList = results.values as List<User>
                    Log.d("+++", "publishResults: ${filteredList.toList()}")
                    notifyDataSetChanged()
                }
            }
        }
    }
}