package com.example.monodiversion.view

import com.example.monodiversion.helper.UserAdapter
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.monodiversion.databinding.ActivityUsersBinding
import com.example.monodiversion.view.fragment.BottomMenuFragment
import com.example.monodiversion.view.fragment.HeaderFragment
import com.example.monodiversion.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersActivity : AppCompatActivity() {

    private val userViewModel:UserViewModel by viewModels()
    private lateinit var binding:ActivityUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(binding.flBottomContainer.id, BottomMenuFragment.newInstance())
                .setReorderingAllowed(true)
                .commit()
        }
        initView()
    }
    private fun initView(){
        val container:RecyclerView = binding.usersContainer
        userViewModel.getUsers()
        userViewModel.users.observe(this){users->
            val userAdapter = UserAdapter(this,users)
            container.adapter = userAdapter
        }
        userViewModel.isLoading.observe(this){
            binding.pbLoading.isVisible = it
        }
    }
}