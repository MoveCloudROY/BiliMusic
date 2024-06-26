package top.roy1994.bilimusic.ui.pages

import android.app.Application
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import top.roy1994.bilimusic.musichorizonbarelem.MusicHorizonBarElem
import top.roy1994.bilimusic.musicverticalcommentelem.MusicVerticalCommentElem
import top.roy1994.bilimusic.notfind.NotFind
import top.roy1994.bilimusic.redirtextbar.ReDirTextBar
import top.roy1994.bilimusic.ui.components.topshowblock.TopShowBlock
import top.roy1994.bilimusic.viewmodel.*

@Composable
fun MainPage(
    topSelectBarVM: TopSelectViewModel,
    playerVM: PlayerViewModel,
    topShowBlockVM: TopShowBlockViewModel = viewModel(
        factory = TopShowBlockViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    ),
    musicHistoryVM: MusicHistoryViewModel = viewModel(
        factory = MusicHistoryViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    ),
    musicOftenVM: MusicOftenViewModel = viewModel(
        factory = MusicOftenViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    ),
    musicRecentVM: MusicRecentViewModel = viewModel(
        factory = MusicRecentViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    ),
) {
    val musicHistory by musicHistoryVM.musicHistory.observeAsState()
    val musicOften by musicOftenVM.musicOften.observeAsState()
    val musicRecent by musicRecentVM.musicRecent.observeAsState()

    // tmp
    val musicCnt by topShowBlockVM.musicCnt.observeAsState()
    val sheetCnt by topShowBlockVM.sheetCnt.observeAsState()
    val tagCnt by topShowBlockVM.tagCnt.observeAsState()
    val artistCnt by topShowBlockVM.artistCnt.observeAsState()

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
            item { TopShowBlock(
                modifier = Modifier
                    .requiredSize(width = 100.dp, height = 60.dp)
                    .clip(RoundedCornerShape(16.dp)),

                number = musicCnt?.toString()?:"0",
                type = topShowBlockVM.categories.value[0].title,
                onTopShowBlockElemTapped = {
                    topSelectBarVM.updateCategoryIndex(1)
                },
            ) }
            item { TopShowBlock(
                modifier = Modifier
                    .requiredSize(width = 100.dp, height = 60.dp)
                    .clip(RoundedCornerShape(16.dp)),

                number = sheetCnt?.toString()?:"0",
                type = topShowBlockVM.categories.value[1].title,
                onTopShowBlockElemTapped = {
                    topSelectBarVM.updateCategoryIndex(2)
                },
            ) }
            item { TopShowBlock(
                modifier = Modifier
                    .requiredSize(width = 100.dp, height = 60.dp)
                    .clip(RoundedCornerShape(16.dp)),

                number = tagCnt?.toString()?:"0",
                type = topShowBlockVM.categories.value[2].title,
                onTopShowBlockElemTapped = {
                    topSelectBarVM.updateCategoryIndex(3)
                },
            ) }
            item { TopShowBlock(
                modifier = Modifier
                    .requiredSize(width = 100.dp, height = 60.dp)
                    .clip(RoundedCornerShape(16.dp)),

                number = artistCnt?.toString()?:"0",
                type = topShowBlockVM.categories.value[3].title,
                onTopShowBlockElemTapped = {
                    topSelectBarVM.updateCategoryIndex(4)
                },
            ) }



//            itemsIndexed(topShowBlockVM.categories.value) { index, category ->
//                TopShowBlock(
//                    modifier = Modifier
//                        .requiredSize(width = 100.dp, height = 60.dp)
////                                    .padding(horizontal = 8.dp)
//                        .clip(RoundedCornerShape(16.dp)),
//
//                    number = category.count.toString(),
//                    type = category.title,
//                    onTopShowBlockElemTapped = {
//                        topSelectBarVM.updateCategoryIndex(index + 1)
//                    },
//                )
//            }
        }
        ReDirTextBar(
            modifier = Modifier.height(50.dp),
            text = "历史",
            onReDirTextBarTapped = {
                topSelectBarVM.updateCategoryIndex(5)
            },
        )
        if (musicHistory.isNullOrEmpty()){
            NotFind(
                modifier = Modifier
                    .height(125.dp)
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                title = "还没有记录哦"
            )
        }
        else {
            LazyRow(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                contentPadding = PaddingValues(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(musicHistory.orEmpty()) { _, item ->
                    MusicHorizonBarElem(
                        Modifier.clickable { },
                        cover = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(item.cover_url)//?:R.drawable.notfind
                                .crossfade(true)
                                .build(),
                        ),
                        name = item.music_name,
                        onHorizonBarElemTapped = {
                            playerVM.addMusicToPlayList(item)
                        },
                    )
                }
            }
        }

        ReDirTextBar(
            modifier = Modifier.height(50.dp),
            text = "最常播放",
            onReDirTextBarTapped = {},
        )
        if (musicOften.isNullOrEmpty()){
            NotFind(
                modifier = Modifier
                    .height(125.dp)
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                title = "还没有记录哦"
            )
        }
        else {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
//                        contentPadding = PaddingValues(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                musicOften.orEmpty().forEachIndexed { _, item ->
                    MusicVerticalCommentElem(
                        cover = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(item.cover_url)//?:R.drawable.notfind
                                .crossfade(true)
                                .build(),
                        ),
                        name = item.music_name,
                        artist = item.music_artist,
                        upComment = item.times5day.toString(),
                        downComment = "Times",
                        onVerticalCommentElemTapped = {
                            playerVM.addMusicToPlayList(item)
                        },
                    )
                }
            }
        }
        ReDirTextBar(
            modifier = Modifier.height(50.dp),
            text = "最近加入",
            onReDirTextBarTapped = {},
        )
        if (musicRecent.isNullOrEmpty()){
            NotFind(
                modifier = Modifier
                    .height(125.dp)
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                title = "还没有记录哦"
            )
        }
        else {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .clickable { },
//                        contentPadding = PaddingValues(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                musicRecent.orEmpty().forEachIndexed { _, item ->
                    MusicVerticalCommentElem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                indication = LocalIndication.current,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {},
                        cover = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(item.cover_url)//?:R.drawable.notfind
                                .crossfade(true)
                                .build(),
                        ),
                        name = item.music_name,
                        artist = item.music_artist,
                        upComment = "",
                        downComment = "",
                        onVerticalCommentElemTapped = {
                            playerVM.addMusicToPlayList(item)
                        },
                    )
                }
            }
        }
        Spacer(modifier = Modifier.requiredHeight(80.dp))
    }
}