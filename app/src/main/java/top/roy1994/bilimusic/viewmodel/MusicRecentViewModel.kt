package top.roy1994.bilimusic.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import top.roy1994.bilimusic.data.objects.Music
import top.roy1994.bilimusic.data.objects.music.MusicDao
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.utils.AppDatabase

class MusicRecentViewModel(application: Application): AndroidViewModel(application) {
    val _5FirstRecent = mutableStateOf(
        (0 until 5).map {
            Music(
                id = it,
                name = "歌曲${it}",
                artist = "作者${it}",
            )
        }
    )
    private val musicDao: MusicDao

    init {
        val appDb = AppDatabase.getInstance(application)
        musicDao = appDb.musicDao()
    }

    val musicRecent: LiveData<List<MusicEntity>> = musicDao.loadMusicsAddTimeDesc()
}

class MusicRecentViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MusicRecentViewModel::class.java)) {
            return MusicRecentViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

