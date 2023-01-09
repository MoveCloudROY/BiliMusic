package top.roy1994.bilimusic.ui.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import top.roy1994.bilimusic.ui.components.MusicConfigDialog
import top.roy1994.bilimusic.ui.pages.ArtistDetail
import top.roy1994.bilimusic.ui.pages.MainFrame
import top.roy1994.bilimusic.ui.pages.SheetDetail
import top.roy1994.bilimusic.viewmodel.PlayerViewModel
import top.roy1994.bilimusic.viewmodel.TopSelectViewModel

@Composable
fun NavGraph (
    navController: NavHostController,
    playerVM: PlayerViewModel,
    topSelectBarVM: TopSelectViewModel
){
    NavHost(
        navController = navController,
        startDestination = Screens.Main.route
    )
    {
        composable(route = Screens.Main.route){
            MainFrame(
                navController = navController,
                playerVM = playerVM,
                topSelectBarVM = topSelectBarVM,
            )
        }

        composable(
            route = "${Screens.SheetDetail.route}/{sheetId}",
            arguments = listOf(navArgument("sheetId") { type = NavType.IntType })
        ) { backStackEntry ->
            val sheetId: Int? = backStackEntry.arguments?.getInt("sheetId")
            sheetId?.let {
                SheetDetail(
                    navController = navController,
                    sheetId = sheetId,
                    playerVM = playerVM
                )
            }
        }

        composable(
            route = "${Screens.ArtistDetail.route}/{artistId}",
            arguments = listOf(navArgument("artistId") { type = NavType.IntType })
        ) { backStackEntry ->
            val artistId: Int? = backStackEntry.arguments?.getInt("artistId")
            artistId?.let {
                ArtistDetail(
                    navController = navController,
                    artistId = artistId,
                    playerVM = playerVM
                )
            }
        }
        dialog(
            route = "${Screens.MusicConfig.route}/{musicId}",
            arguments = listOf(navArgument("musicId") { type = NavType.IntType })
        ) { backStackEntry ->
            val musicId: Int? = backStackEntry.arguments?.getInt("musicId")
            musicId?.let {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    MusicConfigDialog(
                        navController = navController,
                        musicId = musicId,
                    )
                }
            }
        }



    }
}