package top.roy1994.bilimusic.ui.components

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import top.roy1994.bilimusic.R
import top.roy1994.bilimusic.playerbar.PlayerBar
import top.roy1994.bilimusic.playercommandbar.PlayerCommandBar
import top.roy1994.bilimusic.playercommandbar.Status
import top.roy1994.bilimusic.playercovertuple.PlayerCoverTuple
import top.roy1994.bilimusic.playermusicinfo.PlayerMusicInfo
import top.roy1994.bilimusic.viewmodel.PlayerViewModel
import top.roy1994.bilimusic.viewmodel.PlayerViewModelFactory

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Player(
    content : @Composable () -> Unit,
    state: ModalBottomSheetState,
    playerVM: PlayerViewModel,
) {
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        modifier = Modifier,
        sheetState = state,
        sheetContent = {
            PlayerBar(
                modifier = Modifier
                    .background(Color(0xFFFFFFFF))
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .requiredHeight(height = 46.dp)
                    .fillMaxWidth(),
                onCloseTapped = {scope.launch { state.hide() }}
            )
            PlayerCoverTuple(
                modifier = Modifier
                    .background(Color(0xFFFFFFFF))
                    .padding(vertical = 16.dp)
                    .requiredWidth(756.dp)
                    .align(alignment = Alignment.CenterHorizontally),

                lastMusicCover = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(playerVM.preMusic.value.cover_url)//?:R.drawable.notfind
                        .crossfade(true)
                        .build(),
                ),
                nowMusicCover = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(playerVM.nowMusic.value.cover_url?:R.drawable.notfind)//?:R.drawable.notfind
                        .crossfade(true)
                        .build(),
                ),
                nextMusicCover = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(playerVM.nxtMusic.value.cover_url)//?:R.drawable.notfind
                        .crossfade(true)
                        .build(),
                ),
            )

            PlayerMusicInfo(
                modifier = Modifier
                    .background(Color(0xFFFFFFFF))
                    .padding(top = 8.dp, bottom = 24.dp)
                    .requiredHeight(90.dp)
                    .fillMaxWidth(),
                name = playerVM.nowMusic.value.music_name.ifEmpty { "还没有播放" },
                artist = playerVM.nowMusic.value.music_artist,
            )
            PlayerProgressBar(playerVM)
            PlayerCommandBar(
                modifier = Modifier
                    .background(Color(0xFFFFFFFF))
                    .padding(top = 8.dp),
                status = if (playerVM.isPlaying.value) Status.Playing
                            else Status.Stop,
                onShuffleTapped = {
                    playerVM.setPlayerShuffle()
                },
                onPreTapped = {
                    playerVM.previous()
                },
                onPlayTapped = {
                    if (!playerVM.isEmptyMusic()) {
                        if (playerVM.isPlaying.value && playerVM.exoPlayer.isPlaying) {
                            // pause the video
                            playerVM.exoPlayer.pause()
                            playerVM.setPlayState(false)
                        } else {
                            // play the video
                            // it's already paused
                            playerVM.exoPlayer.play()
                            playerVM.setPlayState(true)
                        }
                    }
                },
                onNextTapped = {
                    playerVM.next()
                },
                onLoopTapped = {
                    playerVM.setPlayerRepeatAll()
                },
            )
            Spacer(modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth())
        },
        content = content
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PreviewPlayer() {
    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Expanded)
    Player(
        state = modalBottomSheetState, content = {},
        playerVM = viewModel(
            factory = PlayerViewModelFactory(
                LocalContext.current.applicationContext as Application
            )
        ),
    )
}