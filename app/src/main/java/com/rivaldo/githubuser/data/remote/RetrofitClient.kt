package com.rivaldo.githubuser.data.remote

import android.util.Log
import com.rivaldo.githubuser.data.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private fun getRetrofit(): Retrofit {
        Log.d("RetrofitClient", "getRetrofit: "+Constant.API_URL)
        return Retrofit.Builder()
            .baseUrl(Constant.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}