package top.roy1994.bilimusic.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import top.roy1994.bilimusic.R
import top.roy1994.bilimusic.playbar.PlayBar
import top.roy1994.bilimusic.playercommandbar.Status
import top.roy1994.bilimusic.viewmodel.PlayerViewModel
import top.roy1994.bilimusic.viewmodel.PlayingMusicViewModel
import top.roy1994.bilimusic.viewmodel.TopSelectViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomBar(
    playerVM: PlayerViewModel,
    playerState: ModalBottomSheetState,
) {
    val scope = rememberCoroutineScope()

    BottomAppBar(
        modifier = Modifier
            .background(color = Color(0xFFFFFFFF))
            .requiredHeight(80.dp),
        backgroundColor = Color(0xFFFFFFFF),
        contentPadding = PaddingValues(0.dp),
        content = {
            PlayBar(
                modifier = Modifier,
                musicCover = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(playerVM.nowMusic.value.cover_url?:R.drawable.notfind)//?:R.drawable.notfind
                        .crossfade(true)
                        .build(),
                ),
                musicName = playerVM.nowMusic.value.music_name.ifEmpty { "还没有播放" },
                musicArtist = playerVM.nowMusic.value.music_artist,
                status = if (playerVM.isPlaying.value) top.roy1994.bilimusic.playbar.Status.Playing
                else top.roy1994.bilimusic.playbar.Status.Stop,
                onPlayBarTapped = {
                    scope.launch{ playerState.animateTo(ModalBottomSheetValue.Expanded) }
                },
                onPlayButtonTapped = {
                    if (playerVM.nowMusic.value.music_name.isNotEmpty()) {
                        playerVM.updateIsPlaying(!playerVM.isPlaying.value)
                        if (playerVM.exoPlayer.isPlaying) {
                            // pause the video
                            playerVM.exoPlayer.pause()
                        } else {
                            // play the video
                            // it's already paused
                            playerVM.exoPlayer.play()
                        }
                    }
                }
            )
        }
    )

}