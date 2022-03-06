package com.metehanbolat.nestedrelationshiproomdatabasecompose.repository

import com.metehanbolat.nestedrelationshiproomdatabasecompose.dao.UserDao
import com.metehanbolat.nestedrelationshiproomdatabasecompose.entity.*

class UserRepository(private val userDao: UserDao) {

    suspend fun addUser(item: List<User>) = userDao.insertUser(item)

    suspend fun addPlaylist(item: List<Playlist>) = userDao.insertPlaylist(item)

    suspend fun addSong(item: List<Song>) = userDao.insertSong(item)

    suspend fun addPlaylistSongCrossRef(item: List<PlaylistSongCrossRef>) = userDao.insertPlaylistSongCrossRef(item)

    fun getUsersWithPlaylistAndSongs(userId: Int): List<UserWithPlaylistAndSongs> = userDao.getUserWithPlaylistAndSongs(userId)
}