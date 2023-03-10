package top.roy1994.bilimusic.data.objects.mixed

import androidx.room.Dao
import androidx.room.MapInfo
import androidx.room.Query
import top.roy1994.bilimusic.data.objects.artist.ArtistEntity
import top.roy1994.bilimusic.data.objects.music.MusicEntity

@Dao
interface MusicArtistDao {
    @MapInfo(keyColumn = "artist_id")
    @Query(
        "SELECT * FROM ArtistEntity " +
        "JOIN MusicEntity ON ArtistEntity.artist_id = MusicEntity.which_artist_id"
    )
    fun loadArtistAndMusics(): Map<Int, List<MusicEntity>>
}