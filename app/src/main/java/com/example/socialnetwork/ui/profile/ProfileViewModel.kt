package com.example.socialnetwork.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _login = MutableLiveData<String>().apply {
        value = "Egor"
    }
    val login: LiveData<String> = _login

    private val _status = MutableLiveData<String>().apply {
        value = "I'm here"
    }
    val status: LiveData<String> = _status
}