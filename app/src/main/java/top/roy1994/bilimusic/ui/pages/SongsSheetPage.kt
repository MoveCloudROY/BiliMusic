package top.roy1994.bilimusic.ui.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import top.roy1994.bilimusic.R
import top.roy1994.bilimusic.albumblockelem.AlbumBlockElem
import top.roy1994.bilimusic.viewmodel.MusicSheetViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SongsSheetPage(
    musicSheetVM: MusicSheetViewModel = viewModel()
) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 6.dp, bottom = 85.dp),
        cells = GridCells.Adaptive(minSize = 160.dp)
    ) {
        items(musicSheetVM.musicSheet.value) { item ->
            AlbumBlockElem(
                modifier = Modifier.padding(vertical = 8.dp),
                cover = item.cover
                    ?: painterResource(id = R.drawable.default_cover),
                name = item.name,
                artist = item.description,
            )
        }
    }
}