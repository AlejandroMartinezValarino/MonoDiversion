package com.example.monodiversion.view

import android.os.Bundle
import androidx.activity.viewModels
import com.example.monodiversion.R
import com.example.monodiversion.view.fragment.BottomMenuFragment
import com.example.monodiversion.view.fragment.HeaderFragment
import com.example.monodiversion.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemoryActivity : GameActivity() {

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
        setContentView(R.layout.activity_memory)
        if(intent.hasExtra("id") && intent.getLongExtra("id",0L)!=0L){
            val id = intent.getLongExtra("id",0L)
            userViewModel.updateById(id)
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.flHeaderContainer, HeaderFragment.newInstance())
                .add(R.id.flBottomContainer, BottomMenuFragment.newInstance())
                .setReorderingAllowed(true)
                .commit()
        }
    }
}