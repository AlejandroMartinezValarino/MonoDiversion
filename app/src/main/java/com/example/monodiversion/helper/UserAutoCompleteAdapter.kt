package com.example.monodiversion.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.monodiversion.R
import com.example.monodiversion.model.User

class UserAutoCompleteAdapter(context: Context, userList: List<User>) :
    ArrayAdapter<User>(context, R.layout.user_dropdown, userList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder: ViewHolder
        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.user_dropdown, parent, false)
            holder = ViewHolder()
            holder.userNameTextView = view.findViewById(R.id.text1)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val user = getItem(position)
        holder.userNameTextView.text = user?.name

        return view!!
    }

    internal class ViewHolder {
        lateinit var userNameTextView: TextView
    }
}