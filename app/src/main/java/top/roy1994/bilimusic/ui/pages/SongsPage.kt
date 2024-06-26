package top.roy1994.bilimusic.ui.pages

import android.app.Application
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import top.roy1994.bilimusic.musiclistdetailelem.MusicListDetailElem
import top.roy1994.bilimusic.musiclistdetailelem.Switch
import top.roy1994.bilimusic.ui.components.AnimatedSwipeDismiss
import top.roy1994.bilimusic.ui.navigation.Screens
import top.roy1994.bilimusic.viewmodel.MusicListViewModel
import top.roy1994.bilimusic.viewmodel.MusicListViewModelFactory
import top.roy1994.bilimusic.viewmodel.PlayerViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun SongsPage(
    navController: NavController,
    playerVM: PlayerViewModel,
    musicListVM: MusicListViewModel = viewModel(
        factory = MusicListViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    )
) {
    val musics by musicListVM.musicList.observeAsState()
    val coverMap by musicListVM.coverMap.observeAsState()


    LazyColumn(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 85.dp),
        verticalArrangement =  Arrangement.spacedBy(4.dp),
    ) {

        itemsIndexed(musics.orEmpty()) { index, item ->
            key(item.music_id) {
                AnimatedSwipeDismiss(
                    item = item,
                    background = { isDismissed ->
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxSize()
                                .background(Color.LightGray)
                                .clip(RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.CenterEnd

                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Icon",
                                modifier = Modifier.scale(1.2f).padding(horizontal = 8.dp),
                                tint = Color.Gray
                            )
                        }
                    },
                    content = {
                        MusicListDetailElem(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp)),

                            cover = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(item.cover_url)//?:R.drawable.notfind
                                    .crossfade(true)
                                    .build(),
                            ),
                            name = item.music_name,
                            artist = item.music_artist,
                            minuteOff = (item.second / 60).toString(),
                            secondOff = (item.second % 60).toString().padStart(2, '0'),
                            switch = if (musicListVM.listIndex.value == index) Switch.On
                            else Switch.Off,
                            onSwitchTapped = {
                                musicListVM.updateListIndex(index)
                                playerVM.addMusicToPlayList(item)
                            },
                            onLongPressed = {
                                navController.navigate(
                                    "${Screens.MusicConfig.route}/{musicId}"
                                        .replace(
                                            oldValue = "{musicId}",
                                            newValue = "${item.music_id}"
                                        )
                                )
                            }
                        )
                    },
                    onDismiss = {
                        musicListVM.deleteMusic(item)
                    }
                )
            }
//            Log.i("SongsPage-coverMap", "${coverMap}")
        }
    }
}