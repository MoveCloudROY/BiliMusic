package top.roy1994.bilimusic.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import top.roy1994.bilimusic.R
import top.roy1994.bilimusic.playbar.PlayBar
import top.roy1994.bilimusic.viewmodel.PlayingMusicViewModel
import top.roy1994.bilimusic.viewmodel.TopSelectViewModel

@Composable
fun BottomBar(
    playingMusicVM: PlayingMusicViewModel = viewModel(),
) {
    BottomAppBar(

        modifier = Modifier
            .background(color = Color(0xFFFFFFFF))
            .requiredHeight(80.dp),
        backgroundColor = Color(0xFFFFFFFF),
        contentPadding = PaddingValues(0.dp),
        content = {
            PlayBar(
                modifier = Modifier,
                musicCover = playingMusicVM.playingMusic.value.cover
                    ?: painterResource(id = R.drawable.default_cover),
                musicName = playingMusicVM.playingMusic.value.name,
                musicArtist = playingMusicVM.playingMusic.value.artist,
            )
        }
    )
}