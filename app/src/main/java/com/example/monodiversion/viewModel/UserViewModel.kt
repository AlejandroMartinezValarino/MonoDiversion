package com.example.monodiversion.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monodiversion.model.User
import com.example.monodiversion.model.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
):ViewModel() {
    private val _isLightTheme = MutableLiveData(true)
    private val _userId = MutableLiveData(0L)
    private val _user = MutableLiveData(User())

    val userId:LiveData<Long> get() = _userId
    val users = MutableLiveData<List<User>>()
    val isLoading = MutableLiveData<Boolean>()
    val isLightTheme:LiveData<Boolean> get() = _isLightTheme
    val user:LiveData<User> get() = _user

    fun updateTheme(isLight:Boolean){
        _isLightTheme.value = isLight
    }
    fun updateUser(user: User) {
        _user.value = user
    }
    fun save(user:User){
        viewModelScope.launch {
            if(user.flag != null){
                val id = userRepository.save(user)
                _userId.postValue(id)
            }
        }
    }
    fun getUsers(){
        viewModelScope.launch {
            isLoading.postValue(true)
            val userList = userRepository.getAll()
            users.postValue(userList)
            isLoading.postValue(false)
        }
    }
    fun updateById(id:Long){
        viewModelScope.launch {
            val user = userRepository.getUserById(id)
            _user.postValue(user)
        }
    }
    fun deleteUser(user: User){
        viewModelScope.launch {
            if(userRepository.exist(user)){
                userRepository.remove(user)
            }
        }
    }
}