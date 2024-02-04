package com.example.monodiversion.view

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.NumberPicker
import android.widget.SeekBar
import android.widget.Space
import androidx.activity.viewModels
import com.example.monodiversion.R
import com.example.monodiversion.databinding.ActivityCombinationBinding
import com.example.monodiversion.helper.GameType
import com.example.monodiversion.view.fragment.BottomMenuFragment
import com.example.monodiversion.view.fragment.HeaderFragment
import com.example.monodiversion.viewModel.ScoreViewModel
import com.example.monodiversion.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CombinationActivity : GameActivity() {

    private val userViewModel: UserViewModel by viewModels<UserViewModel>()
    private val scoreViewModel: ScoreViewModel by viewModels<ScoreViewModel>()
    private lateinit var binding: ActivityCombinationBinding
    override fun inflateLayout() {
        val grid = binding.glContainer

        scoreViewModel.gameLevel.observe(this) { level ->
            grid.removeAllViews()
            when (level) {
                0 -> {
                    addNumberPickerToGridLayout(grid, 0, 2)
                    addSpaceToGridLayout(grid, 0, 0)
                    addSpaceToGridLayout(grid, 0, 1)
                    addSpaceToGridLayout(grid, 0, 3)
                    addSpaceToGridLayout(grid, 0, 4)
                }

                1, 2, 3 -> {
                    addSpaceToGridLayout(grid,0,0)
                    addNumberPickerToGridLayout(grid, 0, 1)
                    addNumberPickerToGridLayout(grid, 0, 2)
                    addNumberPickerToGridLayout(grid, 0, 3)
                    addSpaceToGridLayout(grid, 0, 4)
                }

                4 -> {
                    addNumberPickerToGridLayout(grid, 0, 0)
                    addNumberPickerToGridLayout(grid, 0, 1)
                    addNumberPickerToGridLayout(grid, 0, 2)
                    addNumberPickerToGridLayout(grid, 0, 3)
                    addNumberPickerToGridLayout(grid, 0, 4)
                }
            }
        }
    }

    private fun addNumberPickerToGridLayout(grid: GridLayout, row: Int, column: Int) {
        val numberPicker = NumberPicker(this)
        numberPicker.minValue = 0
        numberPicker.maxValue = 10

        val layoutParams = GridLayout.LayoutParams()
        layoutParams.rowSpec = GridLayout.spec(row)
        layoutParams.columnSpec = GridLayout.spec(column,1f)
        layoutParams.setGravity(Gravity.FILL)
        grid.addView(numberPicker, layoutParams)
    }

    private fun addSpaceToGridLayout(grid: GridLayout, row: Int, column: Int) {
        val space = Space(this)

        val layoutParams = GridLayout.LayoutParams()
        layoutParams.rowSpec = GridLayout.spec(row)
        layoutParams.columnSpec = GridLayout.spec(column,1f)
        layoutParams.setGravity(Gravity.FILL)

        grid.addView(space, layoutParams)
    }


    override fun startGame() {
        TODO("Not yet implemented")
    }

    override fun pauseGame() {
        TODO("Not yet implemented")
    }

    override fun endGame() {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCombinationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sbLevel.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                scoreViewModel.updateGameLevel(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        if (intent.hasExtra("id") && intent.getLongExtra("id", 0L) != 0L) {
            val id = intent.getLongExtra("id", 0L)
            userViewModel.updateById(id)
        }

        userViewModel.setGameType(GameType.COMBINATION)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.flHeaderContainer, HeaderFragment.newInstance())
                .add(R.id.flBottomContainer, BottomMenuFragment.newInstance())
                .setReorderingAllowed(true)
                .commit()
        }
        inflateLayout()
    }

}