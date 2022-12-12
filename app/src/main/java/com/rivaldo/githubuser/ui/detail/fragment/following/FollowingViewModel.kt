package com.rivaldo.githubuser.ui.detail.fragment.following

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldo.githubuser.data.Resource
import com.rivaldo.githubuser.data.remote.model.ResponseFollowingItem
import com.rivaldo.githubuser.data.repository.UserRepository
import com.rivaldo.githubuser.model.User
import com.rivaldo.githubuser.utils.DataMapper.mapToUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FollowingViewModel(val repository: UserRepository) : ViewModel() {
    private var _listUser: MutableStateFlow<List<User>> = MutableStateFlow(listOf())
    var listUser: StateFlow<List<User>> = _listUser

    var _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var isLoading: StateFlow<Boolean> = _isLoading

    fun getListFollowing(username: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFollowingByUsername(username).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        val listFollowing = result.data as List<ResponseFollowingItem>
                        _listUser.emit(listFollowing.mapToUser())
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