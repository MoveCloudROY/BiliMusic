package top.roy1994.bilimusic.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import top.roy1994.bilimusic.data.objects.mixed.MusicSheetDao
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.objects.sheet.SheetDao
import top.roy1994.bilimusic.data.objects.sheet.SheetEntity
import top.roy1994.bilimusic.data.utils.AppDatabase

class SheetDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val musicSheetDao: MusicSheetDao

    init {
        val appDb = AppDatabase.getInstance(application)
        musicSheetDao = appDb.musicSheetDao()
    }
    val musicSheetMap: LiveData<Map<SheetEntity, List<MusicEntity>>> = musicSheetDao.loadSheetAndMusics()
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