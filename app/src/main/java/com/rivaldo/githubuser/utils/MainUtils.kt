package com.rivaldo.githubuser.utils

import android.content.Context
import com.rivaldo.githubuser.model.User

object MainUtils {
    fun convertAvatarStringtoInt(listUser: List<User>, context: Context) : List<User>{
        listUser.forEach {
            it.avatarInt = context.resources.getIdentifier(it.avatar, "drawable", context.packageName)
        }
       return listUser
    }
}