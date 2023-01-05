package top.roy1994.bilimusic.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import top.roy1994.bilimusic.data.objects.Music
import top.roy1994.bilimusic.data.objects.music.MusicDao
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.objects.sheet.SheetDao
import top.roy1994.bilimusic.data.utils.AppDatabase

class MusicListViewModel(application: Application): AndroidViewModel(application) {
    private val musicDao: MusicDao


    var listIndex = mutableStateOf(0)
        private set

    init {
        val appDb = AppDatabase.getInstance(application)
        musicDao = appDb.musicDao()
    }

    val musicList: LiveData<List<MusicEntity>> = musicDao.loadAllMusics()
//        mutableStateOf(
//        (0 until 30).map {
//            Music(
//                id = it,
//                name = "歌曲${it}",
//                artist = "作者${it}",
//            )
//        }
//    )



    /**
     * 更新分类下标
     *
     * @param index
     */
    fun updateListIndex(index: Int) {
        listIndex.value = index
    }
}

class MusicListViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MusicListViewModel::class.java)) {
            return MusicListViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

