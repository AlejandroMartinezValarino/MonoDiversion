package com.example.monodiversion.helper.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.monodiversion.R
import com.example.monodiversion.helper.BoxArrangement
import com.example.monodiversion.model.User
import com.example.monodiversion.view.UsersActivity
import com.example.monodiversion.viewModel.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserAdapter(private val context: Context, private var userList: List<User>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    val userViewModel = ViewModelProvider(context as UsersActivity)[UserViewModel::class.java]
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[holder.adapterPosition]
        val flag = user.flag

        holder.tvNameItem.text = user.name
        holder.tvCountryItem.text = user.country.toString()

        if (flag != null) {
            holder.llFlagItem.removeAllViews()
            holder.llFlagItem.orientation =
                if (flag.orientation == BoxArrangement.HORIZONTAL) LinearLayout.HORIZONTAL else LinearLayout.VERTICAL

            for (color in flag.colors) {
                val colorView = View(context)
                colorView.setBackgroundColor(color)

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )

                colorView.layoutParams = params

                holder.llFlagItem.addView(colorView)
            }
            holder.llFlagItem.visibility = View.VISIBLE
        } else {
            holder.llFlagItem.visibility = View.GONE
        }

        holder.faButDelete.setOnClickListener {
            val userToDelete = getItem(holder.layoutPosition)
            val dialog = AlertDialog.Builder(context)
                .setTitle("Delete ${userToDelete.name}")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes") { dialog, which ->
                    userViewModel.deleteUser(userToDelete)
                    val intent = Intent(context,UsersActivity::class.java)
                    context.startActivity(intent)
                    Toast.makeText(
                        context,
                        "You deleted ${userToDelete.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                }.setNegativeButton("No"){_,_->
                }
                .create()
            dialog.show()
        }

        holder.faButChoose.setOnClickListener {
            val userToChoose = getItem(holder.layoutPosition)
            Toast.makeText(context,"You chose ${userToChoose.name}",Toast.LENGTH_SHORT)
                .show()
            userViewModel.updateById(userToChoose.id)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val llRowItem: LinearLayout = view.findViewById(R.id.llRowItem)
        val llTextItem: LinearLayout = view.findViewById(R.id.llTextItem)
        val llFlagItem: LinearLayout = view.findViewById(R.id.llFlagItem)
        val tvNameItem: TextView = view.findViewById(R.id.tvNameItem)
        val tvCountryItem: TextView = view.findViewById(R.id.tvCountryItem)
        val faButDelete: FloatingActionButton = view.findViewById(R.id.faButDelete)
        val faButChoose: FloatingActionButton = view.findViewById(R.id.faButChoose)
    }

    fun getItem(position: Int): User {
        return userList[position]
    }
}