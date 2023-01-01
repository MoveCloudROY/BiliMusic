package top.roy1994.bilimusic.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import top.roy1994.bilimusic.R
import top.roy1994.bilimusic.playerbar.PlayerBar
import top.roy1994.bilimusic.playercommandbar.PlayerCommandBar
import top.roy1994.bilimusic.playercommandbar.Status
import top.roy1994.bilimusic.playercovertuple.PlayerCoverTuple
import top.roy1994.bilimusic.playermusicinfo.PlayerMusicInfo
import top.roy1994.bilimusic.viewmodel.PlayerProgressBarViewModel
import top.roy1994.bilimusic.viewmodel.PlayerViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Player(
    state: ModalBottomSheetState,
    playerVM: PlayerViewModel = viewModel(),
    PlayerProgressBarVM: PlayerProgressBarViewModel = viewModel(),
) {
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            PlayerBar(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .requiredHeight(height = 46.dp)
                    .fillMaxWidth(),
//                onCloseTapped = {scope.launch { state.hide() }}
            )
            PlayerCoverTuple(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .requiredWidth(756.dp),
                lastMusicCover = playerVM.preMusic?.cover
                    ?: painterResource(id = R.drawable.default_cover),
                nowMusicCover = playerVM.nowMusic?.cover
                    ?: painterResource(id = R.drawable.default_cover),
                nextMusicCover = playerVM.nxtMusic?.cover
                    ?: painterResource(id = R.drawable.default_cover),
            )

            PlayerMusicInfo(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 24.dp)
                    .requiredHeight(90.dp)
                    .fillMaxWidth(),
                name = playerVM.nowMusic?.name
                    ?: "No Music",
                artist = playerVM.nowMusic?.artist
                    ?: "test",
            )
            PlayerProgressBar(PlayerProgressBarVM)
            PlayerCommandBar(
                modifier = Modifier
                    .padding(top = 16.dp),
                status = if (playerVM.isPlaying.value) Status.Playing
                            else Status.Stop,
                onShuffleTapped = {},
                onPreTapped = {},
                onPlayTapped = {
                    playerVM.updateIsPlaying(false)
                    PlayerProgressBarVM.updateIsPlaying(false)
                },
                onStopTapped = {
                    playerVM.updateIsPlaying(true)
                    PlayerProgressBarVM.updateIsPlaying(true)
                },
                onNextTapped = {},
                onLoopTapped = {},
            )
            Spacer(modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth())
        },
        content = {}
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PreviewPlayer() {
    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Expanded)
    Player(state = modalBottomSheetState)
}