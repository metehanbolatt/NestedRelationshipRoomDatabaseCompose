package com.metehanbolat.nestedrelationshiproomdatabasecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.metehanbolat.nestedrelationshiproomdatabasecompose.entity.*
import com.metehanbolat.nestedrelationshiproomdatabasecompose.ui.theme.NestedRelationshipRoomDatabaseComposeTheme
import com.metehanbolat.nestedrelationshiproomdatabasecompose.ui.theme.Purple500
import com.metehanbolat.nestedrelationshiproomdatabasecompose.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NestedRelationshipRoomDatabaseComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NestedDb()
                }
            }
        }
    }
}

val userData = listOf(
    User(1, "User 1", 10),
    User(2, "User 2", 11),
    User(3, "User 3", 12),
    User(4, "User 4", 13),
    User(5, "User 5", 14),
)

val playlistData = listOf(
    Playlist(1, 1, "Playlist 1"),
    Playlist(2, 2, "Playlist 2"),
    Playlist(3, 4, "Playlist 3"),
    Playlist(4, 2, "Playlist 4"),
    Playlist(5, 3, "Playlist 5"),
    Playlist(6, 2, "Playlist 6"),
    Playlist(7, 4, "Playlist 7"),
)

val songData = listOf(
    Song(1, "Song 1", "Artist 1"),
    Song(2, "Song 2", "Artist 2"),
    Song(3, "Song 3", "Artist 3"),
    Song(4, "Song 4", "Artist 4"),
    Song(5, "Song 5", "Artist 5"),
    Song(6, "Song 6", "Artist 6"),
    Song(7, "Song 7", "Artist 7"),
)

val playlistSongCrossRefData = listOf(
    PlaylistSongCrossRef(1, 1),
    PlaylistSongCrossRef(1, 3),
    PlaylistSongCrossRef(2, 4),
    PlaylistSongCrossRef(1, 5),
    PlaylistSongCrossRef(3, 4),
    PlaylistSongCrossRef(4, 3),
    PlaylistSongCrossRef(5, 5),
    PlaylistSongCrossRef(2, 7),
)

@Composable
fun NestedDb() {
    val scope = rememberCoroutineScope()
    val userViewModel: UserViewModel = viewModel()


    userViewModel.addUser(userData)
    userViewModel.addPlaylist(playlistData)
    userViewModel.addSong(songData)
    userViewModel.addPlaylistSongCrossRef(playlistSongCrossRefData)

    val getUserPlaylistWithSong = userViewModel.readAllData.observeAsState(listOf()).value


    val userId = remember { mutableStateOf("") }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Purple500)
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Nested Relationship",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = userId.value,
                    onValueChange = { userId.value = it },
                    label = { Text(text = "Enter User Id") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )

                Spacer(modifier = Modifier.height(25.dp))

                Button(
                    onClick = {
                        scope.launch {
                            userViewModel.getUser(userId.value.toInt())
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(60.dp)
                        .padding(10.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(Purple500)
                ) {
                    Text(
                        text = "Submit",
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Purple500)
                        .padding(15.dp)
                ) {
                    Text(
                        text = "User Id",
                        modifier = Modifier.weight(0.3f),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Name",
                        modifier = Modifier.weight(0.3f),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Age",
                        modifier = Modifier.weight(0.3f),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (getUserPlaylistWithSong.isNotEmpty()) {
                    LazyColumn {
                        items(getUserPlaylistWithSong.size) { index ->
                            UserDataList(getUserPlaylistWithSong[index])
                        }
                        
                        item { 
                            Spacer(modifier = Modifier.height(30.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Purple500)
                                    .padding(15.dp)
                            ) {
                                Text(
                                    text = "Playlist Id",
                                    modifier = Modifier.weight(0.3f),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Playlist Name",
                                    modifier = Modifier.weight(0.3f),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        items(getUserPlaylistWithSong[0].playlist.size) { index ->
                            PlaylistDataList(getUserPlaylistWithSong[0].playlist[index])
                        }

                        item {
                            Spacer(modifier = Modifier.height(30.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Purple500)
                                    .padding(15.dp)
                            ) {
                                Text(
                                    text = "Song Id",
                                    modifier = Modifier.weight(0.3f),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Song Name",
                                    modifier = Modifier.weight(0.3f),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Artist Name",
                                    modifier = Modifier.weight(0.3f),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        items(getUserPlaylistWithSong[0].playlist[0].song.size) { index ->
                            SongDataList((getUserPlaylistWithSong[0].playlist[0].song[index]))


                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SongDataList(song: Song) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Purple500)
            .padding(15.dp)
    ) {
        Text(
            text = song.songId.toString(),
            modifier = Modifier.weight(0.3f),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = song.songName,
            modifier = Modifier.weight(0.3f),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = song.artist,
            modifier = Modifier.weight(0.3f),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun UserDataList(userWithPlaylistAndSongs: UserWithPlaylistAndSongs) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Purple500)
            .padding(15.dp)
    ) {
        Text(
            text = userWithPlaylistAndSongs.user.userId.toString(),
            modifier = Modifier.weight(0.3f),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = userWithPlaylistAndSongs.user.name,
            modifier = Modifier.weight(0.3f),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = userWithPlaylistAndSongs.user.age.toString(),
            modifier = Modifier.weight(0.3f),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PlaylistDataList(playlistWithSongs: PlaylistWithSongs) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Purple500)
            .padding(15.dp)
    ) {
        Text(
            text = playlistWithSongs.playlist.playlistId.toString(),
            modifier = Modifier.weight(0.3f),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = playlistWithSongs.playlist.playlistName,
            modifier = Modifier.weight(0.3f),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}