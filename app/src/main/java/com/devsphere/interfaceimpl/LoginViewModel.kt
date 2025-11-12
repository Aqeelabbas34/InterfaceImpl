package com.devsphere.interfaceimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    fun registerUser(name: String, email: String) {
        if (name.isEmpty() || email.isEmpty()) {
            _isSuccess.value = false
            return
        }

        _isLoading.value = true
        viewModelScope.launch {
            val success = repository.registerUser(User(name, email))
            _isLoading.value = false
            _isSuccess.value = success
        }
    }
}
