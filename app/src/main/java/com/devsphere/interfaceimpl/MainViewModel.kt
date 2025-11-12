package com.devsphere.interfaceimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    fun loadUsers() {
        viewModelScope.launch {
            val list = repository.fetchUsers()
            if (list != null) {
                _users.value = list
                _isSuccess.value = true
            } else {
                _isSuccess.value = false
            }
        }
    }
}
