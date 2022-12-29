package top.roy1994.bilimusic.ui.pages
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.selects.select

import top.roy1994.bilimusic.R
import top.roy1994.bilimusic.albumblockelem.AlbumBlockElem

import top.roy1994.bilimusic.header.Header
import top.roy1994.bilimusic.playbar.PlayBar

import top.roy1994.bilimusic.data.struct.Music
import top.roy1994.bilimusic.header.BiliMusic
import top.roy1994.bilimusic.musichorizonbarelem.MusicHorizonBarElem
import top.roy1994.bilimusic.musiclistdetailelem.MusicListDetailElem
import top.roy1994.bilimusic.musicverticalcommentelem.MusicVerticalCommentElem
import top.roy1994.bilimusic.redirtextbar.ReDirTextBar
import top.roy1994.bilimusic.topselectbarelem.Property1
import top.roy1994.bilimusic.topselectbarelem.TopSelectBarElem
import top.roy1994.bilimusic.ui.components.BottomBar
import top.roy1994.bilimusic.ui.components.topselect.Status

import top.roy1994.bilimusic.ui.components.ScrollableTabRow
import top.roy1994.bilimusic.ui.components.TabRowDefaults.tabIndicatorOffset
import top.roy1994.bilimusic.ui.components.TopBar
import top.roy1994.bilimusic.ui.components.drawColoredShadow
import top.roy1994.bilimusic.ui.components.topselect.TopSelect
import top.roy1994.bilimusic.ui.components.topshowblock.TopShowBlock
import top.roy1994.bilimusic.ui.theme.BiliMusicTheme
import top.roy1994.bilimusic.viewmodel.*
import kotlin.math.min


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainFrame(topSelectBarVM: TopSelectViewModel = viewModel()) {

    Scaffold(
        backgroundColor = Color(0xFFFFFFFF),

        topBar = {
            TopBar(topSelectBarVM)
         },
        bottomBar = {
            BottomBar()
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
            4 -> {}
        }
    }
}


@Preview
@Composable
fun MainFramePreview() {
    BiliMusicTheme {
        MainFrame()
    }
}