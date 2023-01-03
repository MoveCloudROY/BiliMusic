package top.roy1994.bilimusic.data.objects.artist

import androidx.room.*

interface ArtistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArtists(vararg musics: ArtistEntity)

    @Update
    fun updateArtists(vararg musics: ArtistEntity)

    @Delete
    fun deleteArtists(vararg musics: ArtistEntity)

    @Query("SELECT * FROM ArtistEntity")
    fun loadAllArtists(): Array<ArtistEntity>
}