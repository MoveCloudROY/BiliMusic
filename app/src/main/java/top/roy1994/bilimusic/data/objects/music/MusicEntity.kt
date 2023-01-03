package top.roy1994.bilimusic.data.objects.music

import androidx.compose.ui.graphics.painter.Painter
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class MusicEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val bvid: String,
    val part: Int = 1,
    @Ignore val cover: Painter? = null,
    val audio_url: String,
    val cover_url: String,
    val name: String,
    val artist: String = "None",
    val sheet_id: Int = 0,

    val second: Int = 300,
    val times5day: Int = 0,
)