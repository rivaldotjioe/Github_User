package com.rivaldo.githubuser.model


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class User(
    val username: String,
    val avatar: String,
    var avatarInt: Int? = null,
    val company: String,
    val follower: Int,
    val following: Int,
    val location: String,
    val name: String,
    val repository: Int,

) : Parcelable

val USER_KEY = "user"

