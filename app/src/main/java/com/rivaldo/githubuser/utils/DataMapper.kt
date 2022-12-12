package com.rivaldo.githubuser.utils

import com.rivaldo.githubuser.data.database.model.UserEntity
import com.rivaldo.githubuser.data.remote.model.ResponseFollowersItem
import com.rivaldo.githubuser.data.remote.model.ResponseFollowingItem
import com.rivaldo.githubuser.data.remote.model.UserItem
import com.rivaldo.githubuser.model.User

object DataMapper {

    fun List<UserEntity>.mapListUserEntityToListUser(): List<User> {
        val listUser = mutableListOf<User>()
        this.forEach {
            listUser.add(mapUserEntityToUser(it))
        }
        return listUser
    }

    fun List<UserItem>.mapToUser(): List<User> {
        val listUser = mutableListOf<User>()
        this.forEach {
            listUser.add(mapUserItemToUser(it))
        }
        return listUser
    }

    @JvmName("mapToUserResponseFollowersItem")
    fun List<ResponseFollowersItem>.mapToUser(): List<User>{
        val listUser = mutableListOf<User>()
        this.forEach {
            listUser.add(mapResponseFollowerItemToUser(it))
        }
        return listUser
    }

    @JvmName("mapToUserResponseFollowingItem")
    fun List<ResponseFollowingItem>.mapToUser(): List<User> {
        val listUser = mutableListOf<User>()
        this.forEach {
            listUser.add(mapResponseFollowingItemToUser(it))
        }
        return listUser
    }

    private fun mapUserItemToUser(userItem: UserItem): User {
        return User(
            username = userItem.login ?: "",
            avatar = userItem.avatarUrl ?: "",
            company = "",
            follower = 0,
            following = 0,
            location = "",
            name = userItem.login ?: "",
            avatarInt = null,
            repository = 0
        )
    }

    fun List<User>.mapToListUserEntity(): List<UserEntity> {
        val listUserEntity = mutableListOf<UserEntity>()
        this.forEach {
            listUserEntity.add(mapUserToUserEntity(it))
        }
        return listUserEntity
    }

    fun mapResponseFollowerItemToUser(responseFollowersItem: ResponseFollowersItem) : User {
        return User(
            username = responseFollowersItem.login ?: "",
            avatar = responseFollowersItem.avatarUrl ?: "",
            company = "",
            follower = 0,
            following = 0,
            location = "",
            name = responseFollowersItem.login ?: "",
            avatarInt = null,
            repository = 0
        )
    }

    fun mapResponseFollowingItemToUser(responseFollowingItem: ResponseFollowingItem) : User {
        return User(
            username = responseFollowingItem.login ?: "",
            avatar = responseFollowingItem.avatarUrl ?: "",
            company = "",
            follower = 0,
            following = 0,
            location = "",
            name = responseFollowingItem.login ?: "",
            avatarInt = null,
            repository = 0
        )

    }

    fun mapUserEntityToUser(userEntity: UserEntity): User {
        return User(
            username = userEntity.username,
            name = userEntity.name,
            avatar = userEntity.avatar,
            avatarInt = userEntity.avatarInt,
            company = userEntity.company,
            location = userEntity.location,
            repository = userEntity.repository,
            follower = userEntity.follower,
            following = userEntity.following,
        )
    }

    fun mapUserToUserEntity(user: User): UserEntity {
        return UserEntity(
            username = user.username,
            name = user.name,
            avatar = user.avatar,
            avatarInt = user.avatarInt,
            company = user.company,
            location = user.location,
            repository = user.repository,
            follower = user.follower,
            following = user.following,
        )
    }
}