package top.roy1994.bilimusic.data.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import top.roy1994.bilimusic.data.objects.mixed.MusicArtistDao
import top.roy1994.bilimusic.data.objects.mixed.MusicSheetDao
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.objects.sheet.SheetDao
import top.roy1994.bilimusic.data.objects.sheet.SheetEntity

class MusicArtistRepo(
    private val musicArtistDao: MusicArtistDao,
) {
    val searchResults:  MutableLiveData<Map<Int, List<MusicEntity>>> = MutableLiveData()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    fun getMap(): Deferred<Map<Int, List<MusicEntity>>> =
        coroutineScope.async(Dispatchers.IO) {
            val ret = musicArtistDao.loadArtistAndMusics()
            withContext(Dispatchers.Main) {
                searchResults.value = ret
            }
            ret
        }

}