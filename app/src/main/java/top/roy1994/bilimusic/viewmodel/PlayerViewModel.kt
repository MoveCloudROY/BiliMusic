package top.roy1994.bilimusic.viewmodel

import android.app.Application
import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_BUFFERING
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.common.Player.STATE_IDLE
import androidx.media3.common.Player.STATE_READY
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.util.EventLogger
import androidx.media3.extractor.DefaultExtractorsFactory

import kotlinx.coroutines.*
import top.roy1994.bilimusic.data.objects.biliapi.BiliService
import top.roy1994.bilimusic.data.objects.biliapi.BiliCreator
import top.roy1994.bilimusic.data.objects.music.MusicCntUpdate
import top.roy1994.bilimusic.data.objects.music.MusicDao
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.objects.music.MusicIncompleteDao
import top.roy1994.bilimusic.data.objects.music.MusicIncompleteEntity
import top.roy1994.bilimusic.data.objects.music.MusicLastPlayTimeUpdate
import top.roy1994.bilimusic.data.utils.AppDatabase
import top.roy1994.bilimusic.data.utils.BiliRepo
import kotlin.math.min

class PlayerViewModel(application: Application): AndroidViewModel(application) {
    val preMusic = mutableStateOf(
        MusicEntity.getEmpty()
    )
    val nowMusic = mutableStateOf(
        MusicEntity.getEmpty()
    )
    val nxtMusic = mutableStateOf(
        MusicEntity.getEmpty()
    )

    var playedPercentage = MutableLiveData<Float>(0.0f)
    var playedSeconds = mutableStateOf<Long>(0)
        private set
    var totalSeconds = mutableStateOf<Long>(300)
        private set

    var isPlaying = mutableStateOf(false)
        private set

    var playingId = -1;
    var isMusicEnded = true
    var isNewPlay = false
    var startPer : Float = -1f
    // ================================================================
    var exoPlayer: ExoPlayer

    private var service: BiliService
    private val musicDao: MusicDao
    private val incompteDao: MusicIncompleteDao
    private var biliRepo: BiliRepo
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private var playingList: MutableList<MusicEntity> = mutableListOf()
    // =========================================================

    @OptIn(UnstableApi::class)
    fun prepareMusic(music: MusicEntity) {
        Log.i("Player", "Invoke `Func prepareMusic()")
        val bvid = music.bvid

        coroutineScope.launch(Dispatchers.IO) {
            val url = biliRepo.getMusicUrl(bvid).await()
            withContext(Dispatchers.Main) {
                if (url != null) {
                    val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
                        .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.54")
                        .setDefaultRequestProperties(hashMapOf("Referer" to "https://www.bilibili.com/video/${bvid}"))

                    exoPlayer.setMediaSource(
                        ProgressiveMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(MediaItem.fromUri(url))
                    )
                }
            }
        }
    }


    fun addMusicToPlayList(music: MusicEntity, playedPer: Float = 0f) {
        Log.i("Player", "Invoke `Func addMusicToPlayList()")
        if (playingList.contains(music)) {
            val item = playingList.indexOf(music)
            playAt(item, playedPer)
            return
        }
        playingList.add(music)
        playAt(playingList.size - 1, playedPer)
    }

    fun setPlayList(musicList: List<MusicEntity>, start: Int = 0) {
        Log.i("Player", "Invoke `Func setPlayList()")
        if (start < 0 || start >= musicList.size)
            return

        exoPlayer.pause()
        clear()

        resetProgressBar(nowMusic.value.second)

        for (e in musicList) {
            playingList.add(e)
            Log.i(
                "Player",
                """
                    id:     ${e.bvid}
                    name:   ${e.music_name}
                    length: ${e.second}
                    state:  ${exoPlayer.currentMediaItem.toString()}
                """.trimIndent()
            )
        }

        preMusic.value = if (start == 0) MusicEntity.getEmpty() else playingList[start-1]
        nowMusic.value = if(playingList.size > start) playingList[start] else MusicEntity.getEmpty()
        nxtMusic.value = if(playingList.size > start+1) playingList[start+1] else MusicEntity.getEmpty()
        if (playingList.size > 0) {
            prepareMusic(playingList[0])
            playingId = 0
            playImpl()
        }

    }

