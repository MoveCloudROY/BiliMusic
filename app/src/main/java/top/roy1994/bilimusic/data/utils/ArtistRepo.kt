package top.roy1994.bilimusic.data.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import top.roy1994.bilimusic.data.objects.artist.ArtistDao
import top.roy1994.bilimusic.data.objects.artist.ArtistEntity

class ArtistRepo(
    private val artistDao: ArtistDao,
) {
    val allSheets: LiveData<List<ArtistEntity>> = artistDao.loadAllArtists()
    val searchResults = MutableLiveData<List<ArtistEntity>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    fun insertArtist(newArtist: ArtistEntity) =
        coroutineScope.launch(Dispatchers.IO) {
            artistDao.insertArtists(newArtist)
        }


    fun deleteArtist(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            artistDao.deleteArtistByName(name)
        }
    }

    fun findArtistById(id: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindById(id).await()
        }
    }

    private fun asyncFindById(id: Int): Deferred<List<ArtistEntity>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async artistDao.findArtistById(id)
        }
}