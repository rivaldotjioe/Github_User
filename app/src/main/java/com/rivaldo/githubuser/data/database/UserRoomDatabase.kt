package com.rivaldo.githubuser.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.rivaldo.githubuser.data.database.dao.UserDao
import com.rivaldo.githubuser.data.database.model.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserRoomDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context) : UserRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context,
                    UserRoomDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}