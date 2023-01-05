package top.roy1994.bilimusic.data.objects.sheet

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SheetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSheets(vararg musics: SheetEntity)

    @Update
    fun updateSheets(vararg musics: SheetEntity)

    @Delete
    fun deleteSheets(vararg musics: SheetEntity)

    @Query("SELECT * FROM SheetEntity WHERE name = :name")
    fun findSheetByName(name: String): List<SheetEntity>

    @Query("DELETE FROM SheetEntity WHERE name = :name")
    fun deleteSheetByName(name: String)

    @Query("SELECT * FROM SheetEntity")
    fun loadAllSheets(): LiveData<List<SheetEntity>>
}