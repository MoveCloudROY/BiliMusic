package top.roy1994.bilimusic.ui.pages

import android.app.Application
import android.util.Log
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
import top.roy1994.bilimusic.ui.navigation.Screens
import top.roy1994.bilimusic.viewmodel.PlayerViewModel
import top.roy1994.bilimusic.viewmodel.PlayerViewModelFactory
import top.roy1994.bilimusic.viewmodel.SheetDetailViewModel
import top.roy1994.bilimusic.viewmodel.SheetDetailViewModelFactory

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetDetail(
    navController: NavHostController,
    sheetId: Int,
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
    sheetDetailVM.updateSheetId(sheetId)

    val sheetInfo by sheetDetailVM.sheetInfo.observeAsState()

    Player(
        playerVM = playerVM,
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
                        onBackTapped = {
                            navController.navigate(Screens.Main.route) {
                                launchSingleTop = true
                            }
                        }
                    )
                    SheetInfo(
                        modifier = Modifier
                            .padding(horizontal = 32.dp, vertical = 0.dp)
                            .fillMaxWidth(),
                        cover = null
                            ?: painterResource(id = R.drawable.default_cover),
                        name = sheetInfo?.sheet_name
                            ?:"Call Recordings",
                        artist = sheetInfo?.sheet_description
                            ?:"<unknown>",
                    )
                    SheetCommand(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .fillMaxWidth(),
                        onShuffleTapped = {
                            playerVM.setPlayList(sheetDetailVM.sheetElems.value)
                            playerVM.setPlayerShuffle()
                        },
                        onPlayTapped = {
                            playerVM.setPlayList(sheetDetailVM.sheetElems.value)
                        },
                    )
                    Column(
                        modifier = Modifier.padding(horizontal = 0.dp, vertical = 12.dp),
//                        contentPadding = PaddingValues(horizontal = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        sheetDetailVM.sheetElems.value.forEachIndexed { index, item ->
                            Log.i("UI", "${item.music_name}  ${item.music_artist}  ${item.second}")
                            SheetSongElem(
                                modifier = Modifier
                                    .requiredHeight(31.dp)
                                    .padding(horizontal = 16.dp, vertical = 0.dp)
                                    .fillMaxWidth(),
                                id = (index + 1).toString(),
                                name = item.music_name,
                                minute = (item.second / 60).toString(),
                                second = (item.second % 60).toString().padStart(2,'0'),
                                onSongTapped = {
                                    playerVM.setPlayList(sheetDetailVM.sheetElems.value)

                                }
                            )
                        }
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
        rememberNavController(),
        sheetId = 1,
        sheetDetailVM = viewModel(
            factory = SheetDetailViewModelFactory(
                LocalContext.current.applicationContext as Application
            )
        ),
        playerVM = viewModel(
            factory = PlayerViewModelFactory(
                LocalContext.current.applicationContext as Application
            )
        )
    )

}