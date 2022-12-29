package top.roy1994.bilimusic.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    topShowBlockVM: TopShowBlockViewModel = viewModel(),
    musicHistoryVM: MusicHistoryViewModel = viewModel(),
    musicOftenVM: MusicOftenViewModel = viewModel(),
    musicRecentVM: MusicRecentViewModel = viewModel(),
) {
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
                    upComment = item.times5day.toString(),
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