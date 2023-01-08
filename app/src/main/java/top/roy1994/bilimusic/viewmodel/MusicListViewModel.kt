package top.roy1994.bilimusic.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.coroutines.*
import top.roy1994.bilimusic.data.objects.Music
import top.roy1994.bilimusic.data.objects.biliapi.BiliService
import top.roy1994.bilimusic.data.objects.biliapi.BiliServiceCreator
import top.roy1994.bilimusic.data.objects.music.MusicDao
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.objects.sheet.SheetDao
import top.roy1994.bilimusic.data.utils.AppDatabase
import top.roy1994.bilimusic.data.utils.BiliRepo

class MusicListViewModel(application: Application): AndroidViewModel(application) {
    private val musicDao: MusicDao
    val biliRepo: BiliRepo
    val service: BiliService

    var listIndex = mutableStateOf(0)
        private set

    init {
        val appDb = AppDatabase.getInstance(application)
        musicDao = appDb.musicDao()
        service = BiliServiceCreator.getInstance()
        biliRepo = BiliRepo(service)
    }

    val musicList: LiveData<List<MusicEntity>> = musicDao.loadAllMusics()
    val coverMap: LiveData<List<String?>> =
        Transformations.switchMap(musicList) { l ->
            transformMap(l)
        }

    fun transformMap(l: List<MusicEntity>): LiveData<List<String?>> {
        return liveData {
            l.map{
                var ret: String? = null
                viewModelScope.launch (Dispatchers.IO) {
                    val res = service.getVideoInfo(it.bvid)
                    withContext(Dispatchers.Main) {
                        if (res.isSuccessful) {
                            ret = res.body()?.data?.pic
                        } else {
                            Log.e("BiliRepo", "Failed to get cover url")
                        }
                    }
                }
                Log.i("DATA", "bvid: ${it.bvid}; return: ${ret}")
                ret
            }
        }
    }

    fun getCoverUrl(bvid: String): String? {
        var ret: String? = null
        viewModelScope.launch(Dispatchers.IO) {
            val url = biliRepo.getCoverUrl(bvid).await()
            withContext(Dispatchers.Main) {
                ret = url
            }
        }

        Log.i("DATA", "bvid: $bvid; return: $ret")
        return ret
    }



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

