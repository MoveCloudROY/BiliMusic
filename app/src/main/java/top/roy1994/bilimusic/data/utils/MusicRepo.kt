package top.roy1994.bilimusic.data.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import top.roy1994.bilimusic.data.objects.artist.ArtistDao
import top.roy1994.bilimusic.data.objects.mixed.MusicArtistDao
import top.roy1994.bilimusic.data.objects.mixed.MusicSheetDao
import top.roy1994.bilimusic.data.objects.music.MusicDao
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.objects.sheet.SheetDao

class MusicRepo(
    private val musicDao: MusicDao,
) {

    val allMusics: LiveData<List<MusicEntity>> = musicDao.loadAllMusics()
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

    fun findMusicByName(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindByName(name).await()
        }
    }

    private fun asyncFindByName(name: String): Deferred<List<MusicEntity>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async musicDao.findMusicByName(name)
        }

    fun findMusicById(id: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindById(id).await()
        }
    }

    private fun asyncFindById(id: Int): Deferred<List<MusicEntity>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async musicDao.findMusicById(id)
        }
}