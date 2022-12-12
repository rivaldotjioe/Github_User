package com.rivaldo.githubuser.ui.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldo.githubuser.data.repository.UserRepository
import com.rivaldo.githubuser.model.User
import com.rivaldo.githubuser.utils.DataMapper.mapListUserEntityToListUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(val repo: UserRepository) : ViewModel() {
    private var _listUser: MutableStateFlow<List<User>> = MutableStateFlow(listOf())
    var listUser : StateFlow<List<User>> = _listUser

    fun getListUser() {
        viewModelScope.launch {
            repo.getAllUser().collect{
                _listUser.emit(it.mapListUserEntityToListUser())
            }
        }
    }
}