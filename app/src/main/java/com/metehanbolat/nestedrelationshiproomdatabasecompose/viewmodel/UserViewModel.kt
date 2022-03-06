package com.metehanbolat.nestedrelationshiproomdatabasecompose.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.metehanbolat.nestedrelationshiproomdatabasecompose.database.UserDatabase
import com.metehanbolat.nestedrelationshiproomdatabasecompose.entity.*
import com.metehanbolat.nestedrelationshiproomdatabasecompose.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val _readAllData = MutableLiveData<List<UserWithPlaylistAndSongs>>()
    var readAllData: LiveData<List<UserWithPlaylistAndSongs>> = _readAllData

    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getInstance(application).userDao()
        repository = UserRepository(userDao)
    }

    fun getUser(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _readAllData.postValue(repository.getUsersWithPlaylistAndSongs(userId))
        }
    }

    fun addUser(item: List<User>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(item)
        }
    }

    fun addPlaylist(item: List<Playlist>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPlaylist(item)
        }
    }

    fun addSong(item: List<Song>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSong(item)
        }
    }

    fun addPlaylistSongCrossRef(item: List<PlaylistSongCrossRef>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPlaylistSongCrossRef(item)
        }
    }

}