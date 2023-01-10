package top.roy1994.bilimusic

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import top.roy1994.bilimusic.ui.components.AddMusicDialog
import top.roy1994.bilimusic.ui.theme.BiliMusicTheme
import top.roy1994.bilimusic.viewmodel.AddMusicViewModel
import top.roy1994.bilimusic.viewmodel.AddMusicViewModelFactory

class ShareActivity : ComponentActivity() {
    lateinit var msg: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get EXTRA_TEXT share from BiliBili App


        val intentData = intent
        msg = intentData.extras?.getString(Intent.EXTRA_TEXT) ?: ""

        val reglist: List<String> = msg.split(Regex("\\bvideo/"))


        setContent {
            BiliMusicTheme {
                val addMusicVM: AddMusicViewModel = viewModel(
                    factory = AddMusicViewModelFactory(
                        LocalContext.current.applicationContext as Application
                    )
                )
                val addMusicDialogState: MutableState<Boolean> = remember {
                    mutableStateOf(true)
                }
                if (reglist.size >= 2 && reglist[reglist.size-1].startsWith("BV")) {
                    addMusicVM.bvid.value = reglist[reglist.size-1]

                    Dialog(
                        onDismissRequest = {
                        },
                        properties = DialogProperties(
                            dismissOnBackPress = false,
                            dismissOnClickOutside = false,
                        )
                    ){
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color.White
                        ) {
                            AddMusicDialog(
                                addMusicVM = addMusicVM,
                                dialogState = addMusicDialogState,
                                onClose = {
                                    finish()
                                }
                            )
                        }
                    }

                }

            }
        }
        setResult(RESULT_OK)

    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    BiliMusicTheme {
        Greeting("Android")
    }
}