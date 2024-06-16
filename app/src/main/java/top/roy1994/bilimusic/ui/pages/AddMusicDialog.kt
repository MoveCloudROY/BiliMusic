package top.roy1994.bilimusic.ui.components

import android.app.Application
import android.app.Dialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
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
    onClose: () -> Unit = {},
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
                onClose()
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
                    onClose()
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
        Column {
            OutlinedTextField(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray
                ),
                trailingIcon = {
                    if (addMusicVM.bvidError.value)
                        Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colors.error)
                },
                keyboardActions = KeyboardActions {
                    addMusicVM.checkBvid(addMusicVM.bvid.value)
                },
                singleLine = true,
                value = addMusicVM.bvid.value,
                onValueChange = {
                    addMusicVM.updateBvid(it)
                    addMusicVM.bvidError.value = false
                },
                label = { Text("BVid") },
                isError = addMusicVM.bvidError.value,
            )
            if (addMusicVM.bvidError.value) {
                Text(
                    text = "Error Bvid, It likes \'BVxxxxxxxx\'",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
//        OutlinedTextField(
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .padding(vertical = 12.dp),
//            value = addMusicVM.part.value,
//            onValueChange = { addMusicVM.updatePart(it) },
//            label = { Text("分片") },
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                unfocusedBorderColor = Color.Gray)
//        )

        Column {
            OutlinedTextField(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray
                ),
                trailingIcon = {
                    if (addMusicVM.nameError.value)
                        Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colors.error)
                },
                keyboardActions = KeyboardActions {
                    addMusicVM.checkName(addMusicVM.name.value)
                },
                singleLine = true,
                value = addMusicVM.name.value,
                onValueChange = {
                    addMusicVM.updateName(it)
                    addMusicVM.nameError.value = false
                },
                label = { Text("歌曲名") },
                isError = addMusicVM.nameError.value,
            )
            if (addMusicVM.nameError.value) {
                Text(
                    text = "Name Should be less than 10 and only CHAR or NUM",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        Column {
            OutlinedTextField(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray
                ),
                trailingIcon = {
                    if (addMusicVM.artistError.value)
                        Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colors.error)
                },
                keyboardActions = KeyboardActions {
                    addMusicVM.checkArtist(addMusicVM.artist.value)
                },
                singleLine = true,
                value = addMusicVM.artist.value,
                onValueChange = {
                    addMusicVM.updateArtist(it)
                    addMusicVM.artistError.value = false
                },
                label = { Text("艺术家") },
                isError = addMusicVM.artistError.value,
            )
            if (addMusicVM.artistError.value) {
                Text(
                    text = "Artist Should be less than 10",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        Column {
            OutlinedTextField(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray
                ),
                trailingIcon = {
                    if (addMusicVM.sheetError.value)
                        Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colors.error)
                },
                singleLine = true,
                value = addMusicVM.sheet.value,
                onValueChange = {
                    addMusicVM.updateSheet(it)
                    addMusicVM.sheetError.value = false
                },
                label = { Text("目标歌单") },
                isError = addMusicVM.sheetError.value,
            )
            if (addMusicVM.artistError.value) {
                Text(
                    text = "Not Found, please add firstly",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
        Log.i("AddMusicDialog",
        """
            ${addMusicVM.bvidError.value}
            ${addMusicVM.nameError.value}
            ${addMusicVM.artistError.value}
            ${addMusicVM.sheetError.value}
        """.trimIndent())

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