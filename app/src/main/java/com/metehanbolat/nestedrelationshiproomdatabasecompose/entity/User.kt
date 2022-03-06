package com.metehanbolat.nestedrelationshiproomdatabasecompose.entity

import androidx.room.*

@Entity
data class User(
    @PrimaryKey
    val userId: Int,
    val name: String,
    val age: Int
)

@Entity
data class PlayList(
    @PrimaryKey
    val playListId: Int,
    val userCreatorId: Int,
    val playlistName: String
)

@Entity
data class Song(
    @PrimaryKey
    val songId: Int,
    val songName: String,
    val artist: String
)

@Entity(primaryKeys = ["playlistId", "songId"])
data class PlayListSongCrossRef(
    val playlistId: Int,
    val songId: Int
)

data class PlaylistWithSongs(
    @Embedded val playlist: PlayList,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "songId",
        associateBy = Junction(PlayListSongCrossRef::class)
    )
    val song: List<Song>
)

data class UserWithPlaylistAndSongs(
    @Embedded val user: User,
    @Relation(
        entity = PlayList::class,
        parentColumn = "userId",
        entityColumn = "userCreatorId"
    )
    val playlist: List<PlaylistWithSongs>
)