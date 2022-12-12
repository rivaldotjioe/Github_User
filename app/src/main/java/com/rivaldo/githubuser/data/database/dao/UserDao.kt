package com.rivaldo.githubuser.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rivaldo.githubuser.data.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)

    @Query("SELECT * FROM user")
    fun getAllUser(): Flow<List<UserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username)")
    fun getUserIsExist(username: String): Boolean
}