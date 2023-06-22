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

    private val _postList = ArrayList<PostItem>()
    private val _postListLiveData: MutableLiveData<ArrayList<PostItem>> = MutableLiveData()
    fun addPost(post: PostItem) {
        _postList.add(post)
        _postListLiveData.value = _postList
    }
    val postListLive = _postListLiveData
}