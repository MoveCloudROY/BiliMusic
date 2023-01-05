package top.roy1994.bilimusic.data.objects.music

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MusicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMusics(vararg musics: MusicEntity)

    @Update
    suspend fun updateMusics(vararg musics: MusicEntity)

    @Delete
    suspend fun deleteMusics(vararg musics: MusicEntity)

    @Query("SELECT * FROM MusicEntity WHERE name = :name")
    suspend fun findMusicByName(name: String): List<MusicEntity>

    @Query("SELECT * FROM MusicEntity WHERE id = :id")
    suspend fun findMusicById(id: Int): List<MusicEntity>

    @Query("DELETE FROM MusicEntity WHERE name = :name")
    suspend fun deleteMusicByName(name: String)

    @Query("SELECT * FROM MusicEntity")
    fun loadAllMusics(): LiveData<List<MusicEntity>>
}