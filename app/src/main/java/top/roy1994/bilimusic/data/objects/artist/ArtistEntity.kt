package top.roy1994.bilimusic.data.objects.artist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArtistEntity (
    @PrimaryKey(autoGenerate = true) val artist_id: Int = 0,
    val artist_name: String,
)