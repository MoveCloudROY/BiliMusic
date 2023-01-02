package top.roy1994.bilimusic.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
    playerState: ModalBottomSheetState,
    playerVM: PlayerViewModel = viewModel(),
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
                musicCover = playerVM.nowMusic.value.cover
                    ?: painterResource(id = R.drawable.default_cover),
                musicName = playerVM.nowMusic.value.name,
                musicArtist = playerVM.nowMusic.value.artist,
                status = if (playerVM.isPlaying.value) top.roy1994.bilimusic.playbar.Status.Playing
                else top.roy1994.bilimusic.playbar.Status.Stop,
                onPlayBarTapped = {
                    scope.launch{ playerState.animateTo(ModalBottomSheetValue.Expanded) }
                },
                onPlayButtonTapped = {
                    playerVM.updateIsPlaying(!playerVM.isPlaying.value)
                }
            )
        }
    )

}