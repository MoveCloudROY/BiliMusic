package top.roy1994.bilimusic.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.STATE_BUFFERING
import com.google.android.exoplayer2.Player.STATE_ENDED
import com.google.android.exoplayer2.Player.STATE_IDLE
import com.google.android.exoplayer2.Player.STATE_READY
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.ShuffleOrder
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.EventLogger
import kotlinx.coroutines.*
import top.roy1994.bilimusic.data.objects.biliapi.BiliService
import top.roy1994.bilimusic.data.objects.biliapi.BiliCreator
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.utils.BiliRepo

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
    var isMusicEnded = true //
    // ================================================================
    var exoPlayer: ExoPlayer

    private var service: BiliService
    private var biliRepo: BiliRepo
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private var playingList: MutableList<MusicEntity> = mutableListOf()
    // =========================================================

    fun prepareMusic(music: MusicEntity) {
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


    fun addMusicToPlayList(music: MusicEntity) {
        exoPlayer.pause()
        preMusic.value = nowMusic.value
        nowMusic.value = music
        nxtMusic.value = MusicEntity.getEmpty()
        resetProgressBar(music.second)
        prepareMusic(music)

        playingList.add(music)
        playingId = playingList.size - 1;

        exoPlayer.apply {
            prepare()
            play()
        }
        updateIsPlaying(true)
    }


    fun setPlayList(musicList: List<MusicEntity>) {
        exoPlayer.pause()
        exoPlayer.clearMediaItems()
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

        preMusic.value = MusicEntity.getEmpty()
        nowMusic.value = if(playingList.size > 0) playingList[0] else MusicEntity.getEmpty()
        nxtMusic.value = if(playingList.size > 1) playingList[1] else MusicEntity.getEmpty()
        if (playingList.size > 0) {
            prepareMusic(playingList[0])
            playingId = 0;
            exoPlayer.apply {
                prepare()
                play()
            }
            updateIsPlaying(true)
        }

    }

    fun setPlayerShuffle() {
        exoPlayer.setShuffleOrder(
            ShuffleOrder.DefaultShuffleOrder(
                exoPlayer.mediaItemCount, System.currentTimeMillis()))
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
    fun previous() {

        val pid = playingId - 1;
        if (pid >= 0) {
            exoPlayer.apply {
                pause()
            }
            nxtMusic.value = nowMusic.value
            nowMusic.value = preMusic.value
            preMusic.value = if (pid > 0)
                playingList[pid-1] else MusicEntity.getEmpty()
            playingId = pid;

            prepareMusic(nowMusic.value)

            exoPlayer.apply {
                prepare()
                play()
            }
            updateIsPlaying(true);
        }
    }
    //   4 - 3
    // 0 1 2 3
    fun next() {
        val nid = playingId+1;
        if (nid < playingList.size) {
            exoPlayer.apply {
                pause()
            }
            preMusic.value = nowMusic.value
            nowMusic.value = nxtMusic.value
            nxtMusic.value = if (nid < playingList.size - 1)
                playingList[nid+1] else MusicEntity.getEmpty()
            playingId = nid;
            prepareMusic(nowMusic.value)

            exoPlayer.apply{
                prepare()
                play()
            }
            updateIsPlaying(true);
        }
    }

    fun clear() {
        playingId = -1;
        playingList.clear();
    }

    fun registerListener() {

        exoPlayer.addListener(
            object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    when (playbackState) {
                        STATE_IDLE -> {

                        }
                        STATE_BUFFERING -> {

                        }
                        STATE_READY-> {
                            isMusicEnded = false
                        }

                        STATE_ENDED -> {
                            if (!isMusicEnded) {
                                next()
                                isMusicEnded = true;
                            }
                        }
                    }
                }
            }
        )
    }


    init {
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
                        playedSize = exoPlayer.currentPosition / 1000
                    }
                    if (playedSize < nowMusic.value.second!!) {
                        withContext(Dispatchers.Main) {
                            playedSeconds.value = playedSize
                            playedPercentage.value =
                                (playedSize.toFloat() / nowMusic.value.second!!) * 100f
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            playedSeconds.value = nowMusic.value.second
                            playedPercentage.value = 100f
                        }
                        break
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
    fun updateIsPlaying(value: Boolean) {
        isPlaying.value = value
    }
    fun resetProgressBar(second: Long) {
        updatePlayedSeconds(0)
        updateTotalSeconds(second)
        playedSeconds.value = 0
        playedPercentage.value = 0.0f

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