package com.rivaldo.githubuser.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rivaldo.githubuser.data.Resource
import com.rivaldo.githubuser.data.repository.UserRepository
import com.rivaldo.githubuser.model.User
import com.rivaldo.githubuser.utils.JsonUtils
import com.rivaldo.githubuser.utils.MainUtils
import com.rivaldo.githubuser.utils.SettingsPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainViewModel(val repository: UserRepository): ViewModel() {
    private var _listUser: MutableStateFlow<List<User>> = MutableStateFlow(listOf())
    var listUser: StateFlow<List<User>> = _listUser
    var prevListUser: List<User> = listOf()
    var _isLoading : MutableStateFlow<Boolean> = MutableStateFlow(false)
    var isLoading : StateFlow<Boolean> = _isLoading
    lateinit var settingsPreferences: SettingsPreferences
    var isDarkModeActive: Boolean = false

    @JvmName("setSettingsPreferences1")
    fun setSettingsPreferences(settingsPreferences: SettingsPreferences){
        this.settingsPreferences = settingsPreferences
        viewModelScope.launch {
            settingsPreferences.getThemeSetting().collect{
                isDarkModeActive = it
            }
        }
    }

    fun getListUser(context: Context) {
        viewModelScope.launch {
            val listUser = JsonUtils.loadFromJSONFile("githubuser.json", context)
            prevListUser = listUser
            _listUser.emit(MainUtils.convertAvatarStringtoInt(listUser, context))
        }
    }

    fun getThemeSettings() : LiveData<Boolean> {
        return settingsPreferences.getThemeSetting().asLiveData()
    }

    fun saveThemeSettings(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            settingsPreferences.saveThemeSetting(isDarkModeActive)
        }
    }

    fun searchUser(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                _listUser.emit(prevListUser)
            } else {
                repository.searchUser(query).collect { result ->
                    when(result) {
                        is Resource.Loading -> {
                            _isLoading.emit(true)
                        }
                        is Resource.Success -> {
                            _isLoading.emit(false)
                            _listUser.emit(result.data as List<User>)
                        }
                        is Resource.Error -> {
                            _isLoading.emit(false)
                        }
                    }
                }
            }
        }
    }
}