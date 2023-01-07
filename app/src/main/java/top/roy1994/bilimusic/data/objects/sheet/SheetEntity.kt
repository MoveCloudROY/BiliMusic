package top.roy1994.bilimusic.data.objects.sheet

import androidx.compose.ui.graphics.painter.Painter
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.FileDescriptor

@Entity
data class SheetEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String = " ",
) {
    @Ignore
    var cover: Painter? = null
    companion object {
        fun GetDefault(): SheetEntity {
            return SheetEntity(
                id = 1,
                name = "默认歌单Test",
                description = " ",
            )
        }
    }
}