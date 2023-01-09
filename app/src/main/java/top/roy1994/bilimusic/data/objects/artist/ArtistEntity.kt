package top.roy1994.bilimusic.data.objects.artist

import androidx.room.Entity
import androidx.room.PrimaryKey
import top.roy1994.bilimusic.data.objects.music.MusicEntity

@Entity
data class ArtistEntity (
    @PrimaryKey(autoGenerate = true) val artist_id: Int = 0,
    val artist_name: String,
){
    companion object {
        fun getEmpty(): ArtistEntity {
            return ArtistEntity(
                artist_name = "",
            )
        }
    }
}