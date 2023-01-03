package top.roy1994.bilimusic.data.objects

import androidx.compose.ui.graphics.painter.Painter


data class Music(
    val id: Int = -1,
    val bvid: String? = null,
    val part: Int? = null,
    val cover: Painter? = null,
    val audio_url: String = "",
    val cover_url: String = "",
    val name: String = "name",
    val artist: String = "artist",

//    val minute: Int = 0,
    val second: Int = 300,
    val times5day: Int = 0,
)
