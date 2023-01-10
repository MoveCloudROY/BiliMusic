package top.roy1994.bilimusic.viewmodel


import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
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
import top.roy1994.bilimusic.data.utils.MusicRepo
import kotlin.math.log

class MusicConfigViewModel(application: Application): AndroidViewModel(application) {
    private val musicDao: MusicDao
    private val musicRepo: MusicRepo
    private val sheetDao: SheetDao
    private val artistDao: ArtistDao
    private val artistRepo: ArtistRepo
    private lateinit var biliRepo: BiliRepo
    private lateinit var service: BiliService

    var musicId = mutableStateOf(0)
    var bvid = mutableStateOf("")
    var name = mutableStateOf("")
    var artist = mutableStateOf("默认艺术家")
    var sheet = mutableStateOf("默认歌单")
    var nowSheetId = mutableStateOf(1)
    var nowMusicEntity = mutableStateOf(MusicEntity.getEmpty())

    var bvidError = mutableStateOf(false)
    var nameError = mutableStateOf(false)
    var artistError = mutableStateOf(false)
    var sheetError = mutableStateOf(false)

    var addSuccess = mutableStateOf(false)
        private set

    init {
        val appDb = AppDatabase.getInstance(application)
        musicDao = appDb.musicDao()
        sheetDao = appDb.sheetDao()
        artistDao = appDb.artistDao()
        service = BiliServiceCreator.getInstance()
        biliRepo = BiliRepo(service)
        artistRepo = ArtistRepo(artistDao)
        musicRepo = MusicRepo(musicDao)
    }
    fun modifyMusic() {
//        checkBvid(bvid.value)
        checkName(name.value)
        checkName(artist.value)
        if (nameError.value or artistError.value)
            return
        viewModelScope.launch(Dispatchers.IO) {
//            val seconds = biliRepo.getMusicInfo(bvid.value).await()
//            val cover_url = biliRepo.getCoverUrl(bvid.value)
            val sheets = sheetDao.findSheetByName(sheet.value)
            val artistsTest = artistDao.findArtistByName(artist.value)
            if (artistsTest.isEmpty()) {
                artistRepo.insertArtist(
                    ArtistEntity(
                        artist_name = artist.value
                    )
                )
            }
            val nowArtists = artistDao.findArtistByName(artist.value)[0]
//            Log.i("AddMusicVM-addmusic", "cover_url: ${cover_url}")
            if (sheets.isNotEmpty()) {
                val sheetId = sheets[0].sheet_id
                musicDao.updateMusics(
                    MusicEntity(
                        music_id = nowMusicEntity.value.music_id,
                        bvid = nowMusicEntity.value.bvid,
                        part = 1,
                        cover_url = nowMusicEntity.value.cover_url,
                        music_name = name.value,
                        music_artist = artist.value,
                        which_artist_id = nowArtists.artist_id,
                        which_sheet_id = sheetId,
                        second = nowMusicEntity.value.second,
                        times5day = nowMusicEntity.value.times5day,
                        add_time = nowMusicEntity.value.add_time,
                        last_play_time = nowMusicEntity.value.last_play_time,
                    )
                )
            }
            bvid.value = ""
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
    fun updateMusicId(id: Int) {
        musicId.value = id
        viewModelScope.launch {
            val musicEntity = withContext(Dispatchers.IO) {
                musicDao.findMusicById(id)
            }[0]
            nowMusicEntity.value = musicEntity
            name.value = musicEntity.music_name
            artist.value = musicEntity.music_artist
            nowSheetId.value = musicEntity.which_sheet_id
            val sheetEntity = withContext(Dispatchers.IO) {
                sheetDao.findSheetById(nowSheetId.value)
            }[0]
            sheet.value = sheetEntity.sheet_name
        }

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

class MusicConfigViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MusicConfigViewModel::class.java)) {
            return MusicConfigViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}