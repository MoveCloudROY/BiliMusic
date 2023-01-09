package top.roy1994.bilimusic.ui.pages

import android.app.Application
import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import top.roy1994.bilimusic.R
import top.roy1994.bilimusic.albumblockelem.AlbumBlockElem
import top.roy1994.bilimusic.ui.navigation.Screens
import top.roy1994.bilimusic.viewmodel.*


@OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)
@Composable
fun ArtistSheetPage(
    navController: NavHostController,
    playerVM: PlayerViewModel,
    artistSheetVM: ArtistSheetViewModel = viewModel(
        factory = ArtistSheetViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    ),
) {
    val ctx: Context = LocalContext.current


    val sheets by artistSheetVM.artistSheet.observeAsState()

    LazyVerticalGrid(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 6.dp, bottom = 85.dp),
        cells = GridCells.Adaptive(minSize = 160.dp)
    ) {
        items(sheets.orEmpty()) { artistEntity ->
            AlbumBlockElem(
                modifier = Modifier.padding(vertical = 8.dp),
                cover = painterResource(id = R.drawable.default_cover),
                name = artistEntity.artist_name,
                artist = "",
                onAlbumBlockElemTapped = {
                    navController.navigate(
                        "${Screens.ArtistDetail.route}/{artistId}"
                            .replace(
                                oldValue = "{artistId}",
                                newValue = "${artistEntity.artist_id}"
                            )
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewArtistSheetPage()
{
    ArtistSheetPage(
        rememberNavController(),
        viewModel(),
    )
}
