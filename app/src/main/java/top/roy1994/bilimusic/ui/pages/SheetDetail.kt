package top.roy1994.bilimusic.ui.pages

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import top.roy1994.bilimusic.R
import top.roy1994.bilimusic.backbar.BackBar
import top.roy1994.bilimusic.data.objects.music.MusicEntity
import top.roy1994.bilimusic.data.objects.sheet.SheetEntity
import top.roy1994.bilimusic.musicverticalcommentelem.MusicVerticalCommentElem
import top.roy1994.bilimusic.sheetcommand.SheetCommand
import top.roy1994.bilimusic.sheetinfo.SheetInfo
import top.roy1994.bilimusic.sheetsongelem.SheetSongElem
import top.roy1994.bilimusic.ui.components.BottomBar
import top.roy1994.bilimusic.ui.components.Player
import top.roy1994.bilimusic.ui.components.TopBar
import top.roy1994.bilimusic.viewmodel.PlayerViewModel
import top.roy1994.bilimusic.viewmodel.SheetDetailViewModel
import top.roy1994.bilimusic.viewmodel.SheetDetailViewModelFactory

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetDetail(
    sheedId: Int,
    playerVM: PlayerViewModel,
    sheetDetailVM: SheetDetailViewModel = viewModel(
        factory = SheetDetailViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    ),
) {
    val playerState =
        rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
        )

    val musicSheetMap by sheetDetailVM.musicSheetMap.observeAsState()

    Player(
        content = {
            Scaffold(
                backgroundColor = Color(0xFFFFFFFF),
                bottomBar = {
                    BottomBar(playerVM, playerState)
                },
            ) {
                Column (
                    Modifier
                        .background(Color(0xFFFFFFFF))
                        .verticalScroll(rememberScrollState())
                ) {
                    BackBar(
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 4.dp)
                            .requiredHeight(height = 46.dp)
                            .fillMaxWidth(),
                        title = "",
                        onBackTapped = {}
                    )
                    SheetInfo(
                        modifier = Modifier
                            .padding(horizontal = 32.dp, vertical = 0.dp)
                            .fillMaxWidth(),
                        cover = null
                            ?: painterResource(id = R.drawable.default_cover),
                        name = "Call Recordings",
                        artist = "<unknown>",
                    )
                    SheetCommand(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .fillMaxWidth(),
                        onShuffleTapped = {},
                        onPlayTapped = {},
                    )
                    Column(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
//                        contentPadding = PaddingValues(horizontal = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
//                        SheetSongElem(
//                            id = , name = , minute = , second =
//                        )
                    }



                }


            }
        },
        state = playerState,
    )
}

@Preview
@Composable
fun PreviewSheetDetail() {
    SheetDetail(
        sheedId = 1,
        sheetDetailVM = viewModel(
            factory = SheetDetailViewModelFactory(
                LocalContext.current.applicationContext as Application
            )
        ),
        playerVM = viewModel(),
    )

}