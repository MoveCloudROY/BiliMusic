package top.roy1994.bilimusic.data.objects.music

import androidx.room.*

interface MusicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusics(vararg musics: MusicEntity)

    @Update
    fun updateMusics(vararg musics: MusicEntity)

    @Delete
    fun deleteMusics(vararg musics: MusicEntity)

    @Query("SELECT * FROM MusicEntity WHERE name = :name")
    fun findMusicByName(name: String): List<MusicEntity>

    @Query("DELETE FROM MusicEntity WHERE name = :name")
    fun deleteMusicByName(name: String)

    @Query("SELECT * FROM MusicEntity")
    fun loadAllMusics(): Array<MusicEntity>
}