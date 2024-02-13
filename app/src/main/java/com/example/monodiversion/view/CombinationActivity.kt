package com.example.monodiversion.view

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
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
    private var numberPickers = mutableListOf<NumberPicker>()
    private var level: Int = 0
    private var tries: Int = 0
    private var time: Long = 0L
    private val rowZero = 0
    private val rowOne = 1
    private val rowTwo = 2
    private val rowThree = 3
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


        binding.sbLevel.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                level = progress
                grid.removeAllViews()
                when (level) {
                    0 -> {
                        maxNumber = 5
                        tries = 100
                        time = 0L
                        marginError = 50
                        resNumbers = generateNumbers(1, 5)
                        tvTries.text = tries.toString()
                        zeroRow()
                        butRow()
                        numberPickers = mutableListOf<NumberPicker>()
                        addNumberPickerToGridLayout(grid, rowOne, 2, maxNumber)
                        addSpaceToGridLayout(grid, rowOne, 0)
                        addSpaceToGridLayout(grid, rowOne, 1)
                        addSpaceToGridLayout(grid, rowOne, 3)
                        addSpaceToGridLayout(grid, rowOne, 4)
                    }

                    1 -> {
                        tries = 30
                        time = 0L
                        marginError = 40
                        resNumbers = generateNumbers(3, 5)
                        tvTries.text = tries.toString()
                        numberPickers = mutableListOf<NumberPicker>()
                        zeroRow()
                        butRow()
                        middleLevel(grid, rowOne, 5)
                    }

                    2 -> {
                        tries = 25
                        time = 0L
                        marginError = 30
                        resNumbers = generateNumbers(3, 10)
                        tvTries.text = tries.toString()
                        numberPickers = mutableListOf<NumberPicker>()
                        zeroRow()
                        butRow()
                        middleLevel(grid, rowOne, 10)
                    }

                    3 -> {
                        tries = 20
                        time = 90000L
                        marginError = 20
                        resNumbers = generateNumbers(3, 10)
                        tvTries.text = tries.toString()
                        tvTimer.text = String.format("%02d:%02d", time / 60000L, time % 60000L)
                        numberPickers = mutableListOf<NumberPicker>()
                        zeroRow()
                        butRow()
                        middleLevel(grid, rowOne, 10)
                    }

                    4 -> {
                        tries = 10
                        time = 60000L
                        marginError = 10
                        maxNumber = 10
                        resNumbers = generateNumbers(5, 10)
                        numberPickers = mutableListOf<NumberPicker>()
                        tvTries.text = tries.toString()
                        tvTimer.text = String.format("%02d:%02d", time / 60000L, time % 60000L)
                        zeroRow()
                        butRow()
                        addNumberPickerToGridLayout(grid, rowOne, 0, maxNumber)
                        addNumberPickerToGridLayout(grid, rowOne, 1, maxNumber)
                        addNumberPickerToGridLayout(grid, rowOne, 2, maxNumber)
                        addNumberPickerToGridLayout(grid, rowOne, 3, maxNumber)
                        addNumberPickerToGridLayout(grid, rowOne, 4, maxNumber)
                    }
                }

                if (time > 0L) {
                    chrono.visibility = View.VISIBLE
                } else {
                    chrono.visibility = View.GONE
                }
                timer = object : CountDownTimer(time, 1000L) {
                    override fun onTick(millisUntilFinished: Long) {
                        time = millisUntilFinished / 1000L
                        tvTimer.text = showTime()
                    }

                    override fun onFinish() {
                        Toast.makeText(this@CombinationActivity, "Time's up", Toast.LENGTH_SHORT)
                            .show()
                        endGame()
                    }

                    private fun showTime(): String {
                        val min = time / 60
                        val sec = time % 60
                        return String.format("%02d:%02d", min, sec)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
    }
    private fun middleLevel(grid: GridLayout, row: Int, maxNumber: Int) {
        addSpaceToGridLayout(grid, row, 0)
        addNumberPickerToGridLayout(grid, row, 1, maxNumber)
        addNumberPickerToGridLayout(grid, row, 2, maxNumber)
        addNumberPickerToGridLayout(grid, row, 3, maxNumber)
        addSpaceToGridLayout(grid, row, 4)
    }

    private fun colorLevel(colorArray: IntArray) {
        colorArray.sort()
        when (level) {
            0 -> {
                val view = TextView(this)
                val layoutParams = GridLayout.LayoutParams()
                view.text = " "
                view.textSize = 100f

                layoutParams.rowSpec = GridLayout.spec(rowThree)
                layoutParams.columnSpec = GridLayout.spec(2, 1f)
                layoutParams.setGravity(Gravity.FILL)
                view.setBackgroundColor(ContextCompat.getColor(this, colorArray[0]))
                binding.glContainer.addView(view, layoutParams)

                addSpaceToGridLayout(binding.glContainer, rowThree, 0)
                addSpaceToGridLayout(binding.glContainer, rowThree, 1)
                addSpaceToGridLayout(binding.glContainer, rowThree, 3)
                addSpaceToGridLayout(binding.glContainer, rowThree, 4)

            }

            1, 2, 3 -> {
                for (i in 0..2) {
                    val view = TextView(this)
                    val layoutParams = GridLayout.LayoutParams()
                    view.text = " "
                    view.textSize = 100f

                    layoutParams.rowSpec = GridLayout.spec(rowThree)
                    layoutParams.columnSpec = GridLayout.spec(i + 1, 1f)
                    layoutParams.setGravity(Gravity.FILL)
                    view.setBackgroundColor(ContextCompat.getColor(this, colorArray[i]))
                    binding.glContainer.addView(view, layoutParams)
                }
                addSpaceToGridLayout(binding.glContainer, rowThree, 0)
                addSpaceToGridLayout(binding.glContainer, rowThree, 4)
                //checkGridLayoutContent(binding.glContainer)
            }

            4 -> {
                for (i in 0..4) {
                    val view = TextView(this)
                    val layoutParams = GridLayout.LayoutParams()
                    view.text = " "
                    view.textSize = 100f

                    layoutParams.rowSpec = GridLayout.spec(rowThree)
                    layoutParams.columnSpec = GridLayout.spec(i, 1f)
                    layoutParams.setGravity(Gravity.FILL)
                    view.setBackgroundColor(ContextCompat.getColor(this, colorArray[i]))
                    binding.glContainer.addView(view, layoutParams)
                }
            }
        }
    }

    private fun checkGridLayoutContent(gridLayout: GridLayout) {
        Log.d("+++", "checkGridLayoutContent: ")
        for (row in 0 until gridLayout.rowCount) {
            for (column in 0 until gridLayout.columnCount) {
                val childView = gridLayout.getChildAt(row * gridLayout.columnCount + column)
                Log.d("+++", "row: $row, column $column: $childView ")
            }
        }
    }


    private fun zeroRow() {
        val grid = binding.glContainer
        val layoutParams = GridLayout.LayoutParams()
        val layoutParams1 = GridLayout.LayoutParams()

        layoutParams.rowSpec = GridLayout.spec(rowZero)
        layoutParams.columnSpec = GridLayout.spec(2, 1f)
        layoutParams.setGravity(Gravity.FILL)
        grid.addView(tvTimer, layoutParams)

        layoutParams1.rowSpec = GridLayout.spec(rowZero)
        layoutParams1.columnSpec = GridLayout.spec(3, 1f)
        layoutParams1.setGravity(Gravity.FILL)
        grid.addView(tvTries, layoutParams1)

        addSpaceToGridLayout(grid, rowZero, 0)
        addSpaceToGridLayout(grid, rowZero, 1)
        addSpaceToGridLayout(grid, rowZero, 4)
    }

    private fun butRow() {
        val grid = binding.glContainer
        val layoutParams = GridLayout.LayoutParams()
        layoutParams.rowSpec = GridLayout.spec(rowTwo)
        layoutParams.columnSpec = GridLayout.spec(2, 1f)
        layoutParams.setGravity(Gravity.FILL)
        grid.addView(butVerify, layoutParams)

        addSpaceToGridLayout(grid, rowTwo, 0)
        addSpaceToGridLayout(grid, rowTwo, 1)
        addSpaceToGridLayout(grid, rowTwo, 3)
        addSpaceToGridLayout(grid, rowTwo, 4)
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
        binding.faButPause.visibility = View.VISIBLE
        binding.faButPlay.visibility = View.GONE
        binding.vOverlay.visibility = View.GONE
        binding.vOverlay.isClickable = false
        binding.sbLevel.isEnabled = false
        if (time > 0L) {
            timer.start()
        }
        if ((userViewModel.score.value?.points ?: 0) > 0) {
            userViewModel.setNewScore(GameType.COMBINATION)
        }

        butVerify.setOnClickListener {
            var npRes: IntArray = intArrayOf()
            for (i in numberPickers.indices) {
                npRes += numberPickers[i].value
            }
            if (tries <= 0 || npRes contentEquals resNumbers) {
                verify()
                endGame()
            } else {
                verify()
            }
        }
    }

    private fun verify() {
        var colors: IntArray = intArrayOf()
        for (i in numberPickers.indices) {
            val response = numberPickers[i].value
            val number = resNumbers[i]
            val dif = abs(response - number)
            val percentage =
                dif * 100 / (if ((resNumbers.maxOrNull() ?: 1) == 0) 1 else resNumbers.maxOrNull()
                    ?: 1)
            val color = if (percentage == 0) R.color.green
            else if (percentage <= marginError) R.color.orange
            else R.color.red
            colors += color
        }
        colorLevel(colors)
        --tries
        tvTries.text = tries.toString()
    }

    override fun pauseGame() {
        binding.faButPause.visibility = View.GONE
        binding.faButPlay.visibility = View.VISIBLE
        binding.vOverlay.visibility = View.VISIBLE
        binding.vOverlay.isClickable = true
        binding.sbLevel.isEnabled = true
        timer.cancel()
    }

    override fun endGame() {
        timer.cancel()
        var points = 0L
        var timeArray: List<String>

        var npRes: IntArray = intArrayOf()
        for (i in numberPickers.indices) {
            npRes += numberPickers[i].value
        }
        if (tries > 0 && npRes contentEquals resNumbers) {
            timeArray = tvTimer.text.split(":")
            if (time > 0L) {
                points = (timeArray[0].toLong() * 1000L) + (timeArray[1].toLong() * 100L)
            }
            points += when (level) {
                0 -> 10L
                1 -> 100L
                2 -> 200L
                3 -> 500L
                4 -> 1000L
                else -> 0L
            }
            userViewModel.updateScore(GameType.COMBINATION,points)
            userViewModel.saveScore()
            Handler(Looper.getMainLooper()).postDelayed({
                userViewModel.setNewScore(GameType.COMBINATION)
            }, 3000)
        }

        showResponse()
        pauseGame()
    }

    private fun showResponse() {
        val row = 4
        val grid = binding.glContainer
        when (level) {
            0 -> {
                val tvRes = TextView(this)
                val layoutParams = GridLayout.LayoutParams()
                tvRes.textSize = 100f

                layoutParams.rowSpec = GridLayout.spec(row)
                layoutParams.columnSpec = GridLayout.spec(2, 1f)
                layoutParams.setGravity(Gravity.FILL)
                tvRes.text = resNumbers[0].toString()
                grid.addView(tvRes, layoutParams)
                addSpaceToGridLayout(grid, row, 0)
                addSpaceToGridLayout(grid, row, 1)
                addSpaceToGridLayout(grid, row, 3)
                addSpaceToGridLayout(grid, row, 4)
            }

            1, 2, 3 -> {
                for (i in 0..2) {
                    val tvRes = TextView(this)
                    val layoutParams = GridLayout.LayoutParams()
                    tvRes.textSize = 100f

                    layoutParams.rowSpec = GridLayout.spec(row)
                    layoutParams.columnSpec = GridLayout.spec(i + 1, 1f)
                    layoutParams.setGravity(Gravity.FILL)
                    tvRes.text = resNumbers[i].toString()
                    grid.addView(tvRes, layoutParams)
                }
                addSpaceToGridLayout(grid, row, 0)
                addSpaceToGridLayout(grid, row, 4)
            }

            4 -> {
                for (i in 0..4) {
                    val tvRes = TextView(this)
                    val layoutParams = GridLayout.LayoutParams()
                    tvRes.textSize = 100f

                    layoutParams.rowSpec = GridLayout.spec(row)
                    layoutParams.columnSpec = GridLayout.spec(i, 1f)
                    layoutParams.setGravity(Gravity.FILL)
                    tvRes.text = resNumbers[i].toString()
                    grid.addView(tvRes, layoutParams)
                }
            }
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
        tvTimer.text = "00:00"
        tvTries = TextView(this)
        tvTries.text = tries.toString()

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