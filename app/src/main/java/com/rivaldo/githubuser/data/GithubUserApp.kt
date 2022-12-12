package com.rivaldo.githubuser.data

import android.app.Application
import android.content.Context
import com.rivaldo.githubuser.di.application
import com.rivaldo.githubuser.di.networkModule
import com.rivaldo.githubuser.di.repository
import com.rivaldo.githubuser.di.viewModelModule
import org.koin.core.context.startKoin

class GithubUserApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance : GithubUserApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun getInstance(): GithubUserApp {
            return instance!!
        }
    }



    override fun onCreate() {
        super.onCreate()
        instance = this
        val context : Context = GithubUserApp.applicationContext()
        startKoin {
            modules(
                listOf(
                    application,
                    networkModule,
                    repository,
                    viewModelModule
                )
            )
        }

    }
}