    fun setPlayerShuffle() {
//        exoPlayer.setShuffleOrder(
//            ShuffleOrder.DefaultShuffleOrder(
//                exoPlayer.mediaItemCount, System.currentTimeMillis()))
        exoPlayer.shuffleModeEnabled = true
    }

    fun setPlayerRepeatOne() {
        exoPlayer.shuffleModeEnabled = false
        exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
    }

    fun setPlayerRepeatAll() {
        exoPlayer.shuffleModeEnabled = false
        exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
    }

    fun isEmptyMusic() : Boolean {
        return nowMusic.value.music_id == 0
    }

    fun previous() {
        Log.i("Player", "Invoke `Func previous()")
        val pid = playingId - 1;
        if (pid >= 0) {
            playAt(pid);
        }
    }
    //   4 - 3
    // 0 1 2 3
    fun next() {
        Log.i("Player", "Invoke `Func next()")
        val nid = playingId+1;
        if (nid < playingList.size) {
            playAt(nid)
        }
    }

    fun clear() {
        Log.i("Player", "Invoke `Func clear()")
        playingId = -1;
        playingList.clear();
    }


    private fun playImpl(playedPer: Float = 0f) {
        Log.i("Player", "Invoke `Func playImpl()")
        prepareMusic(nowMusic.value)

        exoPlayer.apply{
            prepare()
            play()
        }
        startPer = playedPer
        isNewPlay = true
        viewModelScope.launch(Dispatchers.IO) {
            musicDao.updateMusicLastPlayTime(
                MusicLastPlayTimeUpdate(
                    nowMusic.value.music_id,
                    System.currentTimeMillis()
                )
            )
        }

        setPlayState(true);

    }

    private fun playAt(playListId: Int, playedPer: Float = 0f) {
        Log.i("Player", "Invoke `Func playAt()")
        if(playListId < 0 || playListId >= playingList.size) {
            return
        }
        if (exoPlayer.isPlaying || (playedPercentage.value!! > 1 && playedPercentage.value!! < 99)) {
            recordIncompMusic(nowMusic.value)
        }
        exoPlayer.pause()
        preMusic.value = if (playListId > 0) playingList[playListId-1] else MusicEntity.getEmpty()
        nowMusic.value = playingList[playListId]
        nxtMusic.value = if (playListId < playingList.size-1) playingList[playListId+1] else MusicEntity.getEmpty()
        playingId = playListId;

        resetProgressBar(nowMusic.value.second)
        playImpl(playedPer)
    }

    fun resetProgressBar(second: Long) {
        Log.i("Player", "Invoke `Func resetProgressBar()")
        playedPercentage.value = 0.0f
        updatePlayedSeconds(0)
        updateTotalSeconds(second)
    }

    fun setProgressPercentage(per: Float) {
        Log.i("Player", "Invoke `Func setProgressPercentage()")
        playedPercentage.value = per * 100
        playedSeconds.value = (per * nowMusic.value.second).toLong()
        Log.i("Player", """
            [setProgressPercentage] SetProgress
                nowMusic:   ${nowMusic.value.music_name}
                per:        $per
                sec:        ${nowMusic.value.second}
                playedPer:  ${playedPercentage.value}
                playedSec:  ${playedSeconds.value}
            """.trimIndent())

        if(per > 1e-4) {// 避免杂音
            Log.i("Player", """
                [setProgressPercentage]
                    SeekTo ${per * nowMusic.value.second}
            """.trimIndent())
            exoPlayer.seekTo((per * nowMusic.value.second * 1000).toLong())
        }
    }

