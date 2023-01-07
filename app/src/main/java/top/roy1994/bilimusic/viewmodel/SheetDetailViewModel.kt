package top.roy1994.bilimusic.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import top.roy1994.bilimusic.data.objects.mixed.MusicSheetDao
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.objects.sheet.SheetDao
import top.roy1994.bilimusic.data.objects.sheet.SheetEntity
import top.roy1994.bilimusic.data.utils.AppDatabase
import top.roy1994.bilimusic.data.utils.MusicSheetRepo
import top.roy1994.bilimusic.data.utils.SheetRepo

class SheetDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val musicSheetDao: MusicSheetDao
    private val sheetDao: SheetDao
    private val musicSheetRepo: MusicSheetRepo
    private val sheetRepo: SheetRepo
    private var sheetId = mutableStateOf(1)
        private set

    init {
        val appDb = AppDatabase.getInstance(application)
        musicSheetDao = appDb.musicSheetDao()
        sheetDao = appDb.sheetDao()
        musicSheetRepo = MusicSheetRepo(musicSheetDao)
        sheetRepo = SheetRepo(sheetDao)
    }
    val sheetInfo: MutableLiveData<SheetEntity> = MutableLiveData()
    val sheetElems = mutableStateOf<List<MusicEntity>>(listOf())


    fun updateSheetId(value: Int) {
        sheetId.value = value
        sheetRepo.findSheetById(sheetId.value)
        sheetInfo.value = sheetRepo.searchResults.value?.get(0)
            ?: SheetEntity.GetDefault()
        viewModelScope.launch(Dispatchers.Main) {
            musicSheetRepo.getMap()
        }
        sheetElems.value = musicSheetRepo.searchResults.value?.get(sheetId.value)
            ?: listOf()
    }
}

class SheetDetailViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(SheetDetailViewModel::class.java)) {
            return SheetDetailViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}