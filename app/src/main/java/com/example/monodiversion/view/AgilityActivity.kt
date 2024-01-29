package com.example.monodiversion.view

import android.os.Bundle
import androidx.activity.viewModels
import com.example.monodiversion.R
import com.example.monodiversion.view.fragment.BottomMenuFragment
import com.example.monodiversion.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgilityActivity : GameActivity() {

    private val userViewModel: UserViewModel by viewModels()

    override fun inflateLayout() {
        TODO("Not yet implemented")
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
        setContentView(R.layout.activity_agility)
        userViewModel
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.flBottomContainer, BottomMenuFragment.newInstance())
                .setReorderingAllowed(true)
                .commit()
        }
    }
}