package top.roy1994.bilimusic.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import top.roy1994.bilimusic.R
import top.roy1994.bilimusic.musiclistdetailelem.MusicListDetailElem
import top.roy1994.bilimusic.musiclistdetailelem.Switch
import top.roy1994.bilimusic.viewmodel.MusicListViewModel

@Composable
fun SongsPage(
    musicListVM: MusicListViewModel = viewModel()
) {
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
                switch = if (musicListVM.listIndex.value == index) Switch.On
                else Switch.Off,
                onSwitchTapped = {
                    musicListVM.updateListIndex(index)
                },
            )
        }
    }
}