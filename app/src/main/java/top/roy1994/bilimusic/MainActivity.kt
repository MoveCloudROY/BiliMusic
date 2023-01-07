package top.roy1994.bilimusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import top.roy1994.bilimusic.ui.navigation.NavGraph
import top.roy1994.bilimusic.ui.pages.MainFrame
import top.roy1994.bilimusic.ui.theme.BiliMusicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BiliMusicTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
//                MainFrame()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BiliMusicTheme {
        MainFrame()
    }
}