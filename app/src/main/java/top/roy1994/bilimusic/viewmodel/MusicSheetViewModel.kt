package top.roy1994.bilimusic.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import top.roy1994.bilimusic.data.objects.Sheet
import top.roy1994.bilimusic.data.objects.music.MusicDao
import top.roy1994.bilimusic.data.objects.sheet.SheetDao
import top.roy1994.bilimusic.data.objects.sheet.SheetEntity
import top.roy1994.bilimusic.data.utils.AppDatabase

class MusicSheetViewModel(application: Application): AndroidViewModel(application) {
    private val sheetDao: SheetDao

    init {
        val appDb = AppDatabase.getInstance(application)
        sheetDao = appDb.sheetDao()
    }
    val musicSheet: LiveData<List<SheetEntity>> = sheetDao.loadAllSheets()
//    val musicSheet = mutableStateOf(
//        (1 until 8).map {
//            Sheet(
//                id = it,
//                name = "歌单${it}",
//                description = "desc ${it}"
//            )
//        }
//    )
}

class MusicSheetViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MusicSheetViewModel::class.java)) {
            return MusicSheetViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
