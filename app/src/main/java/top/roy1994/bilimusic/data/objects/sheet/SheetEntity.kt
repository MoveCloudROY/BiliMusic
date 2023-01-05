package top.roy1994.bilimusic.data.objects.sheet

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SheetEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
)