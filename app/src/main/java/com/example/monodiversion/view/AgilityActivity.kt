package com.example.monodiversion.view

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.Space
import androidx.activity.viewModels
import com.example.monodiversion.R
import com.example.monodiversion.databinding.ActivityAgilityBinding
import com.example.monodiversion.helper.GameType
import com.example.monodiversion.view.fragment.BottomMenuFragment
import com.example.monodiversion.view.fragment.HeaderFragment
import com.example.monodiversion.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class AgilityActivity : GameActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding:ActivityAgilityBinding
    private lateinit var timer:CountDownTimer
    private lateinit var currentButton: ImageButton
    private val start = 0
    private val middleRow = 2
    private val middleCol = 2
    private val totalTime = 10000L
    private val interval = 100L
    private val greenButton = R.drawable.round_button_0250
    private var points = 0L

    override fun inflateLayout() {
        val grid = binding.glContainer
        val pb = binding.pbHorizontal
        pb.max = (totalTime / interval).toInt()

        timer = object : CountDownTimer(totalTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = ((totalTime - millisUntilFinished) / interval).toInt()
                pb.progress = progress
            }

            override fun onFinish() {
                pb.progress = pb.max
                endGame()
            }
        }

        for (row in start until grid.rowCount) {
            for (col in start until grid.columnCount) {
                val space = Space(this)
                val layoutParams = GridLayout.LayoutParams()
                layoutParams.rowSpec = GridLayout.spec(row,1f)
                layoutParams.columnSpec = GridLayout.spec(col, 1f)
                layoutParams.setGravity(Gravity.FILL)

                grid.addView(space, layoutParams)
            }
        }
        currentButton = ImageButton(this)
        currentButton.setImageResource(greenButton)
        val layoutParams = GridLayout.LayoutParams()
        layoutParams.rowSpec = GridLayout.spec(middleRow,1f)
        layoutParams.columnSpec = GridLayout.spec(middleCol,1f)
        grid.addView(currentButton,layoutParams)
    }

    private fun replace() {
        val grid = binding.glContainer

        currentButton.let { button ->
            val space = Space(this)
            space.layoutParams = button.layoutParams
            grid.removeView(button)
            grid.addView(space)
        }

        val randomRow = Random.nextInt(grid.rowCount)
        val randomCol = Random.nextInt(grid.columnCount)

        val button = ImageButton(this)
        button.setImageResource(greenButton)
        val layoutParams = GridLayout.LayoutParams()
        layoutParams.rowSpec = GridLayout.spec(randomRow,1f)
        layoutParams.columnSpec = GridLayout.spec(randomCol,1f)
        layoutParams.setGravity(Gravity.FILL)
        button.layoutParams = layoutParams

        grid.addView(button)

        currentButton = button
        currentButton.setOnClickListener {
            points += 100
            userViewModel.updateScore(GameType.AGILITY,points)
            replace()
        }
    }
    override fun startGame() {
        binding.faButPause.visibility = View.VISIBLE
        binding.faButPlay.visibility = View.GONE
        binding.vOverlay.visibility = View.GONE
        binding.vOverlay.isClickable = false
        timer.start()
        currentButton.setOnClickListener {
            points += 100
            userViewModel.updateScore(GameType.AGILITY,points)
            replace()
        }
    }

    override fun pauseGame() {
        binding.faButPause.visibility = View.GONE
        binding.faButPlay.visibility = View.VISIBLE
        binding.vOverlay.visibility = View.VISIBLE
        binding.vOverlay.isClickable = true
        timer.cancel()
    }

    override fun endGame() {
        if (points > 0L){
            userViewModel.saveScore()
        }
        pauseGame()
        Handler(Looper.getMainLooper()).postDelayed({
            userViewModel.setNewScore(GameType.AGILITY)
        }, 3000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgilityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(intent.hasExtra("id") && intent.getLongExtra("id",0L)!=0L){
            val id = intent.getLongExtra("id",0L)
            userViewModel.updateById(id)
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.flHeaderContainer,HeaderFragment.newInstance())
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