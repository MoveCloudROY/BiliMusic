package top.roy1994.bilimusic

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.Navigator
import androidx.navigation.compose.rememberNavController
import top.roy1994.bilimusic.ui.navigation.NavGraph
import top.roy1994.bilimusic.ui.pages.MainFrame
import top.roy1994.bilimusic.ui.theme.BiliMusicTheme
import top.roy1994.bilimusic.viewmodel.PlayerViewModel
import top.roy1994.bilimusic.viewmodel.PlayerViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var playerVM: PlayerViewModel  // Declare playerVM as a property
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        playerVM = ViewModelProvider(this, PlayerViewModelFactory(applicationContext as Application)).get(PlayerViewModel::class.java)

        setContent {
            BiliMusicTheme {
                val navController = rememberNavController()
//                val playerVM: PlayerViewModel = viewModel(
//                    factory = PlayerViewModelFactory(
//                        LocalContext.current.applicationContext as Application
//                    )
//                )

                NavGraph(
                    navController = navController,
                    playerVM = playerVM,
                    topSelectBarVM = viewModel(),
                )
//                MainFrame()
            }
        }

        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        // Configure the behavior of the hidden system bars.
        windowInsetsController?.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController?.hide(WindowInsetsCompat.Type.systemBars())

        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = window.decorView.windowInsetsController
        // 隐藏状态栏
        // 同时隐藏状态栏和导航栏
        controller?.hide(WindowInsets.Type.statusBars())
        controller?.hide(WindowInsets.Type.systemBars())

    }

    override fun onStop() {
        super.onStop()
        Log.i("Activity", """
            Stop Main
        """.trimIndent())
        playerVM.recordIncompMusic();
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BiliMusicTheme {
        val navController = rememberNavController()
        val playerVM: PlayerViewModel = viewModel()
        NavGraph(
            navController = navController,
            playerVM = playerVM,
            topSelectBarVM = viewModel()
        )
    }
}