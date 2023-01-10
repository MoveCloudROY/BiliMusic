package top.roy1994.bilimusic.viewmodel


import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.roy1994.bilimusic.data.objects.artist.ArtistDao
import top.roy1994.bilimusic.data.objects.artist.ArtistEntity
import top.roy1994.bilimusic.data.objects.biliapi.BiliService
import top.roy1994.bilimusic.data.objects.biliapi.BiliServiceCreator
import top.roy1994.bilimusic.data.objects.music.MusicDao
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.objects.sheet.SheetDao
import top.roy1994.bilimusic.data.objects.sheet.SheetEntity
import top.roy1994.bilimusic.data.utils.AppDatabase
import top.roy1994.bilimusic.data.utils.ArtistRepo
import top.roy1994.bilimusic.data.utils.BiliRepo
import kotlin.math.log

class AddMusicViewModel(application: Application): AndroidViewModel(application) {
    private val musicDao: MusicDao
    private val sheetDao: SheetDao
    private val artistDao: ArtistDao
    private val artistRepo: ArtistRepo
    private lateinit var biliRepo: BiliRepo
    private lateinit var service: BiliService

    init {
        val appDb = AppDatabase.getInstance(application)
        musicDao = appDb.musicDao()
        sheetDao = appDb.sheetDao()
        service = BiliServiceCreator.getInstance()
        biliRepo = BiliRepo(service)
        artistDao = appDb.artistDao()
        artistRepo = ArtistRepo(artistDao)
    }

    var bvid = mutableStateOf("")
    var part = mutableStateOf("1")
    var name = mutableStateOf("")
    var artist = mutableStateOf("默认艺术家")
    var sheet = mutableStateOf("默认歌单")

    var bvidError = mutableStateOf(false)
    var nameError = mutableStateOf(false)
    var artistError = mutableStateOf(false)
    var sheetError = mutableStateOf(false)

    var addSuccess = mutableStateOf(false)
        private set

    fun addMusic() {
        checkBvid(bvid.value)
        checkName(name.value)
        checkName(artist.value)
        if (bvidError.value or nameError.value or artistError.value)
            return
        viewModelScope.launch(Dispatchers.IO) {
            val seconds = biliRepo.getMusicInfo(bvid.value).await()
            val cover_url = biliRepo.getCoverUrl(bvid.value)
            val sheets = sheetDao.findSheetByName(sheet.value)
            val artistsTest = artistDao.findArtistByName(artist.value)
            if (artistsTest.isEmpty()) {
                artistRepo.insertArtist(
                    ArtistEntity(
                        artist_name = artist.value
                    )
                )
            }
            Log.i("AddMusicVM-addmusic", "cover_url: ${cover_url}")
            if (sheets.isNotEmpty() && seconds != null) {
                val sheetId = sheets[0].sheet_id
                musicDao.insertMusics(
                    MusicEntity(
                        bvid = bvid.value,
                        part = part.value.toInt(),
                        music_name = name.value,
                        music_artist = artist.value,
                        which_sheet_id = sheetId,
                        add_time = System.currentTimeMillis(),
                        last_play_time = System.currentTimeMillis(),
                        second = seconds,
                        cover_url = cover_url,
                    )
                )
            }
            bvid.value = ""
            part.value = "1"
            name.value = ""
            artist.value = "默认艺术家"
            sheet.value = "默认歌单"
        }
        addSuccess.value = true

    }

    fun checkBvid(bvid: String) {
        var p = false
        if (bvid.length != 12) p = true
        if (bvid.subSequence(0,2) != "BV" && bvid.subSequence(0,2) != "bv")
            p = true
        bvidError.value = p
    }
    fun checkName(name: String) {
        nameError.value = name.length >= 11
    }
    fun checkArtist(name: String) {
        artistError.value = name.length >= 11
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
     * @param name
     */
    fun updateArtist(_artist: String) {
        artist.value = _artist
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