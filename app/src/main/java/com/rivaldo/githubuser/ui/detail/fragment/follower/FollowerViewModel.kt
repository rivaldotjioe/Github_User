package com.rivaldo.githubuser.ui.detail.fragment.follower

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldo.githubuser.data.Resource
import com.rivaldo.githubuser.data.remote.model.ResponseFollowersItem
import com.rivaldo.githubuser.data.repository.UserRepository
import com.rivaldo.githubuser.model.User
import com.rivaldo.githubuser.utils.DataMapper.mapToUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FollowerViewModel(val repository: UserRepository) : ViewModel() {

    private var _listUser: MutableStateFlow<List<User>> = MutableStateFlow(listOf())
    var listUser: StateFlow<List<User>> = _listUser

    var _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var isLoading: StateFlow<Boolean> = _isLoading

    fun getListFollower(username: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFollowerByUsername(username).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        val listFollower = result.data as List<ResponseFollowersItem>
                        _listUser.emit(listFollower.mapToUser())
                        _isLoading.emit(false)
                    }
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }
                    is Resource.Error -> {
                        _isLoading.emit(false)
                    }
                }
            }
        }

    }
}