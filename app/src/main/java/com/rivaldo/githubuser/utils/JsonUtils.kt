package com.rivaldo.githubuser.utils

import android.content.Context
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.rivaldo.githubuser.model.GithubUsers
import com.rivaldo.githubuser.model.User
import java.io.IOException

object JsonUtils {

    fun loadFromJSONFile(fileName: String, context: Context) : List<User>{
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

        val listGithubUserType = object : TypeToken<GithubUsers>() {}.type
        return (Gson().fromJson(jsonString, listGithubUserType) as GithubUsers).users
    }
}