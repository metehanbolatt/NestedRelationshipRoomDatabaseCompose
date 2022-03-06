package com.metehanbolat.nestedrelationshiproomdatabasecompose.dao

import androidx.room.*
import com.metehanbolat.nestedrelationshiproomdatabasecompose.entity.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(item: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(item: List<Playlist>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(item: List<Song>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistSongCrossRef(item: List<PlayListSongCrossRef>)

    @Transaction
    @Query("SELECT * FROM User WHERE userId = :userId")
    fun getUserWithPlaylistAndSongs(userId: Int): List<UserWithPlaylistAndSongs>
}