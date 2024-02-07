package com.example.monodiversion.view

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.NumberPicker
import android.widget.SeekBar
import android.widget.Space
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.monodiversion.R
import com.example.monodiversion.databinding.ActivityCombinationBinding
import com.example.monodiversion.helper.GameType
import com.example.monodiversion.helper.State
import com.example.monodiversion.view.fragment.BottomMenuFragment
import com.example.monodiversion.view.fragment.HeaderFragment
import com.example.monodiversion.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Random
import kotlin.math.abs

@AndroidEntryPoint
class CombinationActivity : GameActivity() {

    private val userViewModel: UserViewModel by viewModels<UserViewModel>()
    private lateinit var binding: ActivityCombinationBinding
    private var state: State = State.PAUSED
    private val numberPickers = mutableListOf<NumberPicker>()
    private var level: Int = 0
    private var tries: Int = 0
    private var time: Long = 0L
    private lateinit var timer: CountDownTimer
    private lateinit var tvTimer: TextView
    private lateinit var tvTries: TextView
    private lateinit var butVerify: Button
    private var marginError: Int = 0
    private var maxNumber: Int = 5
    private var resNumbers: IntArray = intArrayOf()

    override fun inflateLayout() {
        val grid = binding.glContainer
        val chrono = tvTimer
        chrono.text = "00:00"

        binding.sbLevel.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val row = 1
                level = progress
                grid.removeAllViews()
                when (level) {
                    0 -> {
                        maxNumber = 5
                        tries = 100
                        time = 0
                        marginError = 50
                        resNumbers = generateNumbers(1, 5)
                        tvTries.text = tries.toString()
                        zeroRow()
                        butRow()
                        addNumberPickerToGridLayout(grid, row, 2, maxNumber)
                        addSpaceToGridLayout(grid, row, 0)
                        addSpaceToGridLayout(grid, row, 1)
                        addSpaceToGridLayout(grid, row, 3)
                        addSpaceToGridLayout(grid, row, 4)
                    }

                    1 -> {
                        tries = 30
                        time = 0
                        marginError = 40
                        resNumbers = generateNumbers(3, 5)
                        tvTries.text = tries.toString()
                        zeroRow()
                        butRow()
                        middleLevel(grid, row, 5)
                    }

                    2 -> {
                        tries = 25
                        time = 0
                        marginError = 30
                        resNumbers = generateNumbers(3, 10)
                        tvTries.text = tries.toString()
                        zeroRow()
                        butRow()
                        middleLevel(grid, row, 10)
                    }

                    3 -> {
                        tries = 20
                        time = 90000L
                        marginError = 20
                        resNumbers = generateNumbers(3, 10)
                        tvTries.text = tries.toString()
                        tvTimer.text = String.format("%02d:%02d", time / 60000L, time % 60000L)
                        zeroRow()
                        butRow()
                        middleLevel(grid, row, 10)
                    }

                    4 -> {
                        tries = 10
                        time = 60000L
                        marginError = 10
                        maxNumber = 10
                        resNumbers = generateNumbers(5, 10)
                        tvTries.text = tries.toString()
                        tvTimer.text = String.format("%02d:%02d", time / 60000L, time % 60000L)
                        zeroRow()
                        butRow()
                        addNumberPickerToGridLayout(grid, row, 0, maxNumber)
                        addNumberPickerToGridLayout(grid, row, 1, maxNumber)
                        addNumberPickerToGridLayout(grid, row, 2, maxNumber)
                        addNumberPickerToGridLayout(grid, row, 3, maxNumber)
                        addNumberPickerToGridLayout(grid, row, 4, maxNumber)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
        if (time > 0) {
            chrono.visibility = View.VISIBLE
        } else {
            chrono.visibility = View.GONE
        }
        tvTries.text = tries.toString()
    }
    //TODO pause timer on State.Paused, points on endgame, if corrects answer endgame, programmatically print layout, print answer
    //TODO redo showcolor

    private fun middleLevel(grid: GridLayout, row: Int, maxNumber: Int) {
        addSpaceToGridLayout(grid, row, 0)
        addNumberPickerToGridLayout(grid, row, 1, maxNumber)
        addNumberPickerToGridLayout(grid, row, 2, maxNumber)
        addNumberPickerToGridLayout(grid, row, 3, maxNumber)
        addSpaceToGridLayout(grid, row, 4)
    }

    private fun zeroRow() {
        val grid = binding.glContainer
        val row = 0
        val layoutParams = GridLayout.LayoutParams()
        val layoutParams1 = GridLayout.LayoutParams()

        layoutParams.rowSpec = GridLayout.spec(row)
        layoutParams.columnSpec = GridLayout.spec(2, 1f)
        layoutParams.setGravity(Gravity.FILL)
        grid.addView(tvTimer, layoutParams)

        layoutParams1.rowSpec = GridLayout.spec(row)
        layoutParams1.columnSpec = GridLayout.spec(3, 1f)
        layoutParams1.setGravity(Gravity.FILL)
        grid.addView(tvTries, layoutParams1)

        addSpaceToGridLayout(grid, row, 0)
        addSpaceToGridLayout(grid, row, 1)
        addSpaceToGridLayout(grid, row, 4)
    }

    private fun butRow() {
        val row = 2
        val grid = binding.glContainer
        val layoutParams = GridLayout.LayoutParams()
        layoutParams.rowSpec = GridLayout.spec(row)
        layoutParams.columnSpec = GridLayout.spec(2, 1f)
        layoutParams.setGravity(Gravity.FILL)
        grid.addView(butVerify, layoutParams)

        addSpaceToGridLayout(grid, row, 0)
        addSpaceToGridLayout(grid, row, 1)
        addSpaceToGridLayout(grid, row, 3)
        addSpaceToGridLayout(grid, row, 4)
    }

    private fun addNumberPickerToGridLayout(grid: GridLayout, row: Int, column: Int, maxVal: Int) {
        val numberPicker = NumberPicker(this)
        numberPicker.minValue = 0
        numberPicker.maxValue = maxVal
        numberPicker.id = NumberPicker.generateViewId()

        val layoutParams = GridLayout.LayoutParams()
        layoutParams.rowSpec = GridLayout.spec(row)
        layoutParams.columnSpec = GridLayout.spec(column, 1f)
        layoutParams.setGravity(Gravity.FILL)
        grid.addView(numberPicker, layoutParams)
        numberPickers.add(numberPicker)
    }

    private fun addSpaceToGridLayout(grid: GridLayout, row: Int, column: Int) {
        val space = Space(this)

        val layoutParams = GridLayout.LayoutParams()
        layoutParams.rowSpec = GridLayout.spec(row)
        layoutParams.columnSpec = GridLayout.spec(column, 1f)
        layoutParams.setGravity(Gravity.FILL)

        grid.addView(space, layoutParams)
    }

    fun generateNumbers(quantity: Int, max: Int): IntArray {
        val num = IntArray(quantity)
        val random = Random()
        for (i in 0 until quantity) {
            num[i] = random.nextInt(max + 1)
        }
        return num
    }

    override fun startGame() {
        state = State.ACTIVE
        binding.glContainer.isEnabled = true
        binding.faButPause.visibility = View.VISIBLE
        binding.faButPlay.visibility = View.GONE
        binding.vOverlay.visibility = View.GONE
        binding.vOverlay.isClickable = false
        binding.sbLevel.isEnabled = false
        if (time > 0L) {
            timer.start()
        }
        butVerify.setOnClickListener {
            if (--tries <= 0) {
                endGame()
            } else {
                verify()
            }
        }
    }

    private fun verify() {
        for (i in numberPickers.indices) {
            val response = numberPickers[i].value
            val number = resNumbers[i]
            val dif = abs(response - number)
            val percentage = dif * 100 / (resNumbers.maxOrNull() ?: 1)
            val color = if (percentage == 0) R.color.green
            else if (percentage <= marginError) R.color.orange
            else R.color.red
            showColor(i, color)
        }
        --tries
        tvTries.text = tries.toString()
    }

    fun showColor(index: Int, color: Int) {
        val grid = binding.glContainer
        val view = View(this)
        view.setBackgroundColor(color)
        val layoutParams = GridLayout.LayoutParams()
        layoutParams.rowSpec = GridLayout.spec(2)
        layoutParams.columnSpec = GridLayout.spec(index, 1f)
        layoutParams.setGravity(Gravity.FILL)
        grid.addView(view, layoutParams)
    }

    override fun pauseGame() {
        state = State.PAUSED
        binding.glContainer.isEnabled = false
        binding.faButPause.visibility = View.GONE
        binding.faButPlay.visibility = View.VISIBLE
        binding.vOverlay.visibility = View.VISIBLE
        binding.vOverlay.isClickable = true
        binding.sbLevel.isEnabled = true

        timer.cancel()
    }

    override fun endGame() {
        var score: Long = 0L
        var timeArray: List<String>

        state = State.ENDED
        timer.cancel()
        timeArray = tvTimer.text.split(":")
        if (time > 0L) {
            score = (timeArray[0].toLong() * 1000) + (timeArray[1].toLong() * 100)
        }
        score += when (level) {
            0 -> 10L
            1 -> 100L
            2 -> 200L
            3 -> 500L
            4 -> 1000L
            else -> 0L
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCombinationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        butVerify = Button(this)
        butVerify.text = "Verify"

        butVerify.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.matrix_theme_light_primary
            )
        )


        tvTimer = TextView(this)
        tvTries = TextView(this)
        tvTries.text = tries.toString()

        timer = object : CountDownTimer(time, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                time = millisUntilFinished / 1000
                showTime()
            }

            override fun onFinish() {
                Toast.makeText(this@CombinationActivity, "Time's up", Toast.LENGTH_SHORT).show()
                endGame()
            }

            private fun showTime(): String {
                val min = time / 60
                val sec = time % 60
                return String.format("%02d:%02d", min, sec)
            }
        }

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

        binding.faButPlay.setOnClickListener {
            startGame()
        }
        binding.faButPause.setOnClickListener {
            pauseGame()
        }
    }
}