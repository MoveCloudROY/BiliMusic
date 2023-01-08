package top.roy1994.bilimusic.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.*
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import kotlinx.coroutines.*
import top.roy1994.bilimusic.data.objects.Music
import top.roy1994.bilimusic.data.objects.biliapi.BiliService
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
    var playedSeconds = MutableLiveData<Int>(0)
        private set
    var totalSeconds = MutableLiveData<Int>(nowMusic.value.second)
        private set

    var isPlaying = mutableStateOf(false)
        private set
    var exoPlayer: ExoPlayer

    lateinit var service: BiliService
    lateinit var biliRepo: BiliRepo
    val coroutineScope = CoroutineScope(Dispatchers.Main)



    fun addMusicToPlayList(music: MusicEntity) {
        val bvid = music.bvid

        coroutineScope.launch(Dispatchers.IO) {
            val url = biliRepo.getMusicUrl(bvid).await()
            withContext(Dispatchers.Main) {
                if (url != null) {
                    val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
                        .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.54")
                        .setDefaultRequestProperties(hashMapOf("Referer" to "https://www.bilibili.com/video/${bvid}"))
                    exoPlayer.addMediaSource(
                        ProgressiveMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(MediaItem.fromUri(url))
                    )
                }
            }
        }
    }

    fun setMusicToPlayList(music: MusicEntity) {
        val bvid = music.bvid
        preMusic.value = nowMusic.value
        nowMusic.value = music
        nxtMusic.value = MusicEntity.getEmpty()

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
        exoPlayer.apply {
            prepare()
            play()
        }
        updateIsPlaying(true)
    }

    init {
        exoPlayer = ExoPlayer.Builder(application)
            .build().apply {
                playWhenReady = false
            }

//        val bvid = "BV1rp4y1e745"
//        val url = "https://upos-hz-mirrorakam.akamaized.net/upgcxcode/65/46/244954665/244954665_f9-1-30280.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1673192775&gen=playurlv2&os=akam&oi=1736926806&trid=422afb4cd8a44b95ad868af53647d370u&mid=0&platform=pc&upsig=2c5da64ec59b9d0d3edc9c04b917a9ca&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,mid,platform&hdnts=exp=1673192775~hmac=ee9ab454e9feb63508ad6d32ebfbae235b01b49b5d9f8ce8f0bde4124f90479b&bvc=vod&nettype=0&orderid=0,1&buvid=&build=0&agrr=0&bw=41220&logo=80000000"
//        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
//            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.54")
//            .setDefaultRequestProperties(hashMapOf("Referer" to "https://www.bilibili.com/video/${bvid}"))
//        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
//            .createMediaSource(MediaItem.fromUri(url))

//        exoPlayer.addMediaSource(mediaSource)
//        exoPlayer.prepare()
    }


    fun startThreadGradient() {
        val totalSize = 1024
        var playedSize = 0
        viewModelScope.launch {
            withContext(Dispatchers.Default) {

                withContext(Dispatchers.Main) {
                    totalSeconds.value = totalSize
                }
                while (exoPlayer.isLoading)
                {
                }
                while (true) {
                    if (isPlaying.value) {
                        playedSize += 1
                    }
                    if (playedSize < totalSize) {
                        withContext(Dispatchers.Main) {
                            playedSeconds.value = playedSize
                            playedPercentage.value =
                                (playedSize.toFloat() / totalSize) * 100f
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            playedSeconds.value = totalSize
                            playedPercentage.value = 100f
                        }
                        break
                    }

                    delay(1000)
                }
            }
        }
    }

    fun updatePlayedSeconds(value: Int) {
        playedSeconds.value = value
    }
    fun updateTotalSeconds(value: Int) {
        totalSeconds.value = value
    }
    fun updateIsPlaying(value: Boolean) {
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