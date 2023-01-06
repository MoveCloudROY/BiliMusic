package top.roy1994.bilimusic.ui.components

import android.app.Application
import android.app.Dialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import top.roy1994.bilimusic.MainActivity
import top.roy1994.bilimusic.dialogbar.DialogBar
import top.roy1994.bilimusic.dialogok.DialogOk
import top.roy1994.bilimusic.viewmodel.AddMusicViewModel


@Composable
fun AddMusicDialog(
    addMusicVM: AddMusicViewModel,
    dialogState: MutableState<Boolean>,
) {

    Column(
        modifier = Modifier
            .background(Color(0xFFFFFFFF))
            .padding(horizontal = 12.dp, vertical = 24.dp)
            .clip(RoundedCornerShape(12.dp)),
        verticalArrangement = Arrangement.Center
    ) {
        DialogBar(
            modifier = Modifier
                .requiredHeight(48.dp)
                .align(Alignment.CenterHorizontally),
            title = "添加歌曲",
            onCloseTapped = {
                dialogState.value = false
            }
        )
        MusicInfoInputField(
            addMusicVM = addMusicVM
        )
        DialogOk(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "确定",
            onButtonTapped = {
                addMusicVM.addMusic()
                if (addMusicVM.addSuccess.value)
                {
                    dialogState.value = false
                }
            }
        )
    }
}

@Composable
fun MusicInfoInputField(
    addMusicVM: AddMusicViewModel,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        OutlinedTextField(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 12.dp),
            value = addMusicVM.bvid.value,
            onValueChange = { addMusicVM.updateBvid(it) },
            label = { Text("BVid") }
        )

        OutlinedTextField(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 12.dp),
            value = addMusicVM.part.value,
            onValueChange = { addMusicVM.updatePart(it) },
            label = { Text("分片") }
        )

        OutlinedTextField(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 12.dp),
            value = addMusicVM.name.value,
            onValueChange = { addMusicVM.updateName(it) },
            label = { Text("歌曲名") }
        )


        OutlinedTextField(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 12.dp),
            value = addMusicVM.artist.value,
            onValueChange = { addMusicVM.updateArtist(it) },
            label = { Text("艺术家") }
        )

        OutlinedTextField(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 12.dp),
            value = addMusicVM.sheet.value,
            onValueChange = { addMusicVM.updateSheet(it) },
            label = { Text("目标歌单") }
        )
    }

}

@Preview
@Composable
fun PreviewAddMusicDialog() {
    AddMusicDialog(viewModel(), remember { mutableStateOf(true) })
}
@Preview
@Composable
fun PreviewMusicInfoInputField() {
    MusicInfoInputField(viewModel())
}