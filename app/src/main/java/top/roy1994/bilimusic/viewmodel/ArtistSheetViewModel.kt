package top.roy1994.bilimusic.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import top.roy1994.bilimusic.data.objects.Sheet
import top.roy1994.bilimusic.data.objects.artist.ArtistDao
import top.roy1994.bilimusic.data.objects.artist.ArtistEntity
import top.roy1994.bilimusic.data.objects.music.MusicDao
import top.roy1994.bilimusic.data.objects.sheet.SheetDao
import top.roy1994.bilimusic.data.objects.sheet.SheetEntity
import top.roy1994.bilimusic.data.utils.AppDatabase

class ArtistSheetViewModel(application: Application): AndroidViewModel(application) {
    private val artistDao: ArtistDao

    init {
        val appDb = AppDatabase.getInstance(application)
        artistDao = appDb.artistDao()
    }
    val artistSheet: LiveData<List<ArtistEntity>> = artistDao.loadAllArtists()

}

class ArtistSheetViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(ArtistSheetViewModel::class.java)) {
            return ArtistSheetViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
