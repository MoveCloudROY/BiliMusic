package top.roy1994.bilimusic.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import top.roy1994.bilimusic.data.struct.TopShowBlockCategory

class TopShowBlockViewModel: ViewModel() {
    val categories = mutableStateOf(
        listOf(
            TopShowBlockCategory("歌曲", 0),
            TopShowBlockCategory("歌单", 0),
            TopShowBlockCategory("标签", 0),
            TopShowBlockCategory("艺术家", 0),
        )
    )
}