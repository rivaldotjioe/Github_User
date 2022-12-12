package com.rivaldo.githubuser.model


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class GithubUsers(
    val users: List<User>
) : Parcelable
