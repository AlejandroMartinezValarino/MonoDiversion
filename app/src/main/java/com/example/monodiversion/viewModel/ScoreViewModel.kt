package com.example.monodiversion.viewModel

import androidx.lifecycle.ViewModel
import com.example.monodiversion.model.ScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository
):ViewModel() {

}