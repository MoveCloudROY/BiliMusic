package top.roy1994.bilimusic.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import top.roy1994.bilimusic.data.objects.Music

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