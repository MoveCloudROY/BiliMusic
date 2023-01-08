package top.roy1994.bilimusic.data.objects.artist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArtistDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertArtists(vararg artists: ArtistEntity)

    @Update
    fun updateArtists(vararg artists: ArtistEntity)

    @Delete
    fun deleteArtists(vararg artists: ArtistEntity)

    @Query("SELECT * FROM ArtistEntity WHERE artist_name = :name")
    fun findArtistByName(name: String): List<ArtistEntity>

    @Query("DELETE FROM ArtistEntity WHERE artist_name = :name")
    fun deleteArtistByName(name: String)

    @Query("SELECT * FROM ArtistEntity")
    fun loadAllArtists(): LiveData<List<ArtistEntity>>
}