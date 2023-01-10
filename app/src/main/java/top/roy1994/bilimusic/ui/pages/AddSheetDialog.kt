package top.roy1994.bilimusic.ui.components

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

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import top.roy1994.bilimusic.dialogbar.DialogBar
import top.roy1994.bilimusic.dialogok.DialogOk
import top.roy1994.bilimusic.viewmodel.AddSheetViewModel


@Composable
fun AddSheetDialog(
    addSheetVM: AddSheetViewModel,
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
            title = "添加歌单",
            onCloseTapped = {
                dialogState.value = false
            }
        )
        SheetInfoInputField(
            addSheetVM = addSheetVM
        )
        DialogOk(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "确定",
            onButtonTapped = {
                addSheetVM.addSheet()
                if (addSheetVM.addSuccess.value)
                {
                    dialogState.value = false
                }
            }
        )
    }
}

@Composable
fun SheetInfoInputField(
    addSheetVM: AddSheetViewModel,
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
            singleLine = true,
            value = addSheetVM.name.value,
            onValueChange = { addSheetVM.updateName(it) },
            label = { Text("歌单名") }
        )

        OutlinedTextField(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 12.dp),
            singleLine = true,
            value = addSheetVM.description.value,
            onValueChange = { addSheetVM.updateDescription(it) },
            label = { Text("备注") }
        )
    }

}

@Preview
@Composable
fun PreviewAddSheetDialog() {
    AddSheetDialog(viewModel(), remember { mutableStateOf(true) })
}
@Preview
@Composable
fun PreviewSheetInfoInputField() {
    SheetInfoInputField(viewModel())
}