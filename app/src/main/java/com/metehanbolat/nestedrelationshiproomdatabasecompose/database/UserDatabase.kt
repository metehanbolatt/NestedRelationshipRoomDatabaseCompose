package com.metehanbolat.nestedrelationshiproomdatabasecompose.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.metehanbolat.nestedrelationshiproomdatabasecompose.dao.UserDao
import com.metehanbolat.nestedrelationshiproomdatabasecompose.entity.Playlist
import com.metehanbolat.nestedrelationshiproomdatabasecompose.entity.PlayListSongCrossRef
import com.metehanbolat.nestedrelationshiproomdatabasecompose.entity.Song
import com.metehanbolat.nestedrelationshiproomdatabasecompose.entity.User

@Database(entities = [User::class, Playlist::class, Song::class, PlayListSongCrossRef::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            val userInstance = INSTANCE
            if (userInstance != null) return userInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).allowMainThreadQueries().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}