    private fun recordIncompMusic(music: MusicEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            playedPercentage.value?.let {
                if (incompteDao.hasIncompRec(music.music_id)) {
                    incompteDao.updateIncompRec(
                        MusicIncompleteEntity(
                            music.music_id, it / 100f
                        )
                    )
                } else {
                    incompteDao.insertIncompRec(
                        MusicIncompleteEntity(
                            music.music_id, it / 100f
                        )
                    )
                }
            }
        }
    }

    private fun registerListener() {

        exoPlayer.addListener(
            object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    when (playbackState) {
                        STATE_IDLE -> {

                        }
                        STATE_BUFFERING -> {
                            isMusicEnded = false
                        }
                        STATE_READY-> {
                            isMusicEnded = false
                            if (startPer > 0f) {
                                setProgressPercentage(startPer)
                                startPer = -1f
                            }
                        }

                        STATE_ENDED -> {
                            if (!isMusicEnded) {
                                var delete_music = nowMusic.value;
                                viewModelScope.launch(Dispatchers.IO) {

                                    Log.i("Player", """
                                        Delete music
                                            name:   ${delete_music.music_name}
                                            id:     ${delete_music.music_id}
                                            
                                    """.trimIndent())
                                    incompteDao.deleteIncompRec(delete_music.music_id)

                                    musicDao.updateMusicPlayCnt(MusicCntUpdate(
                                        delete_music.music_id,
                                        delete_music.times5day+1
                                    ))
                                }
                                next()
                                isMusicEnded = true;
                            }
                        }
                    }
                }

                override fun onPositionDiscontinuity(
                    oldPosition: Player.PositionInfo,
                    newPosition: Player.PositionInfo,
                    reason: Int
                ) {
                    super.onPositionDiscontinuity(oldPosition, newPosition, reason)
                    Log.i("Player", """
                        SeekTo Result:
                            oldPosition:    ${oldPosition.positionMs}
                            newPosition:    ${newPosition.positionMs}
                    """.trimIndent())
                }
            }
        )
    }


    init {
        val appDb = AppDatabase.getInstance(application)
        musicDao = appDb.musicDao()
        incompteDao = appDb.musicIncomleteDao()
        service = BiliCreator.getServiceInstance()
        biliRepo = BiliCreator.getInstance()
        exoPlayer = ExoPlayer.Builder(application)
            .build()
            .apply {
                playWhenReady = false
            }
        registerListener()
        exoPlayer.addAnalyticsListener(EventLogger())

    }


    fun startThreadGradient() {
        var totalSize: Long
        var playedSize: Long = 0

        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                totalSize = nowMusic.value.second
                updatePlayedSeconds(0)
                updateTotalSeconds(totalSize)
                playedPercentage.value = 0.0f
                while (exoPlayer.isLoading) delay(50)
            }
            withContext(Dispatchers.Default) {

                while (true) {
//                    if (isPlaying.value) {
//                        playedSize += 1
//                    }
//                    Log.i("DATA", "${playedSeconds.value}  |  ${totalSeconds.value}")

                    withContext(Dispatchers.Main) {
                        playedSize = if (isNewPlay) playedSeconds.value else
                            exoPlayer.currentPosition / 1000
                        isNewPlay = false
                    }
                    if (playedSize < nowMusic.value.second!!) {
                        withContext(Dispatchers.Main) {
                            playedSeconds.value = playedSize
                            playedPercentage.value =
                                (playedSize.toFloat() / nowMusic.value.second!!) * 100f
//                            Log.i("Player", """
//                                [BarWatch] Still in
//                                    name:   ${nowMusic.value.music_name}
//                                    sec:        ${nowMusic.value.second}
//                                    playedPer:  ${playedPercentage.value}
//                                    playedSec:  ${playedSeconds.value}
//                                """.trimIndent())
                        }
                    } else {
                        withContext(Dispatchers.Main) {
//                            Log.i("Player", """
//                                [BarWatch] Edge
//                                    name:   ${nowMusic.value.music_name}
//                                    sec:        ${nowMusic.value.second}
//                                    playedPer:  ${playedPercentage.value}
//                                    playedSec:  ${playedSeconds.value}
//                            """.trimIndent())
                            playedSeconds.value = nowMusic.value.second
                            playedPercentage.value = 100f
                        }
                    }

                    delay(1000)
                }
            }
        }
    }

    fun updatePlayedSeconds(value: Long) {
        playedSeconds.value = value
    }
    fun updateTotalSeconds(value: Long) {
        totalSeconds.value = value
    }
    fun setPlayState(value: Boolean) {
        isPlaying.value = value
    }

}

class PlayerViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(PlayerViewModel::class.java)) {
            return PlayerViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}