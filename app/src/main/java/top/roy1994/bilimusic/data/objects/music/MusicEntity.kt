package top.roy1994.bilimusic.data.objects.music

import androidx.compose.ui.graphics.painter.Painter
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class MusicEntity (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val bvid: String,
    val part: Int = 1,

    val name: String,
    val description: String = " ",
    val artist: String = "None",
    val sheet_id: Int = 0,

    val second: Int = 300,
    var times5day: Int = 0,
    val add_time: Long = 0,
    var last_play_time: Long,
) {
    @Ignore var cover: Painter? = null
}