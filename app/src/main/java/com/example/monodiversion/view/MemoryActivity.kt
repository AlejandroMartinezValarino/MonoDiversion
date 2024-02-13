package com.example.monodiversion.view

import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.SeekBar
import android.widget.Space
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.marginTop
import com.example.monodiversion.R
import com.example.monodiversion.databinding.ActivityMemoryBinding
import com.example.monodiversion.helper.GameType
import com.example.monodiversion.view.fragment.BottomMenuFragment
import com.example.monodiversion.view.fragment.HeaderFragment
import com.example.monodiversion.viewModel.ScoreViewModel
import com.example.monodiversion.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import kotlin.concurrent.schedule

@AndroidEntryPoint
class MemoryActivity : GameActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityMemoryBinding

    private var level: Int = 0
    private var time: Long = 10000L
    private val maxRow = 3
    private val minRow = 1
    private val maxColumn = 3
    private val minColumn = 1
    private val start = 0
    private var numbers: IntArray = intArrayOf()
    private var selected: IntArray = intArrayOf()

    private lateinit var timer: CountDownTimer
    private lateinit var tvTimer: TextView

    override fun inflateLayout() {
        val grid = binding.glContainer

        binding.sbLevel.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                level = progress
                grid.removeAllViews()
                when (level) {
                    0 -> {
                        initBasicGrid()
                        numbers = (1..5).shuffled().toIntArray()
                        zeroRow()
                        addTextNumber(1, 1, numbers[0])
                        addTextNumber(1, 3, numbers[1])
                        addTextNumber(2, 2, numbers[2])
                        addTextNumber(3, 1, numbers[3])
                        addTextNumber(3, 3, numbers[4])
                    }

                    1 -> {
                        initBasicGrid()
                        numbers = (1..5).shuffled().toIntArray()
                        zeroRow()
                        addTextNumber(1, 1, numbers[0])
                        addTextNumber(1, 3, numbers[1])
                        addTextNumber(2, 2, numbers[2])
                        addTextNumber(3, 1, numbers[3])
                        addTextNumber(3, 3, numbers[4])
                    }

                    2 -> {
                        numbers = (1..9).shuffled().toIntArray()
                        var i = 0
                        zeroRow()
                        for (row in minRow..maxRow) {
                            for (col in minColumn..maxColumn) {
                                if(i < numbers.size){
                                    addTextNumber(row,col,numbers[i++])
                                }
                            }
                        }
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        timer = object : CountDownTimer(time, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                time = millisUntilFinished / 1000L
                tvTimer.text = showTime()
            }

            override fun onFinish() {
                Toast.makeText(this@MemoryActivity, "You lost", Toast.LENGTH_SHORT).apply {
                    setGravity(Gravity.CENTER, 0, 0)
                    show()
                }
                endGame()
            }

            private fun showTime(): String {
                val min = time / 60
                val sec = time % 60
                return String.format("%02d:%02d", min, sec)
            }
        }
    }

    private fun addTextNumber(row: Int, col: Int, num: Int) {
        val grid = binding.glContainer
        val tvNum = TextView(this)
        val sortedNumbers = numbers.sorted().toIntArray()

        tvNum.text = num.toString()
        tvNum.textSize = 50f
        tvNum.setTextColor(Color.BLACK)

        val layoutParams = GridLayout.LayoutParams()
        layoutParams.rowSpec = GridLayout.spec(row)
        layoutParams.columnSpec = GridLayout.spec(col, 1f)
        layoutParams.setGravity(Gravity.FILL)
        tvNum.setOnClickListener {
            val clickedNumber = (it as TextView).text.toString()
            selected += clickedNumber.toInt()
            if (selected.size > 1) {
                for (i in selected.indices) {
                    if ((i > 0 && selected[i] - 1 != selected[i - 1]) || (i == 0 && selected[i] != 1)) {
                        Toast.makeText(this, "You lost", Toast.LENGTH_SHORT).apply {
                            setGravity(Gravity.CENTER, 0, 0)
                            show()
                        }
                        Handler(Looper.getMainLooper()).postDelayed({
                            pauseGame()
                        }, 3000)
                    }
                }
                if (sortedNumbers contentEquals selected) {
                    Toast.makeText(this, "Congratulations", Toast.LENGTH_SHORT).apply {
                        setGravity(Gravity.CENTER, 0, 0)
                        show()
                    }
                    endGame()
                }
            }
        }

        grid.addView(tvNum, layoutParams)
    }

    private fun initBasicGrid() {
        val grid = binding.glContainer

        for (row in start..maxRow) {
            for (col in start..maxColumn) {
                val space = Space(this)
                val layoutParams = GridLayout.LayoutParams()
                layoutParams.rowSpec = GridLayout.spec(row)
                layoutParams.columnSpec = GridLayout.spec(col, 1f)
                layoutParams.setGravity(Gravity.FILL)

                grid.addView(space, layoutParams)
            }
        }
    }

    private fun zeroRow() {
        val grid = binding.glContainer
        val layoutParams = GridLayout.LayoutParams()

        layoutParams.rowSpec = GridLayout.spec(start)
        layoutParams.columnSpec = GridLayout.spec(2, 1f)
        layoutParams.setGravity(Gravity.FILL)
        grid.addView(tvTimer, layoutParams)

        addSpaceToGridLayout(start, 0)
        addSpaceToGridLayout(start, 1)
        addSpaceToGridLayout(start, 3)
        addSpaceToGridLayout(start, 4)
    }

    private fun addSpaceToGridLayout(row: Int, column: Int) {
        val space = Space(this)
        val grid = binding.glContainer

        val layoutParams = GridLayout.LayoutParams()
        layoutParams.rowSpec = GridLayout.spec(row)
        layoutParams.columnSpec = GridLayout.spec(column, 1f)
        layoutParams.setGravity(Gravity.FILL)

        grid.addView(space, layoutParams)
    }

    private fun changeTextViewBackground() {
        val gridLayout = binding.glContainer
        val borderWidth = 10
        val borderColor = Color.WHITE
        val borderDrawable = ShapeDrawable(RectShape()).apply {
            paint.color = borderColor
            paint.style = android.graphics.Paint.Style.STROKE
            paint.strokeWidth = borderWidth.toFloat()
        }
        val layers = arrayOf(borderDrawable, ShapeDrawable(RectShape()).apply {
            paint.color = Color.BLACK
            paint.style = android.graphics.Paint.Style.FILL
        })
        val layerDrawable = LayerDrawable(layers)
        for (i in 0 until gridLayout.childCount) {
            val childView = gridLayout.getChildAt(i)
            if (childView is TextView) {
                childView.background = layerDrawable
                val params = childView.layoutParams as GridLayout.LayoutParams
                params.setMargins(borderWidth, borderWidth, borderWidth, borderWidth)
                childView.layoutParams = params
            }
        }
    }

    override fun startGame() {
        binding.faButPause.visibility = View.VISIBLE
        binding.faButPlay.visibility = View.GONE
        binding.vOverlay.setBackgroundColor(Color.TRANSPARENT)
        binding.sbLevel.isEnabled = false

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            binding.vOverlay.visibility = View.GONE
            binding.vOverlay.isClickable = false
            if (level > 0) {
                changeTextViewBackground()
            }
            timer.start()
        }, 3000L)
    }

    override fun pauseGame() {
        binding.faButPause.visibility = View.GONE
        binding.faButPlay.visibility = View.VISIBLE
        binding.vOverlay.visibility = View.VISIBLE
        binding.vOverlay.isClickable = true
        binding.vOverlay.setBackgroundColor(Color.BLACK)
        binding.sbLevel.isEnabled = true
        timer.cancel()
    }

    override fun endGame() {
        timer.cancel()
        var points: Long
        val timeArray: List<String> = tvTimer.text.split(":")
        points = (timeArray[0].toLong() * 1000L) + (timeArray[1].toLong() * 100L)
        points *= (level + 1)
        userViewModel.updateScore(GameType.MEMORY,points)
        if(points>0){
            userViewModel.saveScore()
            Handler(Looper.getMainLooper()).postDelayed({
                userViewModel.setNewScore(GameType.MEMORY)
            }, 3000)
        }
        pauseGame()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvTimer = TextView(this)
        tvTimer.text = String.format("%02d:%02d", time / 60000L, time % 60000L)
        tvTimer.setTextColor(Color.WHITE)
        tvTimer.textSize = 50f

        if (intent.hasExtra("id") && intent.getLongExtra("id", 0L) != 0L) {
            val id = intent.getLongExtra("id", 0L)
            userViewModel.updateById(id)
        }

        userViewModel.setGameType(GameType.MEMORY)

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