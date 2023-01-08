package top.roy1994.bilimusic

import androidx.room.Dao
import androidx.room.Room
import androidx.test.espresso.internal.inject.InstrumentationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import top.roy1994.bilimusic.data.objects.music.MusicDao
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.utils.AppDatabase
import top.roy1994.bilimusic.data.utils.MusicRepo
import top.roy1994.bilimusic.data.utils.SheetRepo
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var musicDao: MusicDao
    private lateinit var db: AppDatabase
    private lateinit var musicRepo: MusicRepo

    @Before
    fun createDb() {
        val context = InstrumentationRegistry
            .getInstrumentation()
            .targetContext
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        musicDao = db.musicDao()
    }
    @After
    @Throws(IOException::class)
    fun deleteDb() {
        db.close()
    }
    @Test
    @Throws(Exception::class)
    fun insertAndGetMusic() = runBlocking {
        val musicElem = MusicEntity (
            bvid = "test",
            part = 1,
            music_name= "test name",
            last_play_time = System.currentTimeMillis(),
        )
        musicDao.insertMusics(musicElem)
        val getElem = musicDao.findMusicByName("test name")
        assertEquals(getElem[0].music_name, "test name")
    }
}