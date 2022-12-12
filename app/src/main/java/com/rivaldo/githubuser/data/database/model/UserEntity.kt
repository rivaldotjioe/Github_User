package com.rivaldo.githubuser.data.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "avatar")
    val avatar: String,
    @ColumnInfo(name = "avatarInt")
    var avatarInt: Int? = null,
    @ColumnInfo(name = "company")
    val company: String,
    @ColumnInfo(name = "follower")
    val follower: Int,
    @ColumnInfo(name = "following")
    val following: Int,
    @ColumnInfo(name = "location")
    val location: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "repository")
    val repository: Int,
) : Parcelable