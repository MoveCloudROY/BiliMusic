package top.roy1994.bilimusic.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import top.roy1994.bilimusic.ui.pages.MainFrame
import top.roy1994.bilimusic.ui.pages.SheetDetail
import top.roy1994.bilimusic.viewmodel.PlayerViewModel

@Composable
fun NavGraph (
    navController: NavHostController,
    playerVM: PlayerViewModel,
){
    NavHost(
        navController = navController,
        startDestination = Screens.Main.route
    )
    {
        composable(route = Screens.Main.route){
            MainFrame(navController = navController, playerVM = playerVM)
        }

        composable(
            route = "${Screens.Detail.route}/{sheetId}",
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
    }
}