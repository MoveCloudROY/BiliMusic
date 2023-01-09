package top.roy1994.bilimusic.ui.pages

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.liveData
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import top.roy1994.bilimusic.R
import top.roy1994.bilimusic.data.utils.BiliRepo
import top.roy1994.bilimusic.musiclistdetailelem.MusicListDetailElem
import top.roy1994.bilimusic.musiclistdetailelem.Switch
import top.roy1994.bilimusic.viewmodel.MusicListViewModel
import top.roy1994.bilimusic.viewmodel.MusicListViewModelFactory
import top.roy1994.bilimusic.viewmodel.PlayerViewModel

@Composable
fun SongsPage(
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
            MusicListDetailElem(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp)),
                cover = rememberAsyncImagePainter(
                    coverMap?.get(index)?:R.drawable.default_cover,
                    transform =
                ),
                name = item.music_name,
                artist = item.music_artist,
                minuteOff = (item.second / 60).toString(),
                secondOff = (item.second % 60).toString().padStart(2,'0'),
                switch = if (musicListVM.listIndex.value == index) Switch.On
                else Switch.Off,
                onSwitchTapped = {
                    musicListVM.updateListIndex(index)
                    playerVM.setMusicToPlayList(item)
                },
            )
            Log.i("SongsPage-coverMap", "${coverMap}")
        }
    }
}