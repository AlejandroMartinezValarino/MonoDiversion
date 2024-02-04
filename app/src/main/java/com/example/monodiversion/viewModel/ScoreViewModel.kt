package com.example.monodiversion.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.monodiversion.model.ScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository
):ViewModel() {
    private val _gameLevel = MutableLiveData(0)

    val gameLevel:LiveData<Int> get() = _gameLevel

    fun updateGameLevel(level:Int){
        _gameLevel.value = level
    }
}