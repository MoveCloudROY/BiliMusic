package top.roy1994.bilimusic.data.objects.sheet

import androidx.compose.ui.graphics.painter.Painter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.FileDescriptor

@Entity
data class SheetEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sheet_id")
    val sheet_id: Int = 0,
    @ColumnInfo(name = "sheet_name")
    val sheet_name: String,
    @ColumnInfo(name = "sheet_description")
    val sheet_description: String = " ",
) {
    @Ignore
    var cover: Painter? = null
    companion object {
        fun GetDefault(): SheetEntity {
            return SheetEntity(
                sheet_id = 1,
                sheet_name = "默认歌单Test",
                sheet_description = " ",
            )
        }
    }
}