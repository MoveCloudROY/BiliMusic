package top.roy1994.bilimusic.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import top.roy1994.bilimusic.ui.pages.MainFrame
import top.roy1994.bilimusic.ui.pages.SheetDetail

@Composable
fun NavGraph (navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.Main.route
    )
    {
        composable(route = Screens.Main.route){
            MainFrame()
        }
        composable(route = Screens.Detail.route){
            SheetDetail()
        }
    }
}