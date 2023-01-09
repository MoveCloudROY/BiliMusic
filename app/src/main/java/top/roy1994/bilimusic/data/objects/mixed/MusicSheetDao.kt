package top.roy1994.bilimusic.data.objects.mixed

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.MapInfo
import androidx.room.Query
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.objects.sheet.SheetEntity

@Dao
interface MusicSheetDao {
    @MapInfo(keyColumn = "sheet_id")
    @Query(
        "SELECT * FROM SheetEntity " +
        "JOIN MusicEntity ON SheetEntity.sheet_id = MusicEntity.which_sheet_id"
    )
    fun loadSheetAndMusics(): Map<Int, List<MusicEntity>>
}