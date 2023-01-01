package top.roy1994.bilimusic.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import top.roy1994.bilimusic.data.struct.Music

class PlayerViewModel: ViewModel() {
    val preMusic: Music? = null

    val nowMusic: Music? = null
    val nxtMusic: Music? = null

    var isPlaying = mutableStateOf(false)
        private set


    fun updateIsPlaying(value: Boolean) {
        isPlaying.value = value
    }
}
