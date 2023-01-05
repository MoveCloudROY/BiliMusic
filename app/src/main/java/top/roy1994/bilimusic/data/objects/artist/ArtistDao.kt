package top.roy1994.bilimusic.data.objects.artist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArtistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArtists(vararg musics: ArtistEntity)

    @Update
    fun updateArtists(vararg musics: ArtistEntity)

    @Delete
    fun deleteArtists(vararg musics: ArtistEntity)

    @Query("SELECT * FROM ArtistEntity WHERE name = :name")
    fun findArtistByName(name: String): List<ArtistEntity>

    @Query("DELETE FROM ArtistEntity WHERE name = :name")
    fun deleteArtistByName(name: String)

    @Query("SELECT * FROM ArtistEntity")
    fun loadAllArtists(): LiveData<List<ArtistEntity>>
}