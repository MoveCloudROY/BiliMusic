package top.roy1994.bilimusic.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import top.roy1994.bilimusic.data.objects.artist.ArtistDao
import top.roy1994.bilimusic.data.objects.artist.ArtistEntity
import top.roy1994.bilimusic.data.objects.mixed.MusicArtistDao
import top.roy1994.bilimusic.data.objects.mixed.MusicSheetDao
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.objects.sheet.SheetDao
import top.roy1994.bilimusic.data.objects.sheet.SheetEntity
import top.roy1994.bilimusic.data.utils.*

class ArtistDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val musicArtistDao: MusicArtistDao
    private val artistDao: ArtistDao
    private val musicArtistRepo: MusicArtistRepo
    private val artistRepo: ArtistRepo
    private var artistId = mutableStateOf(1)
        private set

    init {
        val appDb = AppDatabase.getInstance(application)
        musicArtistDao = appDb.musicArtistDao()
        artistDao = appDb.artistDao()
        musicArtistRepo = MusicArtistRepo(musicArtistDao)
        artistRepo = ArtistRepo(artistDao)
    }
    val artistInfo: MutableLiveData<ArtistEntity> = MutableLiveData()
    val artistElems = mutableStateOf<List<MusicEntity>>(listOf())


    fun updateArtistId(value: Int) {
        artistId.value = value
        artistRepo.findArtistById(artistId.value)
        artistInfo.value = artistRepo.searchResults.value?.get(0)
            ?: ArtistEntity.getEmpty()
        viewModelScope.launch(Dispatchers.Main) {
            musicArtistRepo.getMap().await()
        }
        artistElems.value = musicArtistRepo.searchResults.value?.get(artistId.value)
            ?: listOf()
    }
}

class ArtistDetailViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(ArtistDetailViewModel::class.java)) {
            return ArtistDetailViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}