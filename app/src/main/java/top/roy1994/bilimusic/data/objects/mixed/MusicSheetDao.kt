package top.roy1994.bilimusic.data.objects.mixed

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.MapInfo
import androidx.room.Query
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.objects.sheet.SheetEntity

@Dao
interface MusicSheetDao {
    @MapInfo(keyColumn = "id")
    @Query(
        "SELECT * FROM SheetEntity " +
        "JOIN MusicEntity ON SheetEntity.id = MusicEntity.sheet_id"
    )
    fun loadSheetAndMusics(): Map<Int, List<MusicEntity>>

//    @MapInfo(keyColumn = "sheetId", valueColumn = [])
//    @Query(
//        "SELECT SheetEntity.id AS sheetId, FROM SheetEntity " +
//        "JOIN MusicEntity ON SheetEntity.id = MusicEntity.sheet_id"
//    )
//    fun loadSheetAndMusicNames(): Map<SheetEntity, List<MusicEntity>>
}