package com.example.monodiversion.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.monodiversion.R
import com.example.monodiversion.view.fragment.BottomMenuFragment
import com.example.monodiversion.view.fragment.HeaderFragment
import com.example.monodiversion.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoreActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        if(intent.hasExtra("id") && intent.getLongExtra("id",0L)!=0L){
            val id = intent.getLongExtra("id",0L)
            userViewModel.updateById(id)
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.flBottomContainer, BottomMenuFragment.newInstance())
                .setReorderingAllowed(true)
                .commit()
        }
    }
}