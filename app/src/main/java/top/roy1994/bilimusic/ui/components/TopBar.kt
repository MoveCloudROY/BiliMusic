package top.roy1994.bilimusic.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import top.roy1994.bilimusic.header.Header
import top.roy1994.bilimusic.topselectbarelem.Property1
import top.roy1994.bilimusic.topselectbarelem.TopSelectBarElem
import top.roy1994.bilimusic.ui.components.TabRowDefaults.tabIndicatorOffset
import top.roy1994.bilimusic.viewmodel.AddMusicViewModel
import top.roy1994.bilimusic.viewmodel.TopSelectViewModel

@Composable
fun TopBar(
    topSelectBarVM: TopSelectViewModel,
    addMusicVM: AddMusicViewModel = viewModel(),
) {
    val ctx: Context = LocalContext.current
    val addMusicDialogState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    // Code to Show and Dismiss Dialog
    if (addMusicDialogState.value) {
        Dialog(
            onDismissRequest = { addMusicDialogState.value = false },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = true,
            )
        ){
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                AddMusicDialog(addMusicVM, addMusicDialogState)
            }
        }

//        addMusicVM.doSomethingMore()
    } else {
        Toast.makeText(ctx, "Dialog Closed", Toast.LENGTH_SHORT).show()
        addMusicVM.addMusic()
    }

    Column() {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            elevation = 0.dp,
            backgroundColor = Color(0xFFFFFFFF),
            contentPadding = PaddingValues(0.dp),
            content = {
                Header(
                    Modifier.padding(0.dp),
                    onAddTapped = {
                        addMusicDialogState.value = true
                    }
                )
            },
        )
        ScrollableTabRow(
            backgroundColor = Color(0xFFFFFFFF),
            selectedTabIndex = topSelectBarVM.categoryIndex.value,
            edgePadding = 12.dp,
            indicator =  {
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .tabIndicatorOffset(it[topSelectBarVM.categoryIndex.value]),
                    color = Color(0xFFFFFFFF),
                )
            },
            divider = {
                TabRowDefaults.Divider(
                    color = Color(0xFFFFFFFF),
                )
            }
        ) {
            topSelectBarVM.categories.value.forEachIndexed{ index, category ->
                Tab(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(20)),
                    selected = topSelectBarVM.categoryIndex.value == index,
                    onClick = {
                        topSelectBarVM.updateCategoryIndex(index)
                    }
                ) {
                    TopSelectBarElem(
                        ontext = category.title,
                        offtext = category.title,
                        property1 = if (topSelectBarVM.categoryIndex.value == index)
                            Property1.On
                        else Property1.Off,
                        modifier = Modifier
                            .requiredHeight(35.dp)
                    )
                }
            }
        }
    }
}