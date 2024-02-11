package com.example.monodiversion.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.monodiversion.R
import com.example.monodiversion.databinding.ActivityScoreBinding
import com.example.monodiversion.helper.GameType
import com.example.monodiversion.helper.ScoreAdapter
import com.example.monodiversion.helper.UserAutoCompleteAdapter
import com.example.monodiversion.view.fragment.BottomMenuFragment
import com.example.monodiversion.viewModel.ScoreViewModel
import com.example.monodiversion.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoreActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private val scoreViewModel: ScoreViewModel by viewModels()
    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.hasExtra("id") && intent.getLongExtra("id", 0L) != 0L) {
            val id = intent.getLongExtra("id", 0L)
            userViewModel.updateById(id)
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.flBottomContainer, BottomMenuFragment.newInstance())
                .setReorderingAllowed(true)
                .commit()
        }
        initViews()
    }

    //TODO change multiautocomplete with simple autocomplete and checkbox with radiobutton
    private fun initViews() {
        val container: RecyclerView = binding.rvScoreContainer
        var selectedArrangement: GameType = GameType.COMBINATION
        userViewModel.getUsers()

        userViewModel.users.observe(this) { users ->

            val adapter = UserAutoCompleteAdapter(this, users)
            binding.actvUsers.setAdapter(adapter)

            binding.actvUsers.setOnItemClickListener { _, _, position, _ ->
                val selectedUser = adapter.getItem(position)
                val userId = selectedUser?.id

                if (userId != null) {
                    binding.rgGameType.setOnCheckedChangeListener { group, checkedId ->
                        val rbArrangement = group.findViewById<RadioButton>(checkedId)
                        selectedArrangement = when (rbArrangement.text) {
                            "Agility" -> GameType.AGILITY
                            "Memory" -> GameType.MEMORY
                            "Combination" -> GameType.COMBINATION
                            else -> GameType.COMBINATION
                        }
                        scoreViewModel.getScoresByIdAndType(userId, selectedArrangement)
                    }
                    scoreViewModel.getUserById(userId)
                }
                scoreViewModel.scores.observe(this) { scores ->
                    val scoreAdapter =
                        scoreViewModel.userScore.value?.let { ScoreAdapter(this, scores, it) }
                    container.adapter = scoreAdapter
                }

                scoreViewModel.isLoading.observe(this) {
                    binding.pbHorizontal.isVisible = it
                }
            }
        }
    }
}