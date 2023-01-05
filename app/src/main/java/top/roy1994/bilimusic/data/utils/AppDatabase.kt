package top.roy1994.bilimusic.data.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import top.roy1994.bilimusic.data.objects.artist.ArtistDao
import top.roy1994.bilimusic.data.objects.artist.ArtistEntity
import top.roy1994.bilimusic.data.objects.mixed.MusicArtistDao
import top.roy1994.bilimusic.data.objects.mixed.MusicSheetDao
import top.roy1994.bilimusic.data.objects.music.MusicDao
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.objects.sheet.SheetDao
import top.roy1994.bilimusic.data.objects.sheet.SheetEntity


@Database(entities = [ArtistEntity::class, MusicEntity::class, SheetEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase()  {
    abstract fun artistDao(): ArtistDao
    abstract fun musicDao(): MusicDao
    abstract fun sheetDao(): SheetDao
    abstract fun musicArtistDao(): MusicArtistDao
    abstract fun musicSheetDao(): MusicSheetDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .createFromAsset("database/app_database.db")
                        .build()
//
//                    val sheetDao = instance.sheetDao()
//                    val sheetRepo = SheetRepo(sheetDao)
//                    if (null == sheetDao.count().value) {
//                        sheetRepo.insertSheet(
//                            SheetEntity(
//                                name = "默认歌单"
//                            )
//                        )
//                    }

                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}

