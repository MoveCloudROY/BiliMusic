package top.roy1994.bilimusic.ui.components

import android.app.Application
import android.app.Dialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import top.roy1994.bilimusic.MainActivity
import top.roy1994.bilimusic.dialogbar.DialogBar
import top.roy1994.bilimusic.dialogok.DialogOk
import top.roy1994.bilimusic.ui.navigation.Screens
import top.roy1994.bilimusic.viewmodel.AddMusicViewModel
import top.roy1994.bilimusic.viewmodel.MusicConfigViewModel
import top.roy1994.bilimusic.viewmodel.MusicConfigViewModelFactory


@Composable
fun MusicConfigDialog(
    navController: NavController,
    musicId: Int,
    musicConfigVM: MusicConfigViewModel = viewModel(
        factory = MusicConfigViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    ),
) {
    musicConfigVM.updateMusicId(musicId)
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
            title = "设置歌曲",
            onCloseTapped = {
                navController.navigate(Screens.Main.route) {
                    launchSingleTop = true
                }
            }
        )
        MusicInfoModifyField(
            musicConfigVM = musicConfigVM
        )
        DialogOk(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "确定",
            onButtonTapped = {

            }
        )
    }
}

@Composable
fun MusicInfoModifyField(
    musicConfigVM: MusicConfigViewModel,
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
                    if (musicConfigVM.nameError.value)
                        Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colors.error)
                },
                keyboardActions = KeyboardActions {
                    musicConfigVM.checkName(musicConfigVM.name.value)
                },
                singleLine = true,
                value = musicConfigVM.name.value,
                onValueChange = {
                    musicConfigVM.updateName(it)
                    musicConfigVM.nameError.value = false
                },
                label = { Text("歌曲名, 不更改保留不变") },
                isError = musicConfigVM.nameError.value,
            )
            if (musicConfigVM.nameError.value) {
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
                    if (musicConfigVM.artistError.value)
                        Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colors.error)
                },
                keyboardActions = KeyboardActions {
                    musicConfigVM.checkArtist(musicConfigVM.artist.value)
                },
                singleLine = true,
                value = musicConfigVM.artist.value,
                onValueChange = {
                    musicConfigVM.updateArtist(it)
                    musicConfigVM.artistError.value = false
                },
                label = { Text("艺术家, 不更改保留不变") },
                isError = musicConfigVM.artistError.value,
            )
            if (musicConfigVM.artistError.value) {
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
                    if (musicConfigVM.sheetError.value)
                        Icon(Icons.Filled.Info, "Error", tint = MaterialTheme.colors.error)
                },
                singleLine = true,
                value = musicConfigVM.sheet.value,
                onValueChange = {
                    musicConfigVM.updateSheet(it)
                    musicConfigVM.sheetError.value = false
                },
                label = { Text("目标歌单, 不更改保留不变") },
                isError = musicConfigVM.sheetError.value,
            )
            if (musicConfigVM.artistError.value) {
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
            ${musicConfigVM.bvidError.value}
            ${musicConfigVM.nameError.value}
            ${musicConfigVM.artistError.value}
            ${musicConfigVM.sheetError.value}
        """.trimIndent())
    }

}

@Preview
@Composable
fun PreviewMusicConfigDialog() {
    MusicConfigDialog(
        rememberNavController(),
        1,
        viewModel())
}