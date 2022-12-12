package com.rivaldo.githubuser.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.rivaldo.githubuser.data.Constant
import com.rivaldo.githubuser.data.GithubUserApp
import com.rivaldo.githubuser.data.remote.ApiService
import com.rivaldo.githubuser.data.repository.UserRepository
import com.rivaldo.githubuser.ui.detail.fragment.follower.FollowerViewModel
import com.rivaldo.githubuser.ui.detail.fragment.following.FollowingViewModel
import com.rivaldo.githubuser.ui.detail.viewmodel.DetailViewModel
import com.rivaldo.githubuser.ui.favorite.viewmodel.FavoriteViewModel
import com.rivaldo.githubuser.ui.main.viewmodel.MainViewModel
import com.rivaldo.githubuser.utils.SettingsPreferences
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { FollowerViewModel(get()) }
    viewModel { FollowingViewModel(get()) }
}

val application = module {
    factory<Application> { GithubUserApp.Companion.getInstance() }
}

val networkModule = module {
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repository = module {
    single { UserRepository(GithubUserApp.Companion.getInstance(), get()) }
}


