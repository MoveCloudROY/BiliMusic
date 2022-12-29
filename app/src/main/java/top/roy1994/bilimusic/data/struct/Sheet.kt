package top.roy1994.bilimusic.data.struct

import androidx.compose.ui.graphics.painter.Painter

data class Sheet (
    val id: Int = -1,
    val name: String,
    val cover: Painter? = null,
    val description: String = "",
    val songs: List<Int> = emptyList(),
)