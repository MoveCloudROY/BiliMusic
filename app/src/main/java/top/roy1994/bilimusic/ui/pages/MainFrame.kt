package top.roy1994.bilimusic.ui.pages
import android.app.Application
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import top.roy1994.bilimusic.ui.components.*

import top.roy1994.bilimusic.ui.theme.BiliMusicTheme
import top.roy1994.bilimusic.viewmodel.*


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainFrame(
    navController: NavHostController,
    playerVM: PlayerViewModel,
    topSelectBarVM: TopSelectViewModel
) {
    val playerState =
        rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
        )


    Player(
        content = {
            Scaffold(
                backgroundColor = Color(0xFFFFFFFF),

                topBar = {
                    TopBar(topSelectBarVM)
                },
                bottomBar = {
                    BottomBar(playerVM, playerState)
                },

                ) {
                when(topSelectBarVM.categoryIndex.value){
                    0 -> {// 统计数据条
                        MainPage(
                            playerVM = playerVM,
                            topSelectBarVM = topSelectBarVM
                        )
                    }
                    1 -> {
                        SongsPage(
                            navController = navController,
                            playerVM = playerVM,
                        )
                    }
                    2 -> {
                        SongsSheetPage(
                            navController = navController,
                            playerVM = playerVM,
                        )
                    }
                    3 -> {}
                    4 -> {
                        ArtistSheetPage(
                            navController = navController,
                            playerVM = playerVM
                        )
                    }
                }
            }
        },
        state = playerState,
        playerVM = playerVM,
    )

}


@Preview
@Composable
fun MainFramePreview() {
    BiliMusicTheme {
        MainFrame(
            rememberNavController(),
            viewModel(),
            viewModel()
        )
    }
}