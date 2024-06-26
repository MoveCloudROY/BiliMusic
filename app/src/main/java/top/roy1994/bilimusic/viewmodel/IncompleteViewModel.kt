package top.roy1994.bilimusic.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import kotlinx.coroutines.*
import top.roy1994.bilimusic.data.objects.biliapi.BiliService
import top.roy1994.bilimusic.data.objects.biliapi.BiliCreator
import top.roy1994.bilimusic.data.objects.music.MusicDao
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.objects.music.MusicIncompleteAllInfo
import top.roy1994.bilimusic.data.objects.music.MusicIncompleteDao
import top.roy1994.bilimusic.data.utils.AppDatabase
import top.roy1994.bilimusic.data.utils.BiliRepo

class IncompleteViewModel(application: Application): AndroidViewModel(application) {
//    private val musicDao: MusicDao
    private val incompDao: MusicIncompleteDao
    val biliRepo: BiliRepo
    val service: BiliService


    var listIndex = mutableStateOf(-1)
        private set

    init {
        val appDb = AppDatabase.getInstance(application)
//        musicDao = appDb.musicDao()
        incompDao = appDb.musicIncomleteDao()
        service = BiliCreator.getServiceInstance()
        biliRepo = BiliRepo(service)
    }

    val incompList: LiveData<List<MusicIncompleteAllInfo>> = incompDao.QueryAllIncompRec()
//    val coverMap: LiveData<List<String?>> =
//        incompList.switchMap { l ->
//            val p = transformMap(l)
//            Log.i("Incomplete-DATA-coverMap", "${p}")
//            p
//        }
//
//
//    private fun transformMap(l: List<MusicEntity>): LiveData<List<String?>> {
//        return liveData(Dispatchers.IO) {
//            emit(l.map{
//                var ret: String? = null
//                withContext(viewModelScope.coroutineContext) {
//                    val url = biliRepo.getCoverUrl(it.bvid)
//                    ret = url
//                    url
//                }
//                ret
//            }.toList())
//        }
//
//    }

//    fun getCoverUrl(bvid: String): String? {
//        var ret: String? = null
//        viewModelScope.launch {
//            val url = biliRepo.getCoverUrl(bvid)
//            ret = url
//        }
//        Log.i("Incomplete-getCoverUrl", "bvid: $bvid; return: $ret")
//        return ret
//    }

    fun deleteMusic(music_id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            incompDao.deleteIncompRec(music_id)
        }
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

class IncompleteViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(IncompleteViewModel::class.java)) {
            return IncompleteViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

