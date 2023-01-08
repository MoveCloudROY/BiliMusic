package top.roy1994.bilimusic.data.objects.sheet

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SheetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSheets(vararg sheets: SheetEntity)

    @Update
    fun updateSheets(vararg sheets: SheetEntity)

    @Delete
    fun deleteSheets(vararg sheets: SheetEntity)

    @Query("SELECT * FROM SheetEntity WHERE sheet_name = :name")
    fun findSheetByName(name: String): List<SheetEntity>

    @Query("SELECT * FROM SheetEntity WHERE sheet_id = :id")
    fun findSheetById(id: Int): List<SheetEntity>

    @Query("DELETE FROM SheetEntity WHERE sheet_name = :name")
    fun deleteSheetByName(name: String)

    @Query("SELECT * FROM SheetEntity")
    fun loadAllSheets(): LiveData<List<SheetEntity>>

    @Query("SELECT COUNT(*) FROM SheetEntity")
    fun count(): LiveData<Int>
}