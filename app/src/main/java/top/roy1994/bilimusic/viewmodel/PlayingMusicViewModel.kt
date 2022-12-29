package top.roy1994.bilimusic.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import top.roy1994.bilimusic.R
import top.roy1994.bilimusic.data.struct.Music

class PlayingMusicViewModel: ViewModel() {
    val playingMusic = mutableStateOf(
        Music(
            id = -1,
            cover = null,
            name = "No Music",
            artist = "No Music Artist",
        )
    )
}