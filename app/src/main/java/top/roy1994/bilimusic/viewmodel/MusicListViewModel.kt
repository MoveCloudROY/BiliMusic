package top.roy1994.bilimusic.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import top.roy1994.bilimusic.R
import top.roy1994.bilimusic.data.struct.Music
import top.roy1994.bilimusic.data.struct.Sheet
import top.roy1994.bilimusic.data.struct.TopSelectBarCategory

class MusicListViewModel: ViewModel() {
    val musicList = mutableStateOf(
        (0 until 30).map {
            Music(
                id = it,
                name = "歌曲${it}",
                artist = "作者${it}",
            )
        }
    )

    var listIndex = mutableStateOf(0)
        private set

    /**
     * 更新分类下标
     *
     * @param index
     */
    fun updateListIndex(index: Int) {
        listIndex.value = index
    }
}


