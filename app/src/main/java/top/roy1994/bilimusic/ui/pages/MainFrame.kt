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

import top.roy1994.bilimusic.ui.components.*

import top.roy1994.bilimusic.ui.theme.BiliMusicTheme
import top.roy1994.bilimusic.viewmodel.*


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun MainFrame(
    topSelectBarVM: TopSelectViewModel = viewModel()
) {
    val playerState =
        rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
        )

    val addMusicVM: AddMusicViewModel = viewModel(
        factory = AddMusicViewModelFactory(
            LocalContext.current.applicationContext as Application)
    )

    Player(
        content = {
            Scaffold(
                backgroundColor = Color(0xFFFFFFFF),

                topBar = {
                    TopBar(topSelectBarVM, addMusicVM)
                },
                bottomBar = {
                    BottomBar(playerState)
                },

                ) {
                when(topSelectBarVM.categoryIndex.value){
                    0 -> {// 统计数据条
                        MainPage(topSelectBarVM = topSelectBarVM)
                    }
                    1 -> {
                        SongsPage()
                    }
                    2 -> {
                        SongsSheetPage()
                    }
                    3 -> {}
//                    4 -> {}
                }
            }
        },
        state = playerState,

    )

}


@Preview
@Composable
fun MainFramePreview() {
    BiliMusicTheme {
        MainFrame()
    }
}