package com.example.monodiversion.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monodiversion.helper.GameType
import com.example.monodiversion.model.Score
import com.example.monodiversion.model.ScoreRepository
import com.example.monodiversion.model.User
import com.example.monodiversion.model.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository,
    private val userRepository: UserRepository
):ViewModel() {
    private val _user = MutableLiveData(User())
    private val _scores = MutableLiveData<List<Score>>()
    
    val userScore:LiveData<User> get() = _user
    val scores:LiveData<List<Score>> get() = _scores

    fun getUserById(userId:Long){
        viewModelScope.launch {
            _user.value = userRepository.getUserById(userId)
        }
    }
    fun getScoresByIdAndType(userId: Long,type:GameType){
        viewModelScope.launch {
            val scoreList =  scoreRepository.getScoresByUserAndGameType(userId,type)
            _scores.postValue(scoreList)
        }
    }
}