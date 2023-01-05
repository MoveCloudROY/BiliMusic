package top.roy1994.bilimusic.viewmodel


import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import top.roy1994.bilimusic.data.objects.Music
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.utils.AppDatabase
import top.roy1994.bilimusic.data.utils.MusicRepo
import top.roy1994.bilimusic.data.utils.SheetRepo

class AddMusicViewModel(application: Application) : AndroidViewModel(application) {
    private val musicRepo: MusicRepo
    private val sheetRepo: SheetRepo

    init {
        val appDb = AppDatabase.getInstance(application)
        val musicDao = appDb.musicDao()
        val sheetDao = appDb.sheetDao()
        musicRepo = MusicRepo(musicDao)
        sheetRepo = SheetRepo(sheetDao)
    }

    var bvid = mutableStateOf("")
    var part = mutableStateOf("1")
    var name = mutableStateOf("")
    var sheet = mutableStateOf("默认歌单")

    fun addMusic() {
        sheetRepo.findSheet(sheet.value)
        var sheet_id = sheetRepo.searchResults.value!![0].id

        musicRepo.insertMusic(
            MusicEntity(
                bvid = bvid.value,
                part = part.value.toInt(),
                name = name.value,
                sheet_id = sheet_id,
            )
        )
    }

    /**
     * 更新分类下标
     *
     * @param bvid
     */
    fun updateBvid(_bvid: String) {
        bvid.value = _bvid
    }
    /**
     * 更新分类下标
     *
     * @param part
     */
    fun updatePart(_part: String) {
        part.value = _part
    }
    /**
     * 更新分类下标
     *
     * @param name
     */
    fun updateName(_name: String) {
        name.value = _name
    }
    /**
     * 更新分类下标
     *
     * @param sheet
     */
    fun updateSheet(_sheet: String) {
        sheet.value = _sheet
    }
}


class AddMusicViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(AddMusicViewModel::class.java)) {
            return AddMusicViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}