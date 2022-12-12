package com.rivaldo.githubuser.data.remote

import com.rivaldo.githubuser.data.remote.model.*
import retrofit2.http.*
import retrofit2.http.Path

interface ApiService {

    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username") username: String) : ResponseDetailUser

    @GET("search/users")
    suspend fun searchUser(@Query("q") username: String) : ResponseSearchUser

    @GET("users/{username}/followers")
    suspend fun getListFollower(@Path("username") username: String) : List<ResponseFollowersItem>

    @GET("users/{username}/following")
    suspend fun getListFollowing(@Path("username") username: String) : List<ResponseFollowingItem>
}