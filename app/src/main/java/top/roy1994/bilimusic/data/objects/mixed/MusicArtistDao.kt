package top.roy1994.bilimusic.data.objects.mixed

import androidx.room.Query
import top.roy1994.bilimusic.data.objects.artist.ArtistEntity
import top.roy1994.bilimusic.data.objects.music.MusicEntity

interface MusicArtistDao {
    @Query(
        "SELECT * FROM ArtistEntity " +
        "JOIN MusicEntity ON ArtistEntity.id = MusicEntity.sheet_id"
    )
    fun loadArtistAndMusics(): Map<ArtistEntity, List<MusicEntity>>
}