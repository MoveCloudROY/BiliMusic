package top.roy1994.bilimusic.ui.pages

import android.app.Application
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.liveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import top.roy1994.bilimusic.R
import top.roy1994.bilimusic.data.utils.BiliRepo
import top.roy1994.bilimusic.musiclistdetailelem.MusicListDetailElem
import top.roy1994.bilimusic.musiclistdetailelem.Switch
import top.roy1994.bilimusic.ui.components.swipeToDismiss
import top.roy1994.bilimusic.ui.navigation.Screens
import top.roy1994.bilimusic.viewmodel.MusicListViewModel
import top.roy1994.bilimusic.viewmodel.MusicListViewModelFactory
import top.roy1994.bilimusic.viewmodel.PlayerViewModel

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
                MusicListDetailElem (
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
                        playerVM.setMusicToPlayList(item)
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




            Log.i("SongsPage-coverMap", "${coverMap}")
        }
    }
}