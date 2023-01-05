package top.roy1994.bilimusic.viewmodel


import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import top.roy1994.bilimusic.data.objects.Music
import top.roy1994.bilimusic.data.objects.music.MusicDao
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.objects.sheet.SheetDao
import top.roy1994.bilimusic.data.objects.sheet.SheetEntity
import top.roy1994.bilimusic.data.utils.AppDatabase
import top.roy1994.bilimusic.data.utils.MusicRepo
import top.roy1994.bilimusic.data.utils.SheetRepo

class AddMusicViewModel(application: Application): AndroidViewModel(application) {
    private val musicDao: MusicDao
    private val sheetDao: SheetDao

    init {
        val appDb = AppDatabase.getInstance(application)
        musicDao = appDb.musicDao()
        sheetDao = appDb.sheetDao()
    }

    var bvid = mutableStateOf("")
    var part = mutableStateOf("1")
    var name = mutableStateOf("")
    var sheet = mutableStateOf("默认歌单")

    var addSuccess = mutableStateOf(false)
        private set

    fun addMusic() {
        viewModelScope.launch(Dispatchers.IO) {
            val sheets: List<SheetEntity> =
                    sheetDao.findSheetByName(sheet.value)
            if (sheets.isNotEmpty()) {
                val sheetId = sheets[0].id
                musicDao.insertMusics(
                    MusicEntity(
                        bvid = bvid.value,
                        part = part.value.toInt(),
                        name = name.value,
                        sheet_id = sheetId,
                    )
                )
            }
            bvid.value = ""
            part.value = "1"
            name.value = ""
            sheet.value = "默认歌单"
        }
        addSuccess.value = true

    }

    /**
     * 更新分类下标
     *
     * @param bvid
     */
    fun updateBvid(_bvid: String) {
        bvid.value = _bvid
    }
    /**    java.lang.RuntimeException: Unable to copy database file.
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

    fun updateStatus(_state: Boolean) {
        addSuccess.value = _state
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