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
import top.roy1994.bilimusic.musichorizonbarelem.MusicHorizonBarElem
import top.roy1994.bilimusic.musiclistdetailelem.MusicListDetailElem
import top.roy1994.bilimusic.musicverticalcommentelem.MusicVerticalCommentElem
import top.roy1994.bilimusic.redirtextbar.ReDirTextBar
import top.roy1994.bilimusic.topselectbarelem.Property1
import top.roy1994.bilimusic.topselectbarelem.TopSelectBarElem
import top.roy1994.bilimusic.ui.components.topselect.Status

import top.roy1994.bilimusic.ui.components.ScrollableTabRow
import top.roy1994.bilimusic.ui.components.TabRowDefaults.tabIndicatorOffset
import top.roy1994.bilimusic.ui.components.drawColoredShadow
import top.roy1994.bilimusic.ui.components.topselect.TopSelect
import top.roy1994.bilimusic.ui.components.topshowblock.TopShowBlock
import top.roy1994.bilimusic.viewmodel.*
import kotlin.math.min


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainFrame() {
    val defaultMusic = Music(
        id = -1,
        cover = painterResource(R.drawable.default_cover),
        name = "No Music",
        artist = "No Music Artist",
    )
    val music by remember {
        mutableStateOf(defaultMusic)
    }
    val topSelectBarVM: TopSelectViewModel = viewModel()
    val topShowBlockVM: TopShowBlockViewModel = viewModel()
    val musicHistoryVM: MusicHistoryViewModel = viewModel()
    val musicOftenVM: MusicOftenViewModel = viewModel()
    val musicRecentVM: MusicRecentViewModel = viewModel()

    val musicListVM: MusicListViewModel = viewModel()

    val musicSheetVM: MusicSheetViewModel = viewModel()

    Scaffold(
        backgroundColor = Color(0xFFFFFFFF),

        topBar = {
            Column() {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 0.dp,
                    backgroundColor = Color(0xFFFFFFFF),
                    contentPadding = PaddingValues(0.dp),
                    content = { Header(Modifier.padding(0.dp))},
                )
                ScrollableTabRow(
                    backgroundColor = Color(0xFFFFFFFF),
                    selectedTabIndex = topSelectBarVM.categoryIndex.value,
                    edgePadding = 12.dp,
                    indicator =  {
                        TabRowDefaults.Indicator(
                            modifier = Modifier
                                .tabIndicatorOffset(it[topSelectBarVM.categoryIndex.value]),
                            color = Color(0xFFFFFFFF),
                        )
                    },
                    divider = {
                        TabRowDefaults.Divider(
                            color = Color(0xFFFFFFFF),
                        )
                    }
                ) {
                    topSelectBarVM.categories.value.forEachIndexed{ index, category ->
                        Tab(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .clip(RoundedCornerShape(20)),
                            selected = topSelectBarVM.categoryIndex.value == index,
                            onClick = {
                                topSelectBarVM.updateCategoryIndex(index)
                            }
                        ) {
                            TopSelectBarElem(
                                ontext = category.title,
                                offtext = category.title,
                                property1 = if (topSelectBarVM.categoryIndex.value == index)
                                    Property1.On
                                else Property1.Off,
                                modifier = Modifier
                                    .requiredHeight(35.dp)
                            )
                        }
                    }
                }
            }

         },
        bottomBar = {
            BottomAppBar(

                modifier = Modifier
                    .background(color = Color(0xFFFFFFFF))
                    .requiredHeight(80.dp),
                backgroundColor = Color(0xFFFFFFFF),
                contentPadding = PaddingValues(0.dp),
                content = {
                        PlayBar(
                            modifier = Modifier,
                            musicCover = music.cover
                                ?: painterResource(id = R.drawable.default_cover),
                            musicName = music.name,
                            musicArtist = music.artist,
                        )
                }
            )
        },

    ) {
        when(topSelectBarVM.categoryIndex.value){
            0 -> {// 统计数据条
                Column (
                    Modifier
                        .background(Color(0xFFFFFFFF))
                        .verticalScroll(rememberScrollState())
                ) {
                    // 滚动页面选择条
                    LazyRow(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        itemsIndexed(topShowBlockVM.categories.value) { index, category ->
                            TopShowBlock(
                                modifier = Modifier
                                    .requiredSize(width = 100.dp, height = 60.dp)
//                                    .padding(horizontal = 8.dp)
                                    .clip(RoundedCornerShape(16.dp)),

                                number = category.count.toString(),
                                type = category.title,
                                onTopShowBlockElemTapped = {
                                    topSelectBarVM.updateCategoryIndex(index + 1)
                                },
                            )
                        }
                    }
                    ReDirTextBar(
                        modifier = Modifier.height(50.dp),
                        text = "历史",
                        onReDirTextBarTapped = {},
                    )
                    LazyRow(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        itemsIndexed(musicHistoryVM._5FirstHistory.value) { index, item ->
                            MusicHorizonBarElem(
                                cover = item.cover
                                    ?: painterResource(id = R.drawable.default_cover),
                                name = item.name
                            )
                        }
                    }
                    ReDirTextBar(
                        modifier = Modifier.height(50.dp),
                        text = "最常播放",
                        onReDirTextBarTapped = {},
                    )
                    Column(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
//                        contentPadding = PaddingValues(horizontal = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        musicOftenVM._5FirstOften.value.forEachIndexed { index, item ->
                            MusicVerticalCommentElem(
                                cover = item.cover
                                    ?: painterResource(id = R.drawable.default_cover),
                                name = item.name,
                                artist = item.artist,
                                upComment = music.times5day.toString(),
                                downComment = "Times",
                            )
                        }
                    }
                    ReDirTextBar(
                        modifier = Modifier.height(50.dp),
                        text = "最近加入",
                        onReDirTextBarTapped = {},
                    )
                    Column(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
//                        contentPadding = PaddingValues(horizontal = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        musicRecentVM._5FirstRecent.value.forEachIndexed { index, item ->
                            MusicVerticalCommentElem(
                                cover = item.cover
                                    ?: painterResource(id = R.drawable.default_cover),
                                name = item.name,
                                artist = item.artist,
                                upComment = "",
                                downComment = "",
                            )
                        }
                    }
                    Spacer(modifier = Modifier.requiredHeight(80.dp))
                }
            }
            1 -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 85.dp),
                    verticalArrangement =  Arrangement.spacedBy(4.dp),
                ) {
                    itemsIndexed(musicListVM.musicList.value) { index, item ->
                        MusicListDetailElem(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp)),
                            cover = item.cover
                                ?: painterResource(id = R.drawable.default_cover),
                            name = item.name,
                            artist = item.artist,
                            minuteOff = item.minute.toString(),
                            secondOff = item.second.toString().padStart(2,'0'),
                            switch = if (musicListVM.listIndex.value == index) top.roy1994.bilimusic.musiclistdetailelem.Switch.On
                                        else top.roy1994.bilimusic.musiclistdetailelem.Switch.Off,
                            onSwitchTapped = {
                                musicListVM.updateListIndex(index)
                            },
                        )
                    }
                }
            }
            2 -> {
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 85.dp),
                    cells = GridCells.Adaptive(minSize = 175.dp)
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
            3 -> {}
            4 -> {}
        }

    }

}


@Preview
@Composable
fun MainFramePreview() {
    MainFrame()
}