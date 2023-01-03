package top.roy1994.bilimusic.data.utils

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import top.roy1994.bilimusic.data.objects.artist.ArtistDao
import top.roy1994.bilimusic.data.objects.mixed.MusicArtistDao
import top.roy1994.bilimusic.data.objects.mixed.MusicSheetDao
import top.roy1994.bilimusic.data.objects.music.MusicDao
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.objects.sheet.SheetDao

class AppRepository(
    private val musicDao: MusicDao,
    private val sheetDao: SheetDao,
    private val artistDao: ArtistDao,
    private val musicArtistDao: MusicArtistDao,
    private val musicSheetDao: MusicSheetDao,
) {
    val searchResults = MutableLiveData<List<MusicEntity>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertMusic(newMusic: MusicEntity) {
        coroutineScope.launch(Dispatchers.IO) {
            musicDao.insertMusics(newMusic)
        }
    }

    fun deleteMusic(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            musicDao.deleteMusicByName(name)
        }
    }

    fun findMusic(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }

    private fun asyncFind(name: String): Deferred<List<MusicEntity>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async musicDao.findMusicByName(name)
        }
}