package com.example.monodiversion.helper

import android.animation.ObjectAnimator
import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.monodiversion.R
import com.example.monodiversion.model.Score
import com.example.monodiversion.model.User

class ScoreAdapter(private val context: Context, private var scoreList:List<Score>,private val userScore:User) :
    RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreAdapter.ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.score_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreAdapter.ViewHolder, position: Int) {
        val score = scoreList[holder.adapterPosition]

        holder.tvName.text = userScore.name
        holder.tvScore.text = score.points.toString()
        holder.tvGType.text = score.gameType.name
        if (!holder.toggleButton.isChecked){
            holder.tvScore.visibility = View.GONE
            holder.tvGType.visibility = View.GONE
        }

        holder.toggleButton.setOnCheckedChangeListener { _, flag ->
            val visibility = if (flag) View.VISIBLE else View.GONE
            val alpha = if (flag) 1f else 0f

            ObjectAnimator.ofFloat(holder.tvScore, "alpha", alpha).apply {
                duration = 1000
                start()
            }
            ObjectAnimator.ofFloat(holder.tvGType, "alpha", alpha).apply {
                duration = 1000
                start()
            }
            holder.tvScore.visibility = visibility
            holder.tvGType.visibility = visibility
        }
    }

    override fun getItemCount(): Int = scoreList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvScoreName)
        val tvScore: TextView = view.findViewById(R.id.tvScore)
        val tvGType: TextView = view.findViewById(R.id.tvGType)
        val toggleButton:ToggleButton = view.findViewById(R.id.tbVisibility)
    }
}