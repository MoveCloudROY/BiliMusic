package top.roy1994.bilimusic.ui.pages

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import top.roy1994.bilimusic.R
import top.roy1994.bilimusic.musichorizonbarelem.MusicHorizonBarElem
import top.roy1994.bilimusic.musiclistdetailelem.MusicListDetailElem
import top.roy1994.bilimusic.musiclistdetailelem.Switch
import top.roy1994.bilimusic.musicverticalcommentelem.MusicVerticalCommentElem
import top.roy1994.bilimusic.redirtextbar.ReDirTextBar
import top.roy1994.bilimusic.ui.components.topshowblock.TopShowBlock
import top.roy1994.bilimusic.viewmodel.*

@Composable
fun MainPage(
    topSelectBarVM: TopSelectViewModel,
    playerVM: PlayerViewModel,
    topShowBlockVM: TopShowBlockViewModel = viewModel(),
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
            itemsIndexed(musicHistory.orEmpty()) { _, item ->
                MusicHorizonBarElem(
                    cover = item.music_cover
                        ?: painterResource(id = R.drawable.default_cover),
                    name = item.music_name,
                    onHorizonBarElemTapped = {
                        playerVM.setMusicToPlayList(item)
                    },
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
            musicOften.orEmpty().forEachIndexed { _, item ->
                MusicVerticalCommentElem(
                    cover = item.music_cover
                        ?: painterResource(id = R.drawable.default_cover),
                    name = item.music_name,
                    artist = item.music_artist,
                    upComment = item.times5day.toString(),
                    downComment = "Times",
                    onVerticalCommentElemTapped = {
                        playerVM.setMusicToPlayList(item)
                    },
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
            musicRecent.orEmpty().forEachIndexed { _, item ->
                MusicVerticalCommentElem(
                    cover = item.music_cover
                        ?: painterResource(id = R.drawable.default_cover),
                    name = item.music_name,
                    artist = item.music_artist,
                    upComment = "",
                    downComment = "",
                    onVerticalCommentElemTapped = {
                        playerVM.setMusicToPlayList(item)
                    },
                )
            }
        }
        Spacer(modifier = Modifier.requiredHeight(80.dp))
    }
}