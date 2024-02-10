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
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val scoreRepository: ScoreRepository
) : ViewModel() {
    private val _isLightTheme = MutableLiveData(true)
    private val _userId = MutableLiveData(0L)
    private val _user = MutableLiveData(User())
    private val _score = MutableLiveData(Score())

    val userId: LiveData<Long> get() = _userId
    val users = MutableLiveData<List<User>>()
    val isLoading = MutableLiveData<Boolean>()
    val isLightTheme: LiveData<Boolean> get() = _isLightTheme
    val user: LiveData<User> get() = _user
    val score: LiveData<Score> get() = _score

    fun updateTheme(isLight: Boolean) {
        _isLightTheme.value = isLight
    }

    fun updateUser(user: User) {
        _user.postValue(user)
    }

    fun updateScore(points: Long) {
        _score.postValue(Score(points = points))
    }

    fun setGameType(gameType: GameType) {
        _score.value?.gameType = gameType
    }
    fun setNewScore(gameType: GameType){
        Log.d("+++", "setNewScore: ")
        _score.postValue(Score())
        setGameType(gameType)
    }
    fun saveScore(){
        viewModelScope.launch {
            score.value?.let { score->
                if(score.points>0){
                    user.value?.let { user->
                        scoreRepository.upsertScore(score,user.id)
                    }
                }
            }
        }
    }

    fun save() {
        viewModelScope.launch {
            user.value?.let { user ->
                if (user.flag != null) {
                    val id = userRepository.save(user)
                    _userId.postValue(id)
                }
            }
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val userList = userRepository.getAll()
            users.postValue(userList)
            isLoading.postValue(false)
        }
    }

    fun updateById(id: Long) {
        viewModelScope.launch {
            val user = userRepository.getUserById(id)
            _user.postValue(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            if (userRepository.exist(user)) {
                userRepository.remove(user)
            }
        }
    }
}