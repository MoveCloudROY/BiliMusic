package top.roy1994.bilimusic.ui.pages

import android.app.Application
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
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
import top.roy1994.bilimusic.viewmodel.IncompleteViewModel
import top.roy1994.bilimusic.viewmodel.IncompleteViewModelFactory
import top.roy1994.bilimusic.viewmodel.PlayerViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun IncompletePage(
    navController: NavController,
    playerVM: PlayerViewModel,
    incompVM: IncompleteViewModel = viewModel(
        factory = IncompleteViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    )
) {
    val musics by incompVM.incompList.observeAsState()
//    val coverMap by incompVM.coverMap.observeAsState()


    LazyColumn(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 85.dp),
        verticalArrangement =  Arrangement.spacedBy(4.dp),
    ) {

        itemsIndexed(musics.orEmpty()) { index, item ->
            key(item.music.music_id) {
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
                        val seconds = (item.music.second * item.musicIncomplete.progress).toLong()
                        Column(
                        ){
                            MusicListDetailElem(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp)),

                                cover = rememberAsyncImagePainter(
                                    ImageRequest.Builder(LocalContext.current)
                                        .data(item.music.cover_url)//?:R.drawable.notfind
                                        .crossfade(true)
                                        .build(),
                                ),
                                name = item.music.music_name,
                                artist = item.music.music_artist,
                                minuteOff = "At "+(seconds / 60).toString(),
                                secondOff = (seconds % 60).toString().padStart(2, '0'),
                                switch = if (incompVM.listIndex.value == index) Switch.On
                                else Switch.Off,
                                onSwitchTapped = {
//                                    incompVM.updateListIndex(index)
                                    playerVM.addMusicToPlayList(item.music, item.musicIncomplete.progress)
                                },
                                onLongPressed = {
//                                    navController.navigate(
//                                        "${Screens.MusicConfig.route}/{musicId}"
//                                            .replace(
//                                                oldValue = "{musicId}",
//                                                newValue = "${item.music.music_id}"
//                                            )
//                                    )
                                }
                            )
                            Canvas(
                                modifier = Modifier
                                    .background(Color(0xFFFFFFFF))
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .padding(start = 4.dp, end = 4.dp)
                            ) {
                                // Background indicator
                                drawLine(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF3f5fff),
                                            Color(0xFFaf71ff),
                                        )
                                    ),
                                    cap = StrokeCap.Round,
                                    strokeWidth = size.height,
                                    start = Offset(x = 0f, y = 0f),
                                    end = Offset(x = item.musicIncomplete.progress * size.width, y = 0f)
                                )
                            }
                        }
                    },
                    onDismiss = {
                        incompVM.deleteMusic(item.music.music_id)
                    }
                )
            }
//            Log.i("SongsPage-coverMap", "${coverMap}")
        }
    }
}