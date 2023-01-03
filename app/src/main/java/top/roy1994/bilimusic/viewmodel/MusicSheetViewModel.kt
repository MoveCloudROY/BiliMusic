package top.roy1994.bilimusic.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import top.roy1994.bilimusic.data.objects.Sheet

class MusicSheetViewModel: ViewModel() {
    val musicSheet = mutableStateOf(
        (1 until 8).map {
            Sheet(
                id = it,
                name = "歌单${it}",
                description = "desc ${it}"
            )
        }
    )
}


