package top.roy1994.bilimusic.ui.pages

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import top.roy1994.bilimusic.R
import top.roy1994.bilimusic.addalbumblock.AddAlbumBlock
import top.roy1994.bilimusic.albumblockelem.AlbumBlockElem
import top.roy1994.bilimusic.ui.components.AddSheetDialog
import top.roy1994.bilimusic.ui.navigation.Screens
import top.roy1994.bilimusic.viewmodel.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SongsSheetPage(
    navController: NavHostController,
    playerVM: PlayerViewModel,
    musicSheetVM: MusicSheetViewModel = viewModel(
        factory = MusicSheetViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    ),
    addSheetVM: AddSheetViewModel = viewModel(
        factory = AddSheetViewModelFactory(
            LocalContext.current.applicationContext as Application
        )
    ),
) {
    val ctx: Context = LocalContext.current
    val addSheetDialogState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    // Code to Show and Dismiss Dialog
    if (addSheetDialogState.value) {
        Dialog(
            onDismissRequest = { addSheetDialogState.value = false },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = true,
            )
        ){
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                AddSheetDialog(addSheetVM, addSheetDialogState)
            }
        }
    } else {
        if (addSheetVM.addSuccess.value) {
            Toast.makeText(ctx, "添加成功", Toast.LENGTH_SHORT).show()
            addSheetVM.updateStatus(false)
        }
    }
    
    val sheets by musicSheetVM.musicSheet.observeAsState()
    
    LazyVerticalGrid(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 6.dp, bottom = 85.dp),
        columns = GridCells.Adaptive(minSize = 160.dp)
    ) {
        item {
            AddAlbumBlock(
                modifier = Modifier.padding(vertical = 8.dp),
                onAddTapped = {
                    addSheetDialogState.value = true
                }
            )
        }
        items(sheets.orEmpty()) { sheetEntity ->
            AlbumBlockElem(
                modifier = Modifier.padding(vertical = 8.dp),
                cover = sheetEntity.cover
                    ?: painterResource(id = R.drawable.default_cover),
                name = sheetEntity.sheet_name,
                artist = sheetEntity.sheet_description,
                onAlbumBlockElemTapped = {
                    navController.navigate(
                        "${Screens.SheetDetail.route}/{sheetId}"
                            .replace(
                                oldValue = "{sheetId}",
                                newValue = "${sheetEntity.sheet_id}"
                            )
                    )
                }

            )
        }
    }
}

@Preview
@Composable
fun PreviewSongsSheetPage()
{
    SongsSheetPage(
        rememberNavController(),
        viewModel(),
    )
}
