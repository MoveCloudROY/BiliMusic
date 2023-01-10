package top.roy1994.bilimusic.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import top.roy1994.bilimusic.data.objects.TopShowBlockCategory
import top.roy1994.bilimusic.data.objects.artist.ArtistDao
import top.roy1994.bilimusic.data.objects.music.MusicDao
import top.roy1994.bilimusic.data.objects.sheet.SheetDao
import top.roy1994.bilimusic.data.utils.AppDatabase

class TopShowBlockViewModel(application: Application): AndroidViewModel(application) {

    private val musicDao: MusicDao
    private val sheetDao: SheetDao
    private val artistDao: ArtistDao
    init {
        val appDb = AppDatabase.getInstance(application)
        musicDao = appDb.musicDao()
        sheetDao = appDb.sheetDao()
        artistDao = appDb.artistDao()
    }

    val categories = mutableStateOf(
        listOf(
            TopShowBlockCategory("歌曲", 0),
            TopShowBlockCategory("歌单", 0),
            TopShowBlockCategory("标签", 0),
            TopShowBlockCategory("艺术家", 0),
        )
    )

    val musicCnt = musicDao.count()
    val sheetCnt = sheetDao.count()
    val tagCnt = liveData { emit(0) }
    val artistCnt = artistDao.count()

}

class TopShowBlockViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(TopShowBlockViewModel::class.java)) {
            return TopShowBlockViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}