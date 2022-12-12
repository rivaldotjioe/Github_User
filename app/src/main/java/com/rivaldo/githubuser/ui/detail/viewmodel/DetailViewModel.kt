package com.rivaldo.githubuser.ui.detail.viewmodel

import androidx.lifecycle.ViewModel
import com.rivaldo.githubuser.data.Resource
import com.rivaldo.githubuser.data.database.model.UserEntity
import com.rivaldo.githubuser.data.remote.model.ResponseDetailUser
import com.rivaldo.githubuser.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import retrofit2.Response

class DetailViewModel(val repository: UserRepository) : ViewModel() {

    fun checkIsFavorite(username: String): Boolean = repository.checkIsFavorite(username)
    fun insertUserFavorite(user: UserEntity) = repository.insertUserFavorite(user)
    fun deleteUserFavorite(user: UserEntity) = repository.deleteUserFavorite(user)
    fun getDetailUser(username: String) = repository.getUserDetail(username)

//    suspend fun getDetailUser(username: String): ResponseDetailUser {
//        repository.getUserDetail(username).collect { result ->
//            when (result) {
//                is Resource.Loading -> {
//                    _isLoading.emit(true)
//                }
//                is Resource.Success -> {
//                    _isLoading.emit(false)
//                    if (result.data != null ){
//                        return@collect result.data
//                    }
//                }
//                is Resource.Error -> {
//                    _isLoading.emit(false)
//                }
//            }
//        }
//    }

}