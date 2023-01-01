package top.roy1994.bilimusic.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlayerProgressBarViewModel : ViewModel() {

    var playedPercentage = MutableLiveData<Float>(0.0f)
    var playedSeconds = MutableLiveData<Int>(0)
        private set
    var totalSeconds = MutableLiveData<Int>(0)
        private set
    var isPlaying =  mutableStateOf(false)
        private set

    fun startThreadGradient() {
        val totalSize = 1024
        var playedSize = 0
        viewModelScope.launch {
            withContext(Dispatchers.Default) {

                withContext(Dispatchers.Main) {
                    totalSeconds.value = totalSize
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