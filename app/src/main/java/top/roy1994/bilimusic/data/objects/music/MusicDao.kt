package top.roy1994.bilimusic.data.objects.music

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MusicDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMusics(vararg musics: MusicEntity)

    @Update
    suspend fun updateMusics(vararg musics: MusicEntity)

    @Update(entity = MusicEntity::class)
    suspend fun updateMusicPlayCnt(vararg musicCntUpdate: MusicCntUpdate)

    @Delete
    suspend fun deleteMusics(vararg musics: MusicEntity)

    @Query("SELECT * FROM MusicEntity WHERE music_name = :name")
    suspend fun findMusicByName(name: String): List<MusicEntity>

    @Query("SELECT * FROM MusicEntity WHERE music_id = :id")
    suspend fun findMusicById(id: Int): List<MusicEntity>

    @Query("DELETE FROM MusicEntity WHERE music_name = :name")
    suspend fun deleteMusicByName(name: String)

    @Query("DELETE FROM MusicEntity WHERE music_id = :id")
    suspend fun deleteMusicById(id: Int)

    @Query("SELECT * FROM MusicEntity")
    fun loadAllMusics(): LiveData<List<MusicEntity>>

    @Query("SELECT * FROM MusicEntity ORDER BY MusicEntity.add_time DESC LIMIT 5")
    fun loadMusicsAddTimeDesc(): LiveData<List<MusicEntity>>

    @Query("SELECT * FROM MusicEntity ORDER BY MusicEntity.times5day DESC LIMIT 5")
    fun loadMusicsPlayTimesDesc(): LiveData<List<MusicEntity>>

    @Query("SELECT * FROM MusicEntity ORDER BY MusicEntity.last_play_time DESC LIMIT 5")
    fun loadMusicsLastPlayDesc(): LiveData<List<MusicEntity>>

    @Query("SELECT COUNT(*) FROM MusicEntity")
    fun count(): LiveData<Int>

}