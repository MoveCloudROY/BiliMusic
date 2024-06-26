package top.roy1994.bilimusic.data.objects.music

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update


@Dao
interface MusicIncompleteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIncompRec(vararg musics: MusicIncompleteEntity)

    @Update
    suspend fun updateIncompRec(vararg musics: MusicIncompleteEntity)

    @Query("DELETE FROM MusicIncompleteEntity WHERE incomp_music_id = :id")
    suspend fun deleteIncompRec(vararg id: Int)

    @Query("SELECT EXISTS  (SELECT 1 FROM  MusicIncompleteEntity WHERE incomp_music_id = :id)")
    suspend fun hasIncompRec(vararg id: Int) : Boolean

    @Transaction
    @Query("SELECT * FROM MusicIncompleteEntity")
    fun QueryAllIncompRec(): LiveData<List<MusicIncompleteAllInfo>>

}