package top.roy1994.bilimusic.data.objects.music

import androidx.compose.ui.graphics.painter.Painter
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class MusicEntity(
    @PrimaryKey(autoGenerate = true) var music_id: Int = 0,
    val bvid: String,
    val part: Int = 1,
    val cover_url: String? = null,

    val music_name: String,
    val music_artist: String = "None",
    val which_artist_id: Int = 1,
    val which_sheet_id: Int = 1,

    val second: Long = 300,
    var times5day: Int = 0,
    val add_time: Long = 0,
    var last_play_time: Long = 0,
) {
    @Ignore var music_cover: Painter? = null
    companion object {
        fun getEmpty(): MusicEntity {
            return MusicEntity(
                bvid = "",
                music_name = "",
                music_artist = "",
            )
        }
    }
}