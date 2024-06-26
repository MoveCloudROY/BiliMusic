package top.roy1994.bilimusic.data.objects.music

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class MusicIncompleteEntity (
    @PrimaryKey var incomp_music_id: Int = 0,
    var progress: Float = 0.0f,
){}

data class MusicIncompleteAllInfo (
    @Embedded val musicIncomplete: MusicIncompleteEntity,
    @Relation(
        parentColumn = "incomp_music_id",
        entityColumn = "music_id"
    )
    val music: MusicEntity
)