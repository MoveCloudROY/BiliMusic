package top.roy1994.bilimusic.data.objects.sheet

import androidx.room.*

interface SheetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSheets(vararg musics: SheetEntity)

    @Update
    fun updateSheets(vararg musics: SheetEntity)

    @Delete
    fun deleteSheets(vararg musics: SheetEntity)

    @Query("SELECT * FROM SheetEntity")
    fun loadAllSheets(): Array<SheetEntity>
}