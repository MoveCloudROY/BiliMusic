package top.roy1994.bilimusic.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import top.roy1994.bilimusic.data.objects.Music

class MusicHistoryViewModel: ViewModel() {
    val _5FirstHistory = mutableStateOf(
        (0 until 5).map {
            Music(
                id = it,
                name = "歌曲${it}",
                artist = "作者${it}",
            )
        }
    )
}


