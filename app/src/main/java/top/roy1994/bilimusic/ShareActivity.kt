package top.roy1994.bilimusic

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import top.roy1994.bilimusic.data.objects.biliapi.BiliCreator
import top.roy1994.bilimusic.ui.components.AddMusicDialog
import top.roy1994.bilimusic.ui.theme.BiliMusicTheme
import top.roy1994.bilimusic.viewmodel.AddMusicViewModel
import top.roy1994.bilimusic.viewmodel.AddMusicViewModelFactory
import java.net.HttpURLConnection
import java.net.URL


class ShareActivity : ComponentActivity() {
    lateinit var msg: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get EXTRA_TEXT share from BiliBili App

        val intentData = intent
        msg = intentData.extras?.getString(Intent.EXTRA_TEXT) ?: ""

        val urlPattern = "https?://b23\\.tv/\\S+"
        val shortRegex = Regex(urlPattern)

        val matchResult = shortRegex.find(msg)
        val extractedUrl = matchResult?.value

        var videoId = mutableStateOf("")
        var videoTitle = mutableStateOf("")
        Log.i("Activity", extractedUrl.toString());
        val videoIdPattern = "https://www\\.bilibili\\.com/video/([A-Za-z0-9]+)"
        val longRegex = Regex(videoIdPattern)
        lifecycleScope.launch {
            val longUrl = extractedUrl?.let { parseShortUrl(it) }
            Log.println(Log.INFO, "Activity", "Long URL: $longUrl")
            val matchResults = longUrl?.let { longRegex.find(it) }
            val BVid = matchResults?.groups?.get(1)?.value
            videoId.value = BVid.orEmpty();
            Log.println(Log.INFO, "Activity", "BVid: $BVid")

            val title = BiliCreator.getInstance().service
                            .getVideoInfo(BVid.orEmpty()).body()?.data?.title;
            videoTitle.value = title.orEmpty()
            Log.println(Log.INFO, "Activity", "Video Title: $title")

            // You can use the long URL here (e.g., update UI state
        }

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

                addMusicVM.bvid.value = videoId.value
                addMusicVM.name.value = videoTitle.value

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
        setResult(RESULT_OK)

    }
    suspend fun parseShortUrl(eurl: String) =
        withContext(Dispatchers.IO) {
            val url = URL(eurl)
            val ucon = url.openConnection() as HttpURLConnection
            ucon.instanceFollowRedirects = false
            val secondURL = URL(ucon.getHeaderField("Location"))
            Log.i("Activity", secondURL.toString());
            secondURL.toString()
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