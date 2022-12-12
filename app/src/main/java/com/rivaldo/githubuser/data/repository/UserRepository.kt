package com.rivaldo.githubuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.rivaldo.githubuser.data.Resource
import com.rivaldo.githubuser.data.database.UserRoomDatabase
import com.rivaldo.githubuser.data.database.dao.UserDao
import com.rivaldo.githubuser.data.database.model.UserEntity
import com.rivaldo.githubuser.data.remote.ApiService
import com.rivaldo.githubuser.data.remote.model.*
import com.rivaldo.githubuser.model.User
import com.rivaldo.githubuser.utils.DataMapper.mapToUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application, val apiService: ApiService) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application.applicationContext)
        mUserDao = db.userDao()
    }

    fun getAllUser(): Flow<List<UserEntity>> = mUserDao.getAllUser()

    fun insertUserFavorite(user: UserEntity) {
        executorService.execute { mUserDao.insert(user) }
    }

    fun deleteUserFavorite(user: UserEntity) {
        executorService.execute { mUserDao.delete(user) }
    }

    fun checkIsFavorite(username: String): Boolean {
        val user = mUserDao.getUserIsExist(username)
        return user
    }

    fun searchUser(query: String): Flow<Resource<List<User>>> {
        return flow {
            emit(Resource.Loading(data = null))
            try {
                val responseQuery = apiService.searchUser(query)
                if ((responseQuery.totalCount ?: 0) > 0) {
                    val listUser = responseQuery.items as List<UserItem>
                    emit(Resource.Success(data = listUser.mapToUser()))
                } else {
                    emit(Resource.Error(data = null, message = "User not found"))
                }
            } catch (e: java.lang.Exception) {
                emit(Resource.Error(data = null, message = e.message ?: "Error Occurred!"))
            }

        }
    }

    fun getUserDetail(username: String): Flow<Resource<ResponseDetailUser>> {
        return channelFlow {
            send(Resource.Loading(data = null))
            try {
                val response = apiService.getDetailUser(username)
                if(response != null) {
                    send(Resource.Success(data = response))
                } else {
                    send(Resource.Error(data = null, message = "User not found"))
                }
            } catch (e: java.lang.Exception) {
                send(Resource.Error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }
    }

    fun getFollowerByUsername(username: String) : Flow<Resource<List<ResponseFollowersItem>>> {
        return flow{
            emit(Resource.Loading(data = null))
            try{
                val response = apiService.getListFollower(username)
                if ((response.size ?: 0) > 0) {
                    emit(Resource.Success(data = response as List<ResponseFollowersItem>))
                } else {
                    emit(Resource.Error(message = "No List Found"))
                }
            } catch (exception: java.lang.Exception) {
                emit(Resource.Error(message = exception.message.toString()))
            }
        }
    }

    fun getFollowingByUsername(username: String) : Flow<Resource<List<ResponseFollowingItem>>> {
        return flow{
            emit(Resource.Loading(data = null))
            try{
                val response = apiService.getListFollowing(username)
                if ((response.size ?: 0) > 0) {
                    emit(Resource.Success(data = response as List<ResponseFollowingItem>))
                } else {
                    emit(Resource.Error(message = "No List Found"))
                }
            } catch (exception: java.lang.Exception) {
                emit(Resource.Error(message = exception.message.toString()))
            }
        }
    }